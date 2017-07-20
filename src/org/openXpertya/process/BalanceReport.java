package org.openXpertya.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openXpertya.model.MBPartner;
import org.openXpertya.model.MPayment;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;
import org.openXpertya.util.Util;

public class BalanceReport extends SvrProcess {

	/** Organización de los comprobantes a consultar */
	private int    p_AD_Org_ID;
	/** Visualizar solo clientes con facturas impagas o todos (AL (All), OO (Open invoices Only) */
	private String     p_Scope; 
	/** Criterio de ordenamiento (BP (BPartner), BL (Balance), OI (Oldest open Invoice) */
	private String     p_Sort_Criteria;
	/** Grupo de entidad comercial */
	private int    p_C_BP_Group_ID;
	/** Fecha desde de la transacción 
	 *  Agregado para dar soporte al requerimiento de Goizueta 
	 * */
	private Timestamp  p_DateTrx_From;
	/** Fecha hasta de la transacción */
	private Timestamp  p_DateTrx_To;
	/** Tipo de Cuenta del Reporte: Cliente o Proveedor */
	private String     p_AccountType;
	/** Signo de documentos que son débitos (depende de p_AccountType) */
	private int debit_signo_issotrx;
	/** Signo de documentos que son créditos (depende de p_AccountType) */
	private int credit_signo_issotrx;
	/** Es transacción de ventas? (depende de p_AccountType) */
	private String isSOtrx = "Y";
	/** Moneda en la que trabaja la compañía */
	private int client_Currency_ID;
	/** Clave de búsqueda desde */
	private String valueFrom;
	/** Clave de búsqueda hasta */
	private String valueTo;
	/** Sólo mostrar con crédito activado */
	private boolean onlyCurentAccounts;
	
