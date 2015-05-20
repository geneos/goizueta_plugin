/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.goizueta.plugin.model;

import org.openXpertya.model.*;

import java.util.logging.Level;
import java.util.*;
import java.sql.*;
import java.math.*;

import org.openXpertya.util.*;

/**
 * Modelo Generado por CG_Trip_Point
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-05-20 10:44:38.417
 */
public class LP_CG_Trip_Point extends org.openXpertya.model.PO {

	private static final long serialVersionUID = -3631864281578571431L;

	/** Constructor estándar */
	public LP_CG_Trip_Point(Properties ctx, int CG_Trip_Point_ID, String trxName) {
		super(ctx, CG_Trip_Point_ID, trxName);
		/**
		 * if (CG_Trip_Point_ID == 0) { setCG_Trip_Point_ID (0);
		 * setC_Location_ID (0); setisdestination (false); setisorigin (false);
		 * setName (null); }
		 */
	}

	/** Load Constructor */
	public LP_CG_Trip_Point(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("CG_Trip_Point");

	/** TableName=CG_Trip_Point */
	public static final String Table_Name = "CG_Trip_Point";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "CG_Trip_Point");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_CG_Trip_Point[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set CG_Trip_Point_ID */
	public void setCG_Trip_Point_ID(int CG_Trip_Point_ID) {
		set_ValueNoCheck("CG_Trip_Point_ID", new Integer(CG_Trip_Point_ID));
	}

	/** Get CG_Trip_Point_ID */
	public int getCG_Trip_Point_ID() {
		Integer ii = (Integer) get_Value("CG_Trip_Point_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Address. Location or Address
	 */
	public void setC_Location_ID(int C_Location_ID) {
		set_Value("C_Location_ID", new Integer(C_Location_ID));
	}

	/**
	 * Get Address. Location or Address
	 */
	public int getC_Location_ID() {
		Integer ii = (Integer) get_Value("C_Location_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set isdestination */
	public void setisdestination(boolean isdestination) {
		set_Value("isdestination", new Boolean(isdestination));
	}

	/** Get isdestination */
	public boolean isdestination() {
		Object oo = get_Value("isdestination");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isorigin */
	public void setisorigin(boolean isorigin) {
		set_Value("isorigin", new Boolean(isorigin));
	}

	/** Get isorigin */
	public boolean isorigin() {
		Object oo = get_Value("isorigin");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Name. Alphanumeric identifier of the entity
	 */
	public void setName(String Name) {
		if (Name == null)
			throw new IllegalArgumentException("Name is mandatory");
		if (Name.length() > 60) {
			log.warning("Length > 60 - truncated");
			Name = Name.substring(0, 60);
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
}
