package ar.com.geneos.goizueta.plugin.client.pos.custom;

import java.util.List;

import org.openXpertya.model.MInvoice;
import org.openXpertya.model.MInvoiceLine;
import org.openXpertya.pos.exceptions.InvoiceCreateException;
import org.openXpertya.pos.exceptions.PosException;

import ar.com.geneos.goizueta.plugin.client.pos.ctrl.PoSOnline;
import ar.com.geneos.goizueta.plugin.client.pos.model.Order;
import ar.com.geneos.goizueta.plugin.client.pos.model.OrderProduct;
import ar.com.geneos.goizueta.plugin.model.LP_C_Invoice;
import ar.com.geneos.goizueta.plugin.model.LP_C_InvoiceLine;

public class CGPoSOnline extends PoSOnline {

	protected MInvoice createOxpInvoice(Order order) throws PosException {
		CGOrder cgOrder = (CGOrder) order;

		MInvoice inv = super.createOxpInvoice(order);
		LP_C_Invoice lpInv = new LP_C_Invoice(getCtx(), inv.getID(), getTrxName());

		lpInv.setCG_Trip_ID(cgOrder.getCg_trip_id());
		lpInv.setCG_Trip_Point_Origin_ID(cgOrder.getCg_origin_id());
		lpInv.setCG_Trip_Point_Destination_ID(cgOrder.getCg_destination_id());
		lpInv.setcg_declared_value(cgOrder.getDeclaredValue());
		
		/* SenderDetails ahora es un campo en las lineas de la invoice*/
		//lpInv.setcg_sender_details(cgOrder.getSenderDetails());

		throwIfFalse(lpInv.save(), lpInv, InvoiceCreateException.class);
		
		MInvoiceLine[] lines = inv.getLines(true);
		List<OrderProduct> cgOrderLines = cgOrder.getOrderProducts();
		
		throwIfFalse(cgOrder.getOrderProducts().size() == lines.length, lpInv, InvoiceCreateException.class);
		
		for (int i = 0; i<lines.length ; i++ ) {
			LP_C_InvoiceLine lpInvLine = new LP_C_InvoiceLine(getCtx(), lines[i].getID(), getTrxName());
			lpInvLine.setcg_sender_details( ((CGOrderProduct)cgOrderLines.get(i)).getSenderDetails());
			throwIfFalse(lpInvLine.save(), lpInv, InvoiceCreateException.class);
		}

		return inv;
	}
}
