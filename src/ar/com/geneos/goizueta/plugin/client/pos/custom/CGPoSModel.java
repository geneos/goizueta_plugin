package ar.com.geneos.goizueta.plugin.client.pos.custom;

import java.math.BigDecimal;

import org.openXpertya.model.MProduct;
import org.openXpertya.pos.model.Product;
import org.openXpertya.pos.model.Tax;

import ar.com.geneos.goizueta.plugin.client.pos.ctrl.PoSModel;
import ar.com.geneos.goizueta.plugin.client.pos.model.Order;
import ar.com.geneos.goizueta.plugin.client.pos.model.OrderProduct;

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
	
	protected OrderProduct createOrderProduct(Product product) {
		OrderProduct op = super.createOrderProduct(product);
		CGOrderProduct cgop = new CGOrderProduct(op.getCount(),op.getDiscount(),op.getTax(),op.getProduct(),op.getCheckoutPlace());
		return cgop;
	}
}
