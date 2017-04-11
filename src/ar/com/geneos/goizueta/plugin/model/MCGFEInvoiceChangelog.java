package ar.com.geneos.goizueta.plugin.model;

import java.util.Properties;

import org.openXpertya.model.MInvoice;

public class MCGFEInvoiceChangelog extends LP_CGFE_Invoice_Changelog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7322461328443970528L;

	public MCGFEInvoiceChangelog(Properties ctx, int CGFE_Invoice_Changelog_ID, String trxName) {
		super(ctx, CGFE_Invoice_Changelog_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MCGFEInvoiceChangelog(Properties ctx, MInvoice mInvoice, String trxName) {
		this(ctx,0,trxName);
		setC_Invoice_ID(mInvoice.getC_Invoice_ID());
		setDocumentNo(mInvoice.getDocumentNo());
	}

}
