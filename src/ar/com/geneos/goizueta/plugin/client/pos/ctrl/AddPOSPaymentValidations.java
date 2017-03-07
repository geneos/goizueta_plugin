package ar.com.geneos.goizueta.plugin.client.pos.ctrl;

import java.math.BigDecimal;

import org.openXpertya.reflection.CallResult;

import ar.com.geneos.goizueta.plugin.client.pos.view.PoSMainForm;

public class AddPOSPaymentValidations {

	public CallResult validateCashPayment(PoSMainForm pmf){
		CallResult result = new CallResult();
		return result;
	}
	
	public CallResult validateCheckPayment(PoSMainForm pmf, BigDecimal amount){
		CallResult result = new CallResult();
		return result;
	}
	
	public CallResult validateCreditPayment(PoSMainForm pmf){
		CallResult result = new CallResult();
		return result;
	}
	
	public CallResult validateCreditCardPayment(PoSMainForm pmf, String creditCardNumber, String couponNumber, String couponBatchNumber, BigDecimal cashRetirement){
		CallResult result = new CallResult();
		return result;
	}
	
	public CallResult validateCreditNotePayment(PoSMainForm pmf, BigDecimal balanceAmt, boolean returnCash, BigDecimal returnCashAmt){
		CallResult result = new CallResult();
		return result;
	}
	
	public CallResult validateDirectDepositPayment(PoSMainForm pmf){
		CallResult result = new CallResult();
		return result;
	}
}