	@Override
	protected void prepare() {

        ProcessInfoParameter[] para = getParameter();
        for( int i = 0;i < para.length;i++ ) {
            String name = para[ i ].getParameterName();

            if( para[ i ].getParameter() == null ) ;
            else if( name.equalsIgnoreCase( "Scope" )) {
            	p_Scope = (String)para[ i ].getParameter();
        	} else if( name.equalsIgnoreCase( "SortCriteria" )) {
            	p_Sort_Criteria = (String)para[ i ].getParameter();
        	} else if( name.equalsIgnoreCase( "AD_Org_ID" )) {
        		BigDecimal tmp = ( BigDecimal )para[ i ].getParameter();
        		p_AD_Org_ID = tmp == null ? null : tmp.intValue();
        	} else if( name.equalsIgnoreCase( "C_BP_Group_ID" )) {
        		p_C_BP_Group_ID = para[ i ].getParameterAsInt();
        	} else if (name.equalsIgnoreCase("TrueDateTrx")) {
				p_DateTrx_From = (Timestamp) para[i].getParameter();
				p_DateTrx_To = (Timestamp) para[i].getParameter_To();
        	} else if( name.equalsIgnoreCase( "AccountType" )) {
        		p_AccountType = ( String )para[ i ].getParameter();
        	} else if( name.equalsIgnoreCase( "OnlyCurrentAccounts" )) {
        		onlyCurentAccounts = ((String)para[ i ].getParameter()).equals("Y");
        	} else if( name.equalsIgnoreCase( "ValueFrom" )) {
        		valueFrom = (String)para[ i ].getParameter();
        	} else if( name.equalsIgnoreCase( "ValueTo" )) {
        		valueTo = (String)para[ i ].getParameter();
        	}
        }
        
        // Reporte de Cta Corriente de Cliente o Proveedor.
 		// +-------------------------+--------------------------+
 		// |  Cta Corriente Cliente  |  Cta Corriente Proveedor |
 		// +-------------------------+--------------------------+
 		// |   Debitos  = Signo 1    |   Debitos  = Signo -1    |
 		// |   Créditos = Signo -1   |   Créditos = Signo 1     |
 		// +-------------------------+--------------------------+
        
        /*
         * Geneos
         * 
         * Cambio para que el reporte de proveedor salga imputado al revés del de cliente.
         * Pedido de Goizueta
         * 
         */
        
        
        
 		debit_signo_issotrx = p_AccountType.equalsIgnoreCase("C") || p_AccountType.equalsIgnoreCase("P") ? 1 : -1;
 		credit_signo_issotrx = p_AccountType.equalsIgnoreCase("C") || p_AccountType.equalsIgnoreCase("P") ? -1 : 1;
		
        isSOtrx = p_AccountType.equalsIgnoreCase("C")?"'Y'":"'N'";
        // Moneda de la compañía utilizada para conversión de montos de documentos.
        client_Currency_ID = Env.getContextAsInt(getCtx(), "$C_Currency_ID");
	}
	
	
	
	
	

	
	@Override
	protected String doIt() throws Exception {

		// delete all rows older than a week
		DB.executeUpdate("DELETE FROM T_BALANCEREPORT WHERE DATECREATED < ('now'::text)::timestamp(6) - interval '7 days'");		
		// delete all rows in table with the given ad_pinstance_id
		DB.executeUpdate("DELETE FROM T_BALANCEREPORT WHERE AD_PInstance_ID = " + getAD_PInstance_ID());

		// calcular el estado de cuenta de cada E.C.
		StringBuffer sqlDoc = new StringBuffer();
		sqlDoc.append(" SELECT T.C_BPARTNER_ID, T.NAME, C_BP_Group_ID, COALESCE(T.so_description,'') AS so_description, coalesce(T.totalopenbalance,0.00) as actualbalance, COALESCE(SUM(t.Credit),0.0) AS Credit, COALESCE(SUM(t.Debit),0.0) AS Debit, COALESCE(SUM(t.Debit),0.0) - COALESCE(SUM(t.Credit),0.0) AS balance,  ");
		sqlDoc.append(" 	( SELECT dateacct FROM C_INVOICE WHERE issotrx = ")
				.append(isSOtrx)
				.append(" AND invoiceopen(c_invoice_id, 0) > 0	AND C_BPartner_id = t.c_bpartner_id ORDER BY DATEACCT asc LIMIT 1 ) as fecha_fact_antigua, ");
		sqlDoc.append(" 	( SELECT dateacct FROM C_INVOICE WHERE issotrx = ")
				.append(isSOtrx)
				.append(" AND invoiceopen(c_invoice_id, 0) > 0	AND C_BPartner_id = t.c_bpartner_id ORDER BY DATEACCT desc LIMIT 1 ) as fecha_fact_reciente, ");
		sqlDoc.append(" (select coalesce(sum(invoiceopen(c_invoice_id, c_invoicepayschedule_id)),0.00) as duedebt " +
						"from c_invoice_v as i " +
						"where (i.duedate::date >= ?::date and i.duedate::date <= ?::date) " +
						"		and i.c_bpartner_id = T.c_bpartner_id " +
						"		and i.docstatus not in ('DR','IN')");
		if (p_AD_Org_ID > 0){
			sqlDoc.append(" AND AD_Org_ID = ").append(p_AD_Org_ID);
		}
		sqlDoc.append(" AND AD_Client_ID = ").append(Env.getAD_Client_ID(getCtx()));
		sqlDoc.append(") as duedebt, ");
		sqlDoc.append(" (select coalesce(sum(paymentavailable(c_payment_id)),0.00) as duedebt " +
						"from c_payment " +
						"where (duedate::date >= ?::date and duedate::date <= ?::date) " +
						"		and c_bpartner_id = T.c_bpartner_id " +
						"		and docstatus not in ('DR','IN') " +
						"		and tendertype = '"+MPayment.TENDERTYPE_Check+"'");
		if (p_AD_Org_ID > 0){
			sqlDoc.append(" AND AD_Org_ID = ").append(p_AD_Org_ID);
		}
		sqlDoc.append(" AND AD_Client_ID = ").append(Env.getAD_Client_ID(getCtx()));
		sqlDoc.append(") as chequesencartera ");
		sqlDoc.append(" FROM ");
		if(!Util.isEmpty(valueFrom, true)){
			sqlDoc.append(" (SELECT min(value) as minvalue " +
						" FROM c_bpartner " +
						" WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) +
						"		and (('"+valueFrom+"' is null OR length(trim('"+valueFrom+"'::character varying)) = 0) " +
						"				OR (CASE WHEN position('%' in '"+valueFrom+"'::character varying) > 0	" +
						"						THEN value ilike trim('"+valueFrom+"'::character varying) " +
						"						ELSE upper(value) >= upper(trim('"+valueFrom+"'::character varying)) END))) min, ");
		}
		if(!Util.isEmpty(valueTo, true)){
			sqlDoc.append(" (SELECT max(value) as maxvalue " +
						" FROM c_bpartner " +
						" WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) +
						"		AND (('"+valueTo+"' is null OR length(trim('"+valueTo+"'::character varying)) = 0) " +
								"		OR (CASE WHEN position('%' in '"+valueTo+"'::character varying) > 0 " +
								"				THEN value ilike trim('"+valueTo+"'::character varying) " +
								"				ELSE upper(value) <= upper(trim('"+valueTo+"'::character varying)) END))) max, ");
		}
		sqlDoc.append(" ( ");
		sqlDoc.append(" 	SELECT " ); 
		sqlDoc.append(" 		d.c_bpartner_id, ");
		sqlDoc.append(" 		bp.value, ");
		sqlDoc.append(" 		bp.name, ");
		sqlDoc.append(" 		bp.C_BP_Group_ID, ");
		sqlDoc.append(" 		bp.so_description, "); 
		sqlDoc.append(" 		bp.totalopenbalance, ");
		sqlDoc.append(" 		CASE WHEN d.signo_issotrx = ").append(debit_signo_issotrx).append(" THEN "); 
		sqlDoc.append(" 			currencyconvert(d.amount, d.c_currency_id, ").append(client_Currency_ID).append(", ('"+ ((p_DateTrx_To != null) ? p_DateTrx_To + "'" : "now'::text") +")::timestamp(6) with time zone, COALESCE(c_conversiontype_id,0), d.ad_client_id, d.ad_org_id) "); 
		sqlDoc.append(" 		ELSE 0.0 END AS Debit, "); 
		sqlDoc.append(" 		CASE WHEN d.signo_issotrx = ").append(credit_signo_issotrx).append(" THEN "); 
		sqlDoc.append(" 			currencyconvert(d.amount, d.c_currency_id, ").append(client_Currency_ID).append(", ('"+ ((p_DateTrx_To != null) ? p_DateTrx_To + "'" : "now'::text") +")::timestamp(6) with time zone, COALESCE(c_conversiontype_id,0), d.ad_client_id, d.ad_org_id) "); 
		sqlDoc.append(" 		ELSE 0.0 END AS Credit ");
		sqlDoc.append(" 	FROM V_Documents_Org d "); 
		sqlDoc.append(" 	INNER JOIN c_bpartner bp on d.c_bpartner_id = bp.c_bpartner_id ");
		sqlDoc.append(" 	WHERE d.DocStatus IN ('CO', 'CL', 'RE', 'VO') ");
		
		/*
		 *  Agregado para filtrar los movimientos entre fechas
		 *  Geneos por bug del informe detectado 02022017
		 *  
		 */
		
		sqlDoc.append(" 	AND d.truedatetrx::date >= '" + p_DateTrx_From + "'::date AND d.truedatetrx::date <='" + p_DateTrx_To + "'::date");
				
		if (p_AD_Org_ID > 0)			// filtrar comprobantes para una organizacion especifica (o no)
			sqlDoc.append(" AND d.AD_Org_ID = ").append(p_AD_Org_ID);
		if(p_C_BP_Group_ID > 0){		// filtrar comprobantes para un grupo de EC especifico (o no)
			sqlDoc.append(" AND bp.C_BP_Group_ID = ").append(p_C_BP_Group_ID);
		}
		if ("OO".equals(p_Scope))		// filtrar E.C.: solo las que adeudan, o listar todas
		{
			sqlDoc.append(" AND bp.C_BPartner_ID IN (");
			sqlDoc.append(" 	SELECT DISTINCT c_bpartner_id FROM c_invoice ");
			sqlDoc.append(
					" 	WHERE invoiceopen(c_invoice_id, 0) > 0 AND issotrx = ")
					.append(isSOtrx).append(" AND AD_Client_ID = ")
					.append(getAD_Client_ID()).append(")");
		}
		sqlDoc.append(" 	AND d.AD_Client_ID = ").append(Env.getAD_Client_ID(getCtx())); 
		sqlDoc.append(" 	AND bp.isactive = 'Y' ");
		/*
		if(p_DateTrx_From != null && p_DateTrx_To != null){
			sqlDoc.append(" 	AND (d.truedatetrx::date >= ?::date and d.truedatetrx::date <= ?::date) ");
		}
		*/
		sqlDoc.append(p_AccountType.equalsIgnoreCase("C") ? " AND bp.iscustomer = 'Y' "
				: " AND bp.isvendor = 'Y' ");
		sqlDoc.append(onlyCurentAccounts?" AND d.socreditstatus <> 'X' ":"");
		sqlDoc.append(" ) AS T ");
		sqlDoc.append(" WHERE (1=1) ");
		if(!Util.isEmpty(valueFrom, true)){
			sqlDoc.append(" AND T.value >= minvalue ");
		}
		if(!Util.isEmpty(valueTo, true)){
			sqlDoc.append(" AND T.value <= maxvalue ");
		}
		
		sqlDoc.append(" GROUP BY T.c_bpartner_id, T.name, T.C_BP_Group_ID, T.so_description, T.totalopenbalance ");
		sqlDoc.append(" ORDER BY ");
		if ("BP".equals(p_Sort_Criteria))
			sqlDoc.append("T.name");
		if ("BL".equals(p_Sort_Criteria))
			sqlDoc.append("balance");		
		if ("OI".equals(p_Sort_Criteria))
			sqlDoc.append("fecha_fact_antigua");		
		
		// ejecutar la consulta y cargar la tabla temporal
		PreparedStatement pstmt = DB.prepareStatement(sqlDoc.toString(), get_TrxName(), true);
		
		System.out.println("SQL: " + sqlDoc.toString());
		
		
		int i = 1;
		// Parámetros de sqlDoc
		pstmt.setTimestamp(i++,
				p_DateTrx_From != null ? p_DateTrx_From : Env.getDate());
		pstmt.setTimestamp(i++,
				p_DateTrx_To != null ? p_DateTrx_To : Env.getDate());
		pstmt.setTimestamp(i++,
				p_DateTrx_From != null ? p_DateTrx_From : Env.getDate());
		pstmt.setTimestamp(i++,
				p_DateTrx_To != null ? p_DateTrx_To : Env.getDate());
		//pstmt.setTimestamp(i++,
		//		p_DateTrx_From != null ? p_DateTrx_From : Env.getDate());
		//pstmt.setTimestamp(i++,
		//		p_DateTrx_To != null ? p_DateTrx_To : Env.getDate());		
		
		/*
		if(p_DateTrx_From != null && p_DateTrx_To != null){
			pstmt.setTimestamp(i++, p_DateTrx_From);
			pstmt.setTimestamp(i++, p_DateTrx_To);
		}
		*/
		ResultSet rs = pstmt.executeQuery();
		int subindice=0;
		StringBuffer usql = new StringBuffer();
		
		String in = "(";
		int flagIn = 0;
		
		
		while (rs.next())
		{
			// Pedido para que no figuren las entidades con saldo 0
			// Goizueta 14/11/2016
			
			MBPartner mbp = new MBPartner(this.getCtx(), rs.getInt("C_BPartner_ID"), this.get_TrxName());
			
			if(flagIn == 0){
				flagIn = 1;
				in += rs.getInt("C_BPartner_ID");
			} else {
				in += "," + rs.getInt("C_BPartner_ID");
			}
			
			
			usql = new StringBuffer();
			
			
			
			//BigDecimal s = this.getSaldoFecha(p_DateTrx_From, rs.getInt("C_BPartner_ID")).add(rs.getBigDecimal("Debit")).subtract(rs.getBigDecimal("Credit"));
			BigDecimal sal = this.getSaldoFecha(p_DateTrx_From, rs.getInt("C_BPartner_ID"));
			System.out.println(mbp.getName() + ": " + this.getSaldoFecha(p_DateTrx_From, rs.getInt("C_BPartner_ID")) + " " + rs.getBigDecimal("Debit") + " " + rs.getBigDecimal("Credit"));
			if(!sal.setScale(2).equals(Env.ZERO.setScale(2)) || !rs.getBigDecimal("Debit").setScale(2).equals(Env.ZERO.setScale(2)) || !rs.getBigDecimal("Credit").setScale(2).equals(Env.ZERO.setScale(2))) {

				subindice++;
				usql.append(" INSERT INTO T_BALANCEREPORT (ad_pinstance_id, ad_client_id, ad_org_id, subindice, c_bpartner_id, observaciones, ");
				usql.append("								s_init, credit, debit, balance, date_oldest_open_invoice, date_newest_open_invoice, sortcriteria, scope, c_bp_group_id, truedatetrx, accounttype, ");
				usql.append("								onlycurrentaccounts, valuefrom, valueto, duedebt, actualbalance, chequesencartera, generalbalance ) ");
				// Se anexa la columna saldo inicial y el saldo final como resultado de sumar los debitos y restar los créditos en proveedor daría negativo (debemos) y cliente positivo (nos deben).
				usql.append(" VALUES ( ")	.append(getAD_PInstance_ID()).append(",")
											.append(getAD_Client_ID()).append(",")
											.append(p_AD_Org_ID).append(",")
											.append(subindice).append(",")
											.append(rs.getInt("C_BPartner_ID")).append(", '")
											.append(rs.getString("SO_DESCRIPTION")).append("', ")
											.append(this.getSaldoFecha(p_DateTrx_From, rs.getInt("C_BPartner_ID"))).append(",")
											.append(rs.getBigDecimal("Credit")).append(",")
											.append(rs.getBigDecimal("Debit")).append(",")
											.append(this.getSaldoFecha(p_DateTrx_From, rs.getInt("C_BPartner_ID")).add(rs.getBigDecimal("Debit")).subtract(rs.getBigDecimal("Credit"))).append(", ");
				if (rs.getTimestamp("fecha_fact_antigua")!=null)
					usql.append(" '").append(rs.getTimestamp("fecha_fact_antigua")).append("', ");
				else
					usql.append("null, ");
				if (rs.getTimestamp("fecha_fact_reciente")!=null)
					usql.append(" '").append(rs.getTimestamp("fecha_fact_reciente")).append("', ");
				else
					usql.append("null, ");
				usql.append(" '").append(p_Sort_Criteria).append("', ")
					.append(" '").append(p_Scope).append("', ")
					.append(rs.getInt("C_BP_Group_ID")).append(", ");
				if (p_DateTrx_To!=null)
					usql.append(" '").append(p_DateTrx_To).append("'::timestamp, ");
				else
					usql.append("null, ");
				usql.append("'").append(p_AccountType).append("'");
				usql.append(" , ");
				usql.append(onlyCurentAccounts?"'Y'":"'N'");
				usql.append(" , ");
				usql.append("'"+valueFrom+"'");
				usql.append(" , ");
				usql.append("'"+valueTo+"'");
				usql.append(" , ");
				usql.append(rs.getBigDecimal("duedebt"));
				usql.append(" , ");
				usql.append(rs.getBigDecimal("actualbalance"));
				usql.append(" , ");
				usql.append(rs.getBigDecimal("chequesencartera"));
				usql.append(" , ");
				usql.append(rs.getBigDecimal("actualbalance").add(
						rs.getBigDecimal("chequesencartera")));
				usql.append(" ); ");
				
				int no = DB.executeUpdate(usql.toString(), get_TrxName());
				if(no == 0){
					throw new Exception("Error insertando datos en la tabla temporal");
				}
				
			}
			
		}
		
		in += ")";

		/*
		 * Geneos
		 * 
		 * Cambio pedido el 11/07/2017 volver a modificar el formato sacando las dos secciones 
		 * mezclado todos con o sin movimientos, solo no van los saldos de inicio = 0 y sin movimientos.
		 * 
		 * 
		 */

		PreparedStatement pstmt_sinmov = null;
		ResultSet rs_sinmov = null;
		
		StringBuilder sql_sinmov = new StringBuilder();
		sql_sinmov.append("SELECT C_BPartner_ID from C_BPartner where C_BP_Group_ID = " + p_C_BP_Group_ID + " and C_BPartner_ID not in " + in + " order by name desc");
		
		
		pstmt_sinmov = DB.prepareStatement(sql_sinmov.toString(), get_TrxName());
		usql = new StringBuffer();
		
		rs_sinmov = pstmt_sinmov.executeQuery();
		
		while (rs_sinmov.next()) {

			
			MBPartner mbp = new MBPartner(this.getCtx(), rs_sinmov.getInt("C_BPartner_ID"), this.get_TrxName());

			BigDecimal sal = this.getSaldoFecha(p_DateTrx_From, rs_sinmov.getInt("C_BPartner_ID"));
			if(!sal.setScale(2).equals(Env.ZERO.setScale(2))) {
				
				usql = new StringBuffer();
				subindice++;
				usql.append(" INSERT INTO T_BALANCEREPORT (ad_pinstance_id, ad_client_id, ad_org_id, subindice, c_bpartner_id, observaciones, ");
				usql.append("								s_init, credit, debit, balance, date_oldest_open_invoice, date_newest_open_invoice, sortcriteria, scope, c_bp_group_id, truedatetrx, accounttype, ");
				usql.append("								onlycurrentaccounts, valuefrom, valueto, duedebt, actualbalance, chequesencartera, generalbalance ) ");
				// Se anexa la columna saldo inicial y el saldo final como resultado de sumar los debitos y restar los créditos en proveedor daría negativo (debemos) y cliente positivo (nos deben).
				usql.append(" VALUES ( ")	.append(getAD_PInstance_ID()).append(",")
											.append(getAD_Client_ID()).append(",")
											.append(p_AD_Org_ID).append(",")
											.append(subindice).append(",")
											.append(rs_sinmov.getInt("C_BPartner_ID"))
											.append(", '', ")
											.append(this.getSaldoFecha(p_DateTrx_From, rs_sinmov.getInt("C_BPartner_ID")))
											.append(",0")
											.append(",0")
											.append("," + this.getSaldoFecha(p_DateTrx_From, rs_sinmov.getInt("C_BPartner_ID")))
											.append(", null")
											.append(", null");
				
				usql.append(", '").append(p_Sort_Criteria).append("'")
					.append(", '").append(p_Scope).append("'")
					.append(", ").append(p_C_BP_Group_ID).append(", ");
				
				if (p_DateTrx_To!=null)
					usql.append(" '").append(p_DateTrx_To).append("'::timestamp, ");
				else
					usql.append("null, ");
				
				usql.append("'").append(p_AccountType).append("'").append(", ");
				usql.append(onlyCurentAccounts?"'Y'":"'N'").append(", ");
				usql.append("'"+valueFrom+"'").append(", ");
				usql.append("'"+valueTo+"'");
				usql.append(" , 0");
				usql.append(" , 0");
				usql.append(" , 0");
				usql.append(" , 0");
				usql.append(" ); ");			
			
				int no = DB.executeUpdate(usql.toString(), get_TrxName());
				if(no == 0){
					throw new Exception("Error insertando datos en la tabla temporal");
				}
									
			}			
			
		}

		return "OK";
		
	}

