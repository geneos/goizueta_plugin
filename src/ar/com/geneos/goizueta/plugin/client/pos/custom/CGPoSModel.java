package ar.com.geneos.goizueta.plugin.client.pos.custom;

import ar.com.geneos.goizueta.plugin.client.pos.ctrl.PoSModel;

public class CGPoSModel extends PoSModel {

	public void setIntoOnlineMode() {
		setConnectionState(new CGPoSOnline());
	}
}
