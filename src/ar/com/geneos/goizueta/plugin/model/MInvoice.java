package ar.com.geneos.goizueta.plugin.model;

import java.util.Properties;
import org.openXpertya.model.PO;
import org.openXpertya.plugin.MPluginDocAction;
import org.openXpertya.plugin.MPluginStatusDocAction;
import org.openXpertya.process.DocAction;

public class MInvoice extends MPluginDocAction {

	public MInvoice(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
	}

	public MPluginStatusDocAction prePrepareIt(DocAction document) {
		return status_docAction;
	}

}