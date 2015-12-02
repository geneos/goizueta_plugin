package ar.com.geneos.goizueta.plugin.client.pos.custom;

import java.math.BigDecimal;

import org.openXpertya.pos.model.Product;
import org.openXpertya.pos.model.Tax;

import ar.com.geneos.goizueta.plugin.client.pos.model.OrderProduct;

public class CGOrderProduct extends OrderProduct {

	private String senderDetails = null;

	public CGOrderProduct(BigDecimal count, BigDecimal discount,
			Tax tax, Product product, String checkoutPlace){
		super(count,discount,tax,product,checkoutPlace);
		this.setSenderDetails("");
	}

	public String getSenderDetails() {
		return senderDetails;
	}

	public void setSenderDetails(String senderDetails) {
		this.senderDetails = senderDetails;
	}

}
