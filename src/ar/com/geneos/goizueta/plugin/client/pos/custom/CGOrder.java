package ar.com.geneos.goizueta.plugin.client.pos.custom;

import java.math.BigDecimal;

import org.openXpertya.pos.model.Organization;

import ar.com.geneos.goizueta.plugin.client.pos.model.Order;

public class CGOrder extends Order {

	private int cg_trip_id = 0;
	private int cg_origin_id = 0;
	private int cg_destination_id = 0;
	private BigDecimal declaredValue = BigDecimal.ZERO;
	private String senderDetails = null;

	public CGOrder(Organization organization) {
		super(organization);
	}

	public int getCg_trip_id() {
		return cg_trip_id;
	}

	public void setCg_trip_id(int cg_trip_id) {
		this.cg_trip_id = cg_trip_id;
	}

	public int getCg_origin_id() {
		return cg_origin_id;
	}

	public void setCg_origin_id(int cg_origin_id) {
		this.cg_origin_id = cg_origin_id;
	}

	public int getCg_destination_id() {
		return cg_destination_id;
	}

	public void setCg_destination_id(int cg_destination_id) {
		this.cg_destination_id = cg_destination_id;
	}

	public BigDecimal getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(BigDecimal declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getSenderDetails() {
		return senderDetails;
	}

	public void setSenderDetails(String senderDetails) {
		this.senderDetails = senderDetails;
	}

}
