package ar.com.geneos.goizueta.plugin.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.Query;

public class MCGParameter extends LP_CG_Parameter {

	private static final long serialVersionUID = -3626003483376845602L;

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
