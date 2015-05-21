/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.goizueta.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.MReference;
import org.openXpertya.model.M_Table;
import org.openXpertya.model.POInfo;
import org.openXpertya.util.KeyNamePair;

/**
 * Modelo Generado por CG_Trip
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-05-21 14:48:00.862
 */
public class LP_CG_Trip extends org.openXpertya.model.PO {

	private static final long serialVersionUID = -7805903390331031156L;

	/** Constructor estándar */
	public LP_CG_Trip(Properties ctx, int CG_Trip_ID, String trxName) {
		super(ctx, CG_Trip_ID, trxName);
		/**
		 * if (CG_Trip_ID == 0) { setCG_Trip_ID (0);
		 * setCG_Trip_Point_Destination_ID (0); setCG_Trip_Point_Origin_ID (0);
		 * setdistance (0); setfinished (null); setName (null); }
		 */
	}

	/** Load Constructor */
	public LP_CG_Trip(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("CG_Trip");

	/** TableName=CG_Trip */
	public static final String Table_Name = "CG_Trip";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "CG_Trip");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_CG_Trip[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set CG_Trip_ID */
	public void setCG_Trip_ID(int CG_Trip_ID) {
		set_ValueNoCheck("CG_Trip_ID", new Integer(CG_Trip_ID));
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
		set_Value("CG_Trip_Point_Origin_ID", new Integer(CG_Trip_Point_Origin_ID));
	}

	/** Get CG_Trip_Point_Origin_ID */
	public int getCG_Trip_Point_Origin_ID() {
		Integer ii = (Integer) get_Value("CG_Trip_Point_Origin_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Project. Financial Project
	 */
	public void setC_Project_ID(int C_Project_ID) {
		if (C_Project_ID <= 0)
			set_Value("C_Project_ID", null);
		else
			set_Value("C_Project_ID", new Integer(C_Project_ID));
	}

	/**
	 * Get Project. Financial Project
	 */
	public int getC_Project_ID() {
		Integer ii = (Integer) get_Value("C_Project_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set distance */
	public void setdistance(int distance) {
		set_Value("distance", new Integer(distance));
	}

	/** Get distance */
	public int getdistance() {
		Integer ii = (Integer) get_Value("distance");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set finished */
	public void setfinished(String finished) {
		if (finished == null)
			throw new IllegalArgumentException("finished is mandatory");
		if (finished.length() > 1) {
			log.warning("Length > 1 - truncated");
			finished = finished.substring(0, 1);
		}
		set_Value("finished", finished);
	}

	/** Get finished */
	public String getfinished() {
		return (String) get_Value("finished");
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
}
