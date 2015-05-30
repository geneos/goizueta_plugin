package ar.com.geneos.goizueta.plugin.client.pos.custom;

import ar.com.geneos.goizueta.plugin.client.pos.ctrl.PoSModel;
import ar.com.geneos.goizueta.plugin.client.pos.model.Order;

public class CGPoSModel extends PoSModel {

	public void setIntoOnlineMode() {
		setConnectionState(new CGPoSOnline());
	}
	
	public void newOrder() {
		if(getOrder() == null) {
			setOrder(new CGOrder(getConnectionState().getOrganization()));
		}
		
		getOrder().clear();
		isCopyRep=true;
		getAddedCustomerOrders().clear();
	}
}