	/*
	 *  Función agregada para obtener el saldo inicial de la Entidad Comercial
	 *  Cambio en el formato del reporte pedido por Goizueta.
	 *  22/08/2016
	 * 
	 */
	
	protected BigDecimal getSaldoFecha(Timestamp  p_Fecha, int C_BPartner_ID) throws Exception {

		// calcular el estado de cuenta de cada E.C.
		StringBuffer sqlDoc = new StringBuffer();
		sqlDoc.append(" SELECT T.C_BPARTNER_ID, T.NAME, C_BP_Group_ID, COALESCE(T.so_description,'') AS so_description, coalesce(T.totalopenbalance,0.00) as actualbalance, COALESCE(SUM(t.Credit),0.0) AS Credit, COALESCE(SUM(t.Debit),0.0) AS Debit, COALESCE(SUM(t.Debit),0.0) - COALESCE(SUM(t.Credit),0.0) AS balance,  ");
		sqlDoc.append(" 	( SELECT dateacct FROM C_INVOICE WHERE issotrx = ")
				.append(isSOtrx)
				.append(" AND invoiceopen(c_invoice_id, 0) > 0	AND C_BPartner_id = t.c_bpartner_id ORDER BY DATEACCT asc LIMIT 1 ) as fecha_fact_antigua, ");
		sqlDoc.append(" 	( SELECT dateacct FROM C_INVOICE WHERE issotrx = ")
				.append(isSOtrx)
				.append(" AND invoiceopen(c_invoice_id, 0) > 0	AND C_BPartner_id = t.c_bpartner_id ORDER BY DATEACCT desc LIMIT 1 ) as fecha_fact_reciente, ");
		sqlDoc.append(" (select coalesce(sum(invoiceopen(c_invoice_id, c_invoicepayschedule_id)),0.00) as duedebt " +
						"from c_invoice_v as i " +
						"where i.duedate::date < ?::date " +
						"		and i.c_bpartner_id = T.c_bpartner_id " +
						"		and i.docstatus not in ('DR','IN')");
		if (p_AD_Org_ID > 0){
			sqlDoc.append(" AND AD_Org_ID = ").append(p_AD_Org_ID);
		}
		sqlDoc.append(" AND AD_Client_ID = ").append(Env.getAD_Client_ID(getCtx()));
		sqlDoc.append(") as duedebt, ");
		sqlDoc.append(" (select coalesce(sum(paymentavailable(c_payment_id)),0.00) as duedebt " +
						"from c_payment " +
						"where duedate::date < ?::date " +
						"		and c_bpartner_id = T.c_bpartner_id " +
						"		and docstatus not in ('DR','IN') " +
						"		and tendertype = '"+MPayment.TENDERTYPE_Check+"'");
		if (p_AD_Org_ID > 0){
			sqlDoc.append(" AND AD_Org_ID = ").append(p_AD_Org_ID);
		}
		sqlDoc.append(" AND AD_Client_ID = ").append(Env.getAD_Client_ID(getCtx()));
		sqlDoc.append(") as chequesencartera ");
		sqlDoc.append(" FROM ");
		if(!Util.isEmpty(valueFrom, true)){
			sqlDoc.append(" (SELECT min(value) as minvalue " +
						" FROM c_bpartner " +
						" WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) +
						"		and (('"+valueFrom+"' is null OR length(trim('"+valueFrom+"'::character varying)) = 0) " +
						"				OR (CASE WHEN position('%' in '"+valueFrom+"'::character varying) > 0	" +
						"						THEN value ilike trim('"+valueFrom+"'::character varying) " +
						"						ELSE upper(value) >= upper(trim('"+valueFrom+"'::character varying)) END))) min, ");
		}
		if(!Util.isEmpty(valueTo, true)){
			sqlDoc.append(" (SELECT max(value) as maxvalue " +
						" FROM c_bpartner " +
						" WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) +
						"		AND (('"+valueTo+"' is null OR length(trim('"+valueTo+"'::character varying)) = 0) " +
								"		OR (CASE WHEN position('%' in '"+valueTo+"'::character varying) > 0 " +
								"				THEN value ilike trim('"+valueTo+"'::character varying) " +
								"				ELSE upper(value) <= upper(trim('"+valueTo+"'::character varying)) END))) max, ");
		}
		sqlDoc.append(" ( ");
		sqlDoc.append(" 	SELECT " ); 
		sqlDoc.append(" 		d.c_bpartner_id, ");
		sqlDoc.append(" 		bp.value, ");
		sqlDoc.append(" 		bp.name, ");
		sqlDoc.append(" 		bp.C_BP_Group_ID, ");
		sqlDoc.append(" 		bp.so_description, "); 
		sqlDoc.append(" 		bp.totalopenbalance, ");
		sqlDoc.append(" 		CASE WHEN d.signo_issotrx = ").append(debit_signo_issotrx).append(" THEN "); 
		sqlDoc.append(" 			currencyconvert(d.amount, d.c_currency_id, ").append(client_Currency_ID).append(", ('"+ ((p_Fecha != null) ? p_Fecha + "'" : "now'::text") +")::timestamp(6) with time zone, COALESCE(c_conversiontype_id,0), d.ad_client_id, d.ad_org_id) "); 
		sqlDoc.append(" 		ELSE 0.0 END AS Debit, "); 
		sqlDoc.append(" 		CASE WHEN d.signo_issotrx = ").append(credit_signo_issotrx).append(" THEN "); 
		sqlDoc.append(" 			currencyconvert(d.amount, d.c_currency_id, ").append(client_Currency_ID).append(", ('"+ ((p_Fecha != null) ? p_Fecha + "'" : "now'::text") +")::timestamp(6) with time zone, COALESCE(c_conversiontype_id,0), d.ad_client_id, d.ad_org_id) "); 
		sqlDoc.append(" 		ELSE 0.0 END AS Credit ");
		sqlDoc.append(" 	FROM V_Documents_Org d "); 
		sqlDoc.append(" 	INNER JOIN c_bpartner bp on d.c_bpartner_id = bp.c_bpartner_id ");
		sqlDoc.append(" 	WHERE d.DocStatus IN ('CO', 'CL', 'RE', 'VO') ");
		if (p_AD_Org_ID > 0)			// filtrar comprobantes para una organizacion especifica (o no)
			sqlDoc.append(" AND d.AD_Org_ID = ").append(p_AD_Org_ID);
		if(p_C_BP_Group_ID > 0){		// filtrar comprobantes para un grupo de EC especifico (o no)
			sqlDoc.append(" AND bp.C_BP_Group_ID = ").append(p_C_BP_Group_ID);
		}
		if ("OO".equals(p_Scope))		// filtrar E.C.: solo las que adeudan, o listar todas
		{
			sqlDoc.append(" AND bp.C_BPartner_ID IN (");
			sqlDoc.append(" 	SELECT DISTINCT c_bpartner_id FROM c_invoice ");
			sqlDoc.append(
					" 	WHERE invoiceopen(c_invoice_id, 0) > 0 AND issotrx = ")
					.append(isSOtrx).append(" AND AD_Client_ID = ")
					.append(getAD_Client_ID()).append(")");
		}
		sqlDoc.append(" 	AND d.AD_Client_ID = ").append(Env.getAD_Client_ID(getCtx())); 
		sqlDoc.append(" 	AND bp.isactive = 'Y' ");
		if(p_Fecha != null){
			sqlDoc.append(" 	AND d.truedatetrx::date < ?::date");
		}
		sqlDoc.append(p_AccountType.equalsIgnoreCase("C") ? " AND bp.iscustomer = 'Y' "
				: " AND bp.isvendor = 'Y' ");
		sqlDoc.append(onlyCurentAccounts?" AND d.socreditstatus <> 'X' ":"");
		sqlDoc.append(" ) AS T ");
		sqlDoc.append(" WHERE (1=1) ");
		sqlDoc.append(" AND T.C_BPartner_ID = ?");
		sqlDoc.append(" GROUP BY T.c_bpartner_id, T.name, T.C_BP_Group_ID, T.so_description, T.totalopenbalance ");
		sqlDoc.append(" ORDER BY ");
		if ("BP".equals(p_Sort_Criteria))
			sqlDoc.append("T.name");
		if ("BL".equals(p_Sort_Criteria))
			sqlDoc.append("balance");		
		if ("OI".equals(p_Sort_Criteria))
			sqlDoc.append("fecha_fact_antigua");		
		
		// ejecutar la consulta y cargar la tabla temporal
		PreparedStatement pstmt = DB.prepareStatement(sqlDoc.toString(), get_TrxName(), true);
		int i = 1;
		// Parámetros de sqlDoc
		pstmt.setTimestamp(i++,
				p_Fecha != null ? p_Fecha : Env.getDate());
		pstmt.setTimestamp(i++,
				p_Fecha != null ? p_Fecha : Env.getDate());
		if(p_Fecha != null){
			pstmt.setTimestamp(i++, p_Fecha);
		}
		
		pstmt.setInt(i++, C_BPartner_ID);
		
		ResultSet rs = pstmt.executeQuery();
		
		BigDecimal res = Env.ZERO;
		
		if (rs.next())
		{
			res = rs.getBigDecimal("Balance");
		}

		
		return res;
		
	}
	

}
