package ar.com.geneos.goizueta.plugin.client.pos.custom;

import org.openXpertya.model.MInvoice;
import org.openXpertya.pos.exceptions.PosException;

import ar.com.geneos.goizueta.plugin.client.pos.ctrl.PoSOnline;
import ar.com.geneos.goizueta.plugin.client.pos.model.Order;

public class CGPoSOnline extends PoSOnline {

	protected MInvoice createOxpInvoice(Order order) throws PosException {
		return super.createOxpInvoice(order);
	}
}
