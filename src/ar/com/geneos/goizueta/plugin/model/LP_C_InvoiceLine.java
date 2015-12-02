/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.goizueta.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.MReference;
import org.openXpertya.util.Env;

/**
 * Modelo Generado por C_InvoiceLine
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-05-26 17:50:26.628
 */
public class LP_C_InvoiceLine extends org.openXpertya.model.MInvoiceLine {

	/** Constructor estándar */
	public LP_C_InvoiceLine(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		/**
		 * if (C_Invoice_ID == 0) { }
		 */
	}

	/** Load Constructor */
	public LP_C_InvoiceLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_C_InvoiceLine[").append(getID()).append("]");
		return sb.toString();
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
}
