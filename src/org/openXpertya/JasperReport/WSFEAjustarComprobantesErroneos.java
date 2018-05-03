package org.openXpertya.JasperReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openXpertya.JasperReport.DataSource.WSFEConsultarComprobanteDataSource;
import org.openXpertya.model.MBPartner;
import org.openXpertya.model.MInvoice;
import org.openXpertya.model.MMessage;
import org.openXpertya.model.MNote;
import org.openXpertya.model.MPreference;
import org.openXpertya.model.MProcess;
import org.openXpertya.model.X_C_DocType;
import org.openXpertya.process.ProcessInfo;
import org.openXpertya.process.ProcessInfoParameter;
import org.openXpertya.process.SvrProcess;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import FEV1.dif.afip.gov.ar.Err;
import FEV1.dif.afip.gov.ar.FEAuthRequest;
import FEV1.dif.afip.gov.ar.FECompConsultaReq;
import FEV1.dif.afip.gov.ar.FECompConsultaResponse;
import FEV1.dif.afip.gov.ar.ServiceLocator;
import FEV1.dif.afip.gov.ar.ServiceSoap;

public class WSFEAjustarComprobantesErroneos extends SvrProcess {

	// TIPOS DE COMPROBANTES SEGUN AFIP 
	public static final int TIPO_COMPROBANTE_FACTURA_A 							= 1;
	public static final int TIPO_COMPROBANTE_NOTA_DEBITO_A						= 2;
	public static final int TIPO_COMPROBANTE_NOTA_CREDITO_A						= 3;
	public static final int TIPO_COMPROBANTE_FACTURA_B 							= 6;
	public static final int TIPO_COMPROBANTE_NOTA_DEBITO_B						= 7;
	public static final int TIPO_COMPROBANTE_NOTA_CREDITO_B						= 8;
	public static final int TIPO_COMPROBANTE_FACTURA_C 							= 11;
	public static final int TIPO_COMPROBANTE_NOTA_DEBITO_C						= 12;
	public static final int TIPO_COMPROBANTE_NOTA_CREDITO_C						= 13;
	public static final int TIPO_COMPROBANTE_FACTURA_EXPORTACION 				= 19;
	public static final int TIPO_COMPROBANTE_NOTA_DEBITO_EXPORTACION			= 20;
	public static final int TIPO_COMPROBANTE_NOTA_CREDITO_EXPORTACION			= 21;
	public static final int TIPO_COMPROBANTE_FACTURA_EXPORTACION_SIMPLIFICADO 	= 22;
	public static final int TIPO_COMPROBANTE_FACTURA_M 							= 51;
	public static final int TIPO_COMPROBANTE_NOTA_DEBITO_M						= 52;
	public static final int TIPO_COMPROBANTE_NOTA_CREDITO_M						= 53;
	public static final int TIPO_COMPROBANTE_TIQUE_FACTURA_A 					= 81;
	public static final int TIPO_COMPROBANTE_TIQUE_FACTURA_B 					= 82;
	public static final int TIPO_COMPROBANTE_TIQUE 								= 83;

	/** Archivo TA.xml conteniendo token y sign */ 
	public static final String TA_FILE_NAME = "TA.xml";
	/** Etiquta token dentro del archivo */
	public static final String TA_TAG_TOKEN = "token";
	/** Etiquta sign dentro del archivo */
	public static final String TA_TAG_SIGN  = "sign";
	
	/** Nomina de comprobantes recuperados.  En cada posicion de la lista hay una map con todos los datos, o bien ERROR_KEY si no se pudo recuperar */
	protected ArrayList<HashMap<String, String>> retrievedDocuments = new ArrayList<HashMap<String, String>>();

	/** Numero de reintentos por documento */
	public static final int RETRY_MAX = 3;
	/** Numero de documentos a consultar por ejecucion */
	public static final int MAX_DOCS = 100;
	
	/** Mapeo entre tipos de documentos con los tipos definidos por AFIP */
	public static HashMap<String, Integer> docTypeMap = new HashMap<String, Integer>();

	/** ID de la compañía */
	Integer clientID = null;
	/** CUIT de la compañía */
	Long cuit = null;	
	/** Sign de acceso a WS de AFIP */
	String sign = null;
	/** Token de acceso a WS de AFIP */
	String token = null;

	/** Numero de comprobante */
	protected long cbteNro = -1;
	/** Recuperacion un CAE en particular: punto de venta */
	protected int ptoVta = -1;
	/** Recuperacion un CAE en particular: tipo de comprobante */
	protected int cbteTipo = -1;
	/** Tipo de documento (nombre) */
	protected String cbteTipoNombre = "";

