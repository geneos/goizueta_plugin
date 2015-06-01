/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.goizueta.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.M_Table;
import org.openXpertya.model.POInfo;
import org.openXpertya.util.KeyNamePair;

/**
 * Modelo Generado por CG_Parameter
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-01 17:13:36.085
 */
public class LP_CG_Parameter extends org.openXpertya.model.PO {

	private static final long serialVersionUID = -3407246155587705759L;

	/** Constructor estándar */
	public LP_CG_Parameter(Properties ctx, int CG_Parameter_ID, String trxName) {
		super(ctx, CG_Parameter_ID, trxName);
		/**
		 * if (CG_Parameter_ID == 0) { setCG_Parameter_ID (0); setName (null);
		 * setValue (null); }
		 */
	}

	/** Load Constructor */
	public LP_CG_Parameter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("CG_Parameter");

	/** TableName=CG_Parameter */
	public static final String Table_Name = "CG_Parameter";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "CG_Parameter");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_CG_Parameter[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set CG_Parameter_ID */
	public void setCG_Parameter_ID(int CG_Parameter_ID) {
		set_ValueNoCheck("CG_Parameter_ID", new Integer(CG_Parameter_ID));
	}

	/** Get CG_Parameter_ID */
	public int getCG_Parameter_ID() {
		Integer ii = (Integer) get_Value("CG_Parameter_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Description. Optional short description of the record
	 */
	public void setDescription(String Description) {
		if (Description != null && Description.length() > 255) {
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 255);
		}
		set_Value("Description", Description);
	}

	/**
	 * Get Description. Optional short description of the record
	 */
	public String getDescription() {
		return (String) get_Value("Description");
	}

	/**
	 * Set Name. Alphanumeric identifier of the entity
	 */
	public void setName(String Name) {
		if (Name == null)
			throw new IllegalArgumentException("Name is mandatory");
		if (Name.length() > 30) {
			log.warning("Length > 30 - truncated");
			Name = Name.substring(0, 30);
		}
		set_Value("Name", Name);
	}

	/**
	 * Get Name. Alphanumeric identifier of the entity
	 */
	public String getName() {
		return (String) get_Value("Name");
	}

	public KeyNamePair getKeyNamePair() {
		return new KeyNamePair(getID(), getName());
	}

	public void setParamValue(String ParamValue) {
		if (ParamValue == null)
			throw new IllegalArgumentException("ParamValue is mandatory");
		if (ParamValue.length() > 40) {
			log.warning("Length > 40 - truncated");
			ParamValue = ParamValue.substring(0, 40);
		}
		set_Value("ParamValue", ParamValue);
	}

	public String getParamValue() {
		return (String) get_Value("ParamValue");
	}
}
