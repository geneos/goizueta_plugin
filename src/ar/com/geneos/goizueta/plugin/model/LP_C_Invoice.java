/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.goizueta.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.MReference;
import org.openXpertya.util.Env;

/**
 * Modelo Generado por C_Invoice
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-05-26 17:50:26.628
 */
public class LP_C_Invoice extends org.openXpertya.model.MInvoice {

	private static final long serialVersionUID = 7573498390514983609L;

	/** Constructor estándar */
	public LP_C_Invoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		/**
		 * if (C_Invoice_ID == 0) { }
		 */
	}

	/** Load Constructor */
	public LP_C_Invoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_C_Invoice[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set cg_aditional_per_value */
	public void setcg_aditional_per_value(BigDecimal cg_aditional_per_value) {
		set_Value("cg_aditional_per_value", cg_aditional_per_value);
	}

	/** Get cg_aditional_per_value */
	public BigDecimal getcg_aditional_per_value() {
		BigDecimal bd = (BigDecimal) get_Value("cg_aditional_per_value");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cg_declared_value */
	public void setcg_declared_value(BigDecimal cg_declared_value) {
		set_Value("cg_declared_value", cg_declared_value);
	}

	/** Get cg_declared_value */
	public BigDecimal getcg_declared_value() {
		BigDecimal bd = (BigDecimal) get_Value("cg_declared_value");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cg_sender_details */
	public void setcg_sender_details(String cg_sender_details) {
		if (cg_sender_details != null && cg_sender_details.length() > 80) {
			log.warning("Length > 80 - truncated");
			cg_sender_details = cg_sender_details.substring(0, 80);
		}
		set_Value("cg_sender_details", cg_sender_details);
	}

	/** Get cg_sender_details */
	public String getcg_sender_details() {
		return (String) get_Value("cg_sender_details");
	}

	/** Set CG_Trip_ID */
	public void setCG_Trip_ID(int CG_Trip_ID) {
		if (CG_Trip_ID <= 0)
			set_Value("CG_Trip_ID", null);
		else
			set_Value("CG_Trip_ID", new Integer(CG_Trip_ID));
	}

	/** Get CG_Trip_ID */
	public int getCG_Trip_ID() {
		Integer ii = (Integer) get_Value("CG_Trip_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int CG_TRIP_POINT_DESTINATION_ID_AD_Reference_ID = MReference.getReferenceID("Trip Points (Destination)");

	/** Set CG_Trip_Point_Destination_ID */
	public void setCG_Trip_Point_Destination_ID(int CG_Trip_Point_Destination_ID) {
		if (CG_Trip_Point_Destination_ID <= 0)
			set_Value("CG_Trip_Point_Destination_ID", null);
		else
			set_Value("CG_Trip_Point_Destination_ID", new Integer(CG_Trip_Point_Destination_ID));
	}

	/** Get CG_Trip_Point_Destination_ID */
	public int getCG_Trip_Point_Destination_ID() {
		Integer ii = (Integer) get_Value("CG_Trip_Point_Destination_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int CG_TRIP_POINT_ORIGIN_ID_AD_Reference_ID = MReference.getReferenceID("Trip Points (Origin)");

	/** Set CG_Trip_Point_Origin_ID */
	public void setCG_Trip_Point_Origin_ID(int CG_Trip_Point_Origin_ID) {
		if (CG_Trip_Point_Origin_ID <= 0)
			set_Value("CG_Trip_Point_Origin_ID", null);
		else
			set_Value("CG_Trip_Point_Origin_ID", new Integer(CG_Trip_Point_Origin_ID));
	}

	/** Get CG_Trip_Point_Origin_ID */
	public int getCG_Trip_Point_Origin_ID() {
		Integer ii = (Integer) get_Value("CG_Trip_Point_Origin_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
