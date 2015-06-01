package ar.com.geneos.goizueta.plugin.client.pos.custom;

import org.openXpertya.model.MInvoice;
import org.openXpertya.pos.exceptions.InvoiceCreateException;
import org.openXpertya.pos.exceptions.PosException;

import ar.com.geneos.goizueta.plugin.client.pos.ctrl.PoSOnline;
import ar.com.geneos.goizueta.plugin.client.pos.model.Order;
import ar.com.geneos.goizueta.plugin.model.LP_C_Invoice;

public class CGPoSOnline extends PoSOnline {

	protected MInvoice createOxpInvoice(Order order) throws PosException {
		System.out.println("Override de PoSOnline");
		CGOrder cgOrder = (CGOrder) order;
		System.out.println(cgOrder);
		MInvoice inv = super.createOxpInvoice(order);
		LP_C_Invoice lpInv = new LP_C_Invoice(getCtx(),inv.getID(), getTrxName());
		
		lpInv.setCG_Trip_ID(cgOrder.getCg_trip_id());
		lpInv.setCG_Trip_Point_Origin_ID(cgOrder.getCg_origin_id());
		lpInv.setCG_Trip_Point_Destination_ID(cgOrder.getCg_destination_id());
		lpInv.setcg_declared_value(cgOrder.getDeclaredValue());
		lpInv.setcg_sender_details(cgOrder.getSenderDetails());
		
		throwIfFalse(lpInv.save(), lpInv, InvoiceCreateException.class);
		
		return inv;
	}
}
