package ar.com.geneos.goizueta.plugin.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.Query;

public class MCGParameter extends LP_CG_Parameter {

	private static final long serialVersionUID = -3626003483376845602L;
	public static final String THIRD_CARRIER_GROUP_ID = "IDGrupoCamionesTerceros";
	public static final String OWN_CARRIER_GROUP_ID = "IDGrupoCamionesPropios";
	public static final String APV_PRODUCT_ID = "IdentificadorAdicionalPorValor";
	public static final String APV_RATE = "ProporcionAdicionalPorValor";

	public MCGParameter(Properties ctx, int CG_Trip_Point_ID, String trxName) {
		super(ctx, CG_Trip_Point_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MCGParameter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static String getValueForName(Properties ctx, String name, String trx) {
		MCGParameter param = new Query(ctx, MCGParameter.Table_Name, "isactive = 'Y' and name = '" + name + "'", trx).<MCGParameter> first();
		if (param == null)
			return null;
		return param.getParamValue();
	}

}
