package ar.com.geneos.goizueta.plugin.callout;

import java.util.Properties;

import org.openXpertya.model.MBPartner;
import org.openXpertya.model.MField;
import org.openXpertya.model.MTab;
import org.openXpertya.plugin.CalloutPluginEngine;
import org.openXpertya.plugin.MPluginStatusCallout;
import org.openXpertya.util.Env;

import ar.com.geneos.goizueta.plugin.model.MCGParameter;

/**
 * Descripción de Clase
 *
 *
 * @version 2.2, 12.10.07
 * @author Equipo de Desarrollo de openXpertya
 */

public class CalloutProyect extends CalloutPluginEngine {

	public MPluginStatusCallout bPartnerGroup(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);

		if (isCalloutActive()) {
			return state;
		}
		boolean show = false;

		if ((value != null)) {

			setCalloutActive(true);
			int C_BP_Group_ID = 0;
			System.out.println(value);

			// Obtengo id del partner seleccionado
			int C_BPartner_ID = (Integer) value;

			if (C_BPartner_ID != 0) {
				// Obtengo grupo
				MBPartner bPartner = new MBPartner(ctx, C_BPartner_ID, null);
				C_BP_Group_ID = bPartner.getC_BP_Group_ID();

				// Verifico si el grupo pertenece al grupo de
				// 'thirdCarrierGroupID'
				int thirdCarrierGroupID = Integer.valueOf(MCGParameter.getValueForName(Env.getCtx(), MCGParameter.THIRD_CARRIER_GROUP_ID, null));

				if (thirdCarrierGroupID == C_BP_Group_ID)
					show = true;
			}
		}
		mTab.getField("cg_carrier_rate").setDisplayed(show);

		setCalloutActive(false);
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state;
	}

}