	/** Letra de Comprobante */
	String letraCbte = "";
	/** Recuperacion del Id del tipo de documento */
	protected int docTypeId = -1;
	
	/** DataSource para interaccion con Jasper */ 
	protected WSFEConsultarComprobanteDataSource ds;
	
	int ind_wsfe = 1;
	
	protected MBPartner cbp = null;

	
	static {
		// === TIPOS DE DOCUMENTOS A CONTEMPLAR EN LA BUSQUEDA === 
		
		// Comprobantes tipo A 
		docTypeMap.put("CIA", TIPO_COMPROBANTE_FACTURA_A);
		docTypeMap.put("CDNA", TIPO_COMPROBANTE_NOTA_DEBITO_A);
		docTypeMap.put("CCNA", TIPO_COMPROBANTE_NOTA_CREDITO_A);
		// Comprobantes tipo B		
		docTypeMap.put("CIB", TIPO_COMPROBANTE_FACTURA_B);
		docTypeMap.put("CDNB", TIPO_COMPROBANTE_NOTA_DEBITO_B);
		docTypeMap.put("CCNB", TIPO_COMPROBANTE_NOTA_CREDITO_B);
		// Comprobantes tipo C		
		docTypeMap.put("CIC", TIPO_COMPROBANTE_FACTURA_C);
		docTypeMap.put("CDNC", TIPO_COMPROBANTE_NOTA_DEBITO_C);
		docTypeMap.put("CCNC", TIPO_COMPROBANTE_NOTA_CREDITO_C);
		// Comprobantes tipo M		
		docTypeMap.put("CIM", TIPO_COMPROBANTE_FACTURA_M);
		docTypeMap.put("CDNM", TIPO_COMPROBANTE_NOTA_DEBITO_M);
		docTypeMap.put("CCNM", TIPO_COMPROBANTE_NOTA_CREDITO_M);
	}
	
		
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();

			if (para[i].getParameter() == null)
				;
			else if (name.equalsIgnoreCase("PtoVta")) {
				ptoVta = para[i].getParameterAsInt();
			}
			else if (name.equalsIgnoreCase("C_DocType_ID")) {
				X_C_DocType aDocType = new X_C_DocType(getCtx(), para[i].getParameterAsInt(), get_TrxName());
				cbteTipo = getCbteTipo(aDocType);
				cbteTipoNombre = aDocType.getName();
				docTypeId = para[i].getParameterAsInt();
			}
			else if (name.equalsIgnoreCase("NroCbte")) {
				cbteNro = ((BigDecimal)para[i].getParameter()).longValue();
			}
		}
	}
	
	
	@Override
	protected String doIt() throws Exception {
		
		// Validaciones preliminares
		checkPreconditions();
		
		// Carga de valores iniciales
		loadInitialValues();
		
		// Consulta de comprobantes
		queryInvoices();

		return "";
	}

	
	/** Recupera el tipo de documento según AFIP a partir del tipo de la factura */
	protected static int getCbteTipo(String docTypeKey) throws Exception {
		int i=0;
		while (!Character.isDigit(docTypeKey.charAt(i))) i++;
		return docTypeMap.get(docTypeKey.substring(0, i));
	}
	
	
	/** Genera la consulta al WS de AFIP por el comprobante dado */
	protected HashMap<String, String> consultarCAE(long cbteNro, int cbteTipo, String cbeTipoDesc, int ptoVta) {
		
		// Valores minimos a retornar, bien sea error o no (numero de documento, tipo de comprobante y punto de venta)
		HashMap<String, String> retValues = new HashMap<String, String>(); 
		retValues.put("DocNro", 	"" + cbteNro);
		retValues.put("CbteTipo", 	"" + cbteTipo);
		retValues.put("PtoVta", 	"" + ptoVta);
		
		// Recuperar el servicio
		ServiceLocator locator = new ServiceLocator();
		locator.setServiceSoapEndpointAddress("https://servicios1.afip.gov.ar/wsfev1/service.asmx");
		ServiceSoap service = null;
		try {
			service = locator.getServiceSoap();
		} catch (Exception e) {
			retValues.put("Resultado", "ERROR DE ACCESO A WSFE: " + e.toString());
			return retValues;
		}
		
		FEAuthRequest auth = new FEAuthRequest();
		FECompConsultaReq consulta = new FECompConsultaReq();
		
		auth.setCuit(cuit);
		auth.setSign(sign);
		auth.setToken(token);
		
		consulta.setCbteNro(cbteNro);
		consulta.setCbteTipo(cbteTipo);
		consulta.setPtoVta(ptoVta);

		// Invocacion a la operacion
		FECompConsultaResponse response = null;
		int tryNo = 0;
		boolean endLoop = false;
		
		while (!endLoop) {
			try {
				tryNo++;
				response = service.FECompConsultar(auth, consulta);
				response.toString();	// <-- NPE check
				System.out.println("Respuesta : " + response.toString());
				endLoop = true;
			} catch (Exception e) {
				// Al capturar una excepción al RETRY_MAX intento, no continuar intentando
				if (tryNo == RETRY_MAX) {
					// Error al interactuar con WSFE de AFIP
					retValues.put("Resultado", "ERROR DE CONEXION: " + e.toString());
					return retValues;
				}
			}
		}
		
		// Error recibido desde WSFE de AFIP
		if (response.getErrors() != null && response.getErrors().length > 0) {
			StringBuffer completeErrorStr = new StringBuffer();
			for (Err error : response.getErrors()) {
				StringBuffer errorStr = new StringBuffer();
				errorStr.append(error.getCode()).append(". ").append(error.getMsg());
				completeErrorStr.append(errorStr);
			}
			retValues.put("Resultado", "ERROR RECIBIDO: " + completeErrorStr.toString());
			return retValues;
		}

		/*
		String sql_insert = "INSERT INTO cg_wsfe_update " +
				"(CG_WSFE_Update_ID, AD_CLIENT_ID, AD_ORG_ID, CREATEDBY, UPDATEDBY, documentno, grandtotal) " +
				"VALUES (" + ind_wsfe + ",1010016,1010053,1010781,1010781," + response.getResultGet().getCbteDesde() + "," + response.getResultGet().getImpTotal() + ")";
		
		ind_wsfe = ind_wsfe + 1;

		DB.executeUpdate(sql_insert, get_TrxName());
		*/
		
		// En este punto se supone valores recibidos conformes a un comprobante encontrado
		retValues.put("CbteDesde", 			"" + response.getResultGet().getCbteDesde());
		retValues.put("CbteFch", 			"" + response.getResultGet().getCbteFch());
		retValues.put("CbteHasta", 			"" + response.getResultGet().getCbteHasta());
		retValues.put("CbteTipo", 			"" + response.getResultGet().getCbteTipo());
		retValues.put("CodAutorizacion", 	"" + response.getResultGet().getCodAutorizacion());
		retValues.put("Concepto", 			"" + response.getResultGet().getConcepto());
		retValues.put("DocNro", 			"" + response.getResultGet().getDocNro());
		retValues.put("DocTipo", 			"" + response.getResultGet().getDocTipo());
		retValues.put("EmisionTipo", 		"" + response.getResultGet().getEmisionTipo());
		retValues.put("FchProceso", 		"" + response.getResultGet().getFchProceso());
		retValues.put("FchServDesde", 		"" + response.getResultGet().getFchServDesde());
		retValues.put("FchServHasta", 		"" + response.getResultGet().getFchServHasta());
		retValues.put("FchVto", 			"" + response.getResultGet().getFchVto());
		retValues.put("FchVtoPago", 		"" + response.getResultGet().getFchVtoPago());
		retValues.put("ImpIVA", 			"" + response.getResultGet().getImpIVA());
		retValues.put("ImpNeto", 			"" + response.getResultGet().getImpNeto());
		retValues.put("ImpOpEx", 			"" + response.getResultGet().getImpOpEx());
		retValues.put("ImpTotal", 			"" + response.getResultGet().getImpTotal());
		retValues.put("ImpTotConc", 		"" + response.getResultGet().getImpTotConc());
		retValues.put("ImpTrib", 			"" + response.getResultGet().getImpTrib());
		retValues.put("MonCotiz", 			"" + response.getResultGet().getMonCotiz());
		retValues.put("MonId", 				"" + response.getResultGet().getMonId());
		retValues.put("PtoVta", 			"" + response.getResultGet().getPtoVta());
		retValues.put("Resultado", 			"" + response.getResultGet().getResultado());
		
		
		actulizarComprobanteLibertya(response.getResultGet().getPtoVta(),
										response.getResultGet().getEmisionTipo(),
										response.getResultGet().getCbteDesde(), 
										response.getResultGet().getCodAutorizacion(),
										response.getResultGet().getFchVto(),
										response.getResultGet().getImpTotal(),
										Long.toString(response.getResultGet().getDocNro()));
		
		
		return retValues; 
	}
	
	public String completarConCeros(String palabra, int cantidad) {
	       String valorRelleno = palabra;
	       for (int i = 0; i < cantidad - palabra.length(); i++) {
	           valorRelleno = "0" + valorRelleno;
	       }
	       return valorRelleno;
	}
	
	private void actulizarComprobanteLibertya(int ptoVta, String tipoDoc, long nroCbte, String CAE, String vtoCAE, double impTotal, String taxid) {
		// TODO Auto-generated method stub
		
		
		
		
		
		// Ver reemplazo de letra dinámica
		
		String nroCompleto = "A" + completarConCeros(String.valueOf(ptoVta), 4) + completarConCeros(String.valueOf(nroCbte), 8);
		
		int[] cbte = MInvoice.getAllIDs(MInvoice.Table_Name, "c_doctype_id = " + docTypeId + " AND documentno = '" + nroCompleto + "' AND issotrx = 'Y'", this.get_TrxName());
		
		if(cbte.length != 0){
			
			int cbte_ID = cbte[0];
			MInvoice inv = MInvoice.get(this.getCtx(), cbte_ID, this.get_TrxName());
					
			inv.setcae(CAE);
			// FALTA: parsear vtoCAE a Timestamp !!!!!!!
			String an = vtoCAE.substring(0, 4);
			String me = vtoCAE.substring(4, 6);
			String di = vtoCAE.substring(6, 8);
			String fecha = an + "-" + me + "-" + di + " 00:00:00";
			inv.setvtocae(Timestamp.valueOf(fecha));
			inv.save(this.get_TrxName());
			MNote note = new MNote(getCtx(),0, 1010717, MInvoice.Table_ID, inv.getC_Invoice_ID(),"Comprobante actualizado", "Comprobante " + nroCompleto + " con problemas", this.get_TrxName());
			note.save();				
	
		} else {
			
			MNote note = new MNote(getCtx(),0, 1010717, MInvoice.Table_ID, 0,"Comprobante inexistente", "Comprobante " + nroCbte + " no existe en Libertya", this.get_TrxName());
			note.save();
			
		}
		
		
	}

	/**
	 * Retorna el tipo de comprobante segun la denominacion de AFIP a partir del doctype 
	 */
	protected int getCbteTipo(X_C_DocType aDocType) {
		try {
			StringBuffer type = new StringBuffer();
			for (int i = 0; i < aDocType.getDocTypeKey().length(); i++) {
				if (Character.isDigit(aDocType.getDocTypeKey().charAt(i)))
					break;
				type.append(aDocType.getDocTypeKey().charAt(i));
			}
			return docTypeMap.get(type.toString());
		} catch (Exception e) {
			return -1;
		}
	}

	/** Validaciones preliminares */
	protected void checkPreconditions() throws Exception {
		if (cbteNro == -1)
			throw new Exception("Comprobante invalido");
		if (ptoVta == -1)
			throw new Exception("Punto de venta indicado invalido");
		if (cbteTipo == -1)
			throw new Exception("Tipo de documento indicado invalido");
	}
	
	
	/** Carga inicial */
	protected void loadInitialValues() throws Exception {
		// Parse del TA.xml
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		File file = new File(getTAFileName());
		Document doc = builder.parse(file);
		// Token
		NodeList tokenNodes = doc.getElementsByTagName(TA_TAG_TOKEN);
		token = (tokenNodes.item(0).getFirstChild().getNodeValue());
		// Sign
		NodeList signNodes = doc.getElementsByTagName(TA_TAG_SIGN);
		sign = (signNodes.item(0).getFirstChild().getNodeValue());
		// ClientID
		clientID = getAD_Client_ID();
		// Cuit
		try {
			cuit = Long.parseLong(DB.getSQLValueString(null, "SELECT replace(cuit, '-', '') FROM AD_ClientInfo WHERE AD_Client_ID = ?", clientID));
		} catch (Exception ex) {
			throw new Exception ("Error al recuperar CUIT de la BBDD de compañía " + clientID + ". " + ex.getMessage());
		}
			

	}

	/** Consultar cada una de los comprobantes */
	protected void queryInvoices() throws Exception {
		// Consultar por el comprobante
		retrievedDocuments.add(consultarCAE(cbteNro, cbteTipo, "", ptoVta));
	}
	
	
	protected String getTAFileName() {
		String pyafipwsLocation = MPreference.GetCustomPreferenceValue("WSFE_PV" + ptoVta, clientID);
		//return pyafipwsLocation + File.separator + TA_FILE_NAME;
		return "/home/geneos/" + TA_FILE_NAME;
		//return "C:\\" + TA_FILE_NAME;
	}
	
}
