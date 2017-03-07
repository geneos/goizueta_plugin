package ar.com.geneos.goizueta.plugin.client.pos.custom;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.openXpertya.apps.AUserAuth;
import org.openXpertya.grid.ed.VNumber;
import org.openXpertya.pos.view.KeyUtils;
import org.openXpertya.swing.util.FocusUtils;
import org.openXpertya.util.DisplayType;

import ar.com.geneos.goizueta.plugin.client.pos.model.OrderProduct;
import ar.com.geneos.goizueta.plugin.client.pos.view.UpdateOrderProductDialog;

public class CGUpdateOrderProductDialog extends UpdateOrderProductDialog {

	private CLabel cProductPriceBaseLabel = null;
	protected VNumber cPriceBaseText = null;
	private String MSG_BASE_PRICE;

	
	public CGUpdateOrderProductDialog(OrderProduct orderProduct, PosMainForm posMainForm) {
		super(orderProduct,posMainForm);
	}


	@Override
	public void initMsgs() {
		super.initMsgs();
		MSG_BASE_PRICE = getMsg("Base_Price");
	}
	
	
	/**
	 * This method initializes cItemPanel
	 * 
	 * @return org.compiere.swing.CPanel
	 */
	@Override
	protected CPanel getCItemPanel() {
		if (cItemPanel == null) {
			final int V_SPAN = 3;

			GridBagConstraints gbc0603 = new GridBagConstraints();
			gbc0603.gridx = 3;
			gbc0603.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0603.anchor = java.awt.GridBagConstraints.WEST;
			gbc0603.gridy = 6;
			gbc0603.gridwidth = 2;
			GridBagConstraints gbc0602 = new GridBagConstraints();
			gbc0602.gridx = 2;
			gbc0602.anchor = java.awt.GridBagConstraints.WEST;
			gbc0602.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0602.gridy = 6;

			GridBagConstraints gbc0601 = new GridBagConstraints();
			gbc0601.fill = java.awt.GridBagConstraints.NONE;
			gbc0601.gridy = 6;
			gbc0601.anchor = java.awt.GridBagConstraints.WEST;
			gbc0601.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0601.gridx = 1;
			GridBagConstraints gbc0600 = new GridBagConstraints();
			gbc0600.gridx = 0;
			gbc0600.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0600.anchor = java.awt.GridBagConstraints.WEST;
			gbc0600.gridy = 6;

			GridBagConstraints gbc0503 = new GridBagConstraints();
			gbc0503.gridx = 3;
			gbc0503.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0503.anchor = java.awt.GridBagConstraints.WEST;
			gbc0503.gridy = 5;
			gbc0503.gridwidth = 2;
			GridBagConstraints gbc0502 = new GridBagConstraints();
			gbc0502.gridx = 2;
			gbc0502.anchor = java.awt.GridBagConstraints.WEST;
			gbc0502.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0502.gridy = 5;

			GridBagConstraints gbc0501 = new GridBagConstraints();
			gbc0501.fill = java.awt.GridBagConstraints.NONE;
			gbc0501.gridy = 5;
			gbc0501.anchor = java.awt.GridBagConstraints.WEST;
			gbc0501.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0501.gridx = 1;
			GridBagConstraints gbc0500 = new GridBagConstraints();
			gbc0500.gridx = 0;
			gbc0500.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0500.anchor = java.awt.GridBagConstraints.WEST;
			gbc0500.gridy = 8;
			gbc0500.gridwidth = 4;
			gbc0500.insets = new java.awt.Insets(10, 0, 3, 0);

			GridBagConstraints gbc0303 = new GridBagConstraints();
			gbc0303.gridx = 3;
			gbc0303.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0303.anchor = java.awt.GridBagConstraints.WEST;
			gbc0303.gridy = 3;
			gbc0303.gridwidth = 2;
			GridBagConstraints gbc0302 = new GridBagConstraints();
			gbc0302.gridx = 2;
			gbc0302.anchor = java.awt.GridBagConstraints.WEST;
			gbc0302.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0302.gridy = 3;
			gbc0302.gridwidth = 3;
			cApplicationLabel = new CLabel();
			cApplicationLabel.setText(MSG_APPLICATION);

			GridBagConstraints gbc0401 = new GridBagConstraints();
			gbc0401.fill = java.awt.GridBagConstraints.NONE;
			gbc0401.gridy = 4;
			gbc0401.weightx = 1.0;
			gbc0401.anchor = java.awt.GridBagConstraints.WEST;
			gbc0401.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0401.gridx = 1;
			GridBagConstraints gbc0400 = new GridBagConstraints();
			gbc0400.gridx = 0;
			gbc0400.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0400.anchor = java.awt.GridBagConstraints.WEST;
			gbc0400.gridy = 4;

			GridBagConstraints gbc0201 = new GridBagConstraints();
			gbc0201.fill = java.awt.GridBagConstraints.NONE;
			gbc0201.gridy = 2;
			gbc0201.weightx = 1.0;
			gbc0201.anchor = java.awt.GridBagConstraints.WEST;
			gbc0201.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0201.gridx = 1;
			GridBagConstraints gbc0200 = new GridBagConstraints();
			gbc0200.gridx = 0;
			gbc0200.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0200.anchor = java.awt.GridBagConstraints.WEST;
			gbc0200.gridy = 2;
			gbc0200.gridwidth = 4;
			gbc0200.insets = new java.awt.Insets(10, 0, 3, 0);
			
			
			GridBagConstraints gbc0105 = new GridBagConstraints();
			gbc0105.gridx = 5;
			gbc0105.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0105.anchor = java.awt.GridBagConstraints.WEST;
			gbc0105.gridy = 1;
			
			cProductTaxedPriceLabel = new CLabel();
			cProductTaxedPriceLabel.setText(MSG_TAXED_PRICE);
			GridBagConstraints gbc0104 = new GridBagConstraints();
			gbc0104.gridx = 4;
			gbc0104.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0104.anchor = java.awt.GridBagConstraints.WEST;
			gbc0104.gridy = 1;
			
			cProductPriceBaseLabel = new CLabel();
			cProductPriceBaseLabel.setText(MSG_BASE_PRICE);
			GridBagConstraints gbc0103 = new GridBagConstraints();
			gbc0103.gridx = 3;
			gbc0103.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0103.anchor = java.awt.GridBagConstraints.WEST;
			gbc0103.gridy = 1;
			
			GridBagConstraints gbc0102 = new GridBagConstraints();
			gbc0102.gridx = 2;
			gbc0102.anchor = java.awt.GridBagConstraints.WEST;
			gbc0102.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0102.gridy = 1;
			cProductTaxRateLabel = new CLabel();
			cProductTaxRateLabel.setText(MSG_TAXRATE);
			
			GridBagConstraints gbc0403 = new GridBagConstraints();
			gbc0403.fill = java.awt.GridBagConstraints.NONE;
			gbc0403.gridy = 4;
			gbc0403.weightx = 1.0;
			gbc0403.anchor = java.awt.GridBagConstraints.WEST;
			gbc0403.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0403.gridwidth = 3;
			gbc0403.gridx = 3;
			GridBagConstraints gbc0402 = new GridBagConstraints();
			gbc0402.gridx = 2;
			gbc0402.anchor = java.awt.GridBagConstraints.WEST;
			gbc0402.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0402.gridy = 4;
			// gbc0402.gridwidth = 3;
			GridBagConstraints gbc0301 = new GridBagConstraints();
			gbc0301.fill = java.awt.GridBagConstraints.NONE;
			gbc0301.gridy = 3;
			gbc0301.weightx = 1.0;
			gbc0301.anchor = java.awt.GridBagConstraints.WEST;
			gbc0301.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0301.gridx = 1;
			GridBagConstraints gbc0300 = new GridBagConstraints();
			gbc0300.gridx = 0;
			gbc0300.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0300.anchor = java.awt.GridBagConstraints.WEST;
			gbc0300.gridy = 3;
			GridBagConstraints gbc0205 = new GridBagConstraints();
			gbc0205.fill = java.awt.GridBagConstraints.NONE;
			gbc0205.gridy = 2;
			gbc0205.weightx = 1.0;
			gbc0205.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0205.anchor = java.awt.GridBagConstraints.WEST;
			gbc0205.gridx = 5;
			GridBagConstraints gbc0204 = new GridBagConstraints();
			gbc0204.gridx = 4;
			gbc0204.insets = new java.awt.Insets(V_SPAN, 10, 0, 0);
			gbc0204.anchor = java.awt.GridBagConstraints.WEST;
			gbc0204.gridy = 2;
			cCountLabel = new CLabel();
			cCountLabel.setText(MSG_COUNT);
			GridBagConstraints gbc0203 = new GridBagConstraints();
			gbc0203.gridx = 3;
			gbc0203.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0203.anchor = java.awt.GridBagConstraints.WEST;
			gbc0203.gridy = 2;
			GridBagConstraints gbc0202 = new GridBagConstraints();
			gbc0202.gridx = 2;
			gbc0202.anchor = java.awt.GridBagConstraints.WEST;
			gbc0202.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0202.gridy = 2;
			cDiscountLabel = new CLabel();
			cDiscountLabel.setText(MSG_DISCOUNT);
			GridBagConstraints gbc0101 = new GridBagConstraints();
			gbc0101.gridx = 1;
			gbc0101.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			gbc0101.anchor = java.awt.GridBagConstraints.WEST;
			gbc0101.gridy = 1;
			GridBagConstraints gbc0100 = new GridBagConstraints();
			gbc0100.gridx = 0;
			gbc0100.insets = new java.awt.Insets(V_SPAN, 0, 0, 0);
			gbc0100.anchor = java.awt.GridBagConstraints.WEST;
			gbc0100.gridy = 1;
			cProductPriceLabel = new CLabel();
			cProductPriceLabel.setText(MSG_PRICE);
			GridBagConstraints gbc0001 = new GridBagConstraints();
			gbc0001.gridx = 1;
			gbc0001.insets = new java.awt.Insets(0, 5, 5, 0);
			gbc0001.gridwidth = 5;
			gbc0001.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gbc0001.gridy = 0;
			cProductDescLabel = new CLabel();
			cProductDescLabel.setText("<html>" + getOrderProduct().getProduct().getDescription() + "</html>");
			cProductDescLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			GridBagConstraints gbc0000 = new GridBagConstraints();
			gbc0000.gridx = 0;
			gbc0000.anchor = java.awt.GridBagConstraints.WEST;
			gbc0000.gridy = 0;
			gbc0000.insets = new java.awt.Insets(0, 0, 5, 0);
			cProductLabel = new CLabel();
			cProductLabel.setText(MSG_PRODUCT + ":");
			cProductLabel.setFontBold(true);
			CLabel cPriceListLabel = new CLabel();
			cPriceListLabel.setText(MSG_PRICE_LIST);
			CLabel cManualDiscountTitleLabel = new CLabel();
			cManualDiscountTitleLabel.setText(MSG_MANUAL_DISCOUNT);
			cManualDiscountTitleLabel.setFontBold(true);
			CLabel cDiscountAmtLabel = new CLabel();
			cDiscountAmtLabel.setText(MSG_AMOUNT);
			CLabel cAuthTitleLabel = new CLabel();
			cAuthTitleLabel.setText(MSG_SUPERVISOR_AUTH);
			cAuthTitleLabel.setFontBold(true);

			// Título de descripción de línea
			CLabel cLineDescriptionTitleLabel = new CLabel();
			cLineDescriptionTitleLabel.setText(MSG_LINE_DESCRIPTION_TITLE);
			cLineDescriptionTitleLabel.setFontBold(true);
			GridBagConstraints gbc0700 = new GridBagConstraints();
			gbc0700.gridx = 0;
			gbc0700.anchor = java.awt.GridBagConstraints.WEST;
			gbc0700.gridy = 6;
			gbc0700.gridwidth = 4;
			gbc0700.insets = new java.awt.Insets(10, 0, 3, 0);
			// Label de Descripción
			cLineDescriptionLabel = new CLabel();
			cLineDescriptionLabel.setText(MSG_DESCRIPTION);
			GridBagConstraints gbc0800 = new GridBagConstraints();
			gbc0800.gridx = 0;
			gbc0800.anchor = java.awt.GridBagConstraints.WEST;
			gbc0800.gridy = 7;
			gbc0800.insets = new java.awt.Insets(V_SPAN, 0, 5, 0);
			// Descripción
			GridBagConstraints gbc0801 = new GridBagConstraints();
			gbc0801.gridx = 1;
			gbc0801.anchor = java.awt.GridBagConstraints.WEST;
			gbc0801.gridy = 7;
			gbc0801.insets = new java.awt.Insets(V_SPAN, 5, 5, 0);
			gbc0801.gridwidth = 5;
			gbc0801.fill = java.awt.GridBagConstraints.HORIZONTAL;

			cItemPanel = new CPanel();
			cItemPanel.setLayout(new GridBagLayout());
			cItemPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
					javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED),
					javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			cItemPanel.add(cProductLabel, gbc0000);
			cItemPanel.add(cProductDescLabel, gbc0001);
			// cItemPanel.add(cProductPriceLabel, gridBagConstraints2);
			// cItemPanel.add(getCProductPriceText(), gridBagConstraints3);
			// cItemPanel.add(cProductTaxRateLabel, gridBagConstraints12);
			// cItemPanel.add(getCProductTaxRateText(), gridBagConstraints13);
			
			/*GENEOS*/
			cItemPanel.add(cProductPriceBaseLabel, gbc0100);
			cItemPanel.add(getCProductBasePriceText(), gbc0101);
			
			cItemPanel.add(cProductTaxedPriceLabel, gbc0102);
			cItemPanel.add(getCProductTaxedPriceText(), gbc0103);
			cItemPanel.add(cCountLabel, gbc0104);
			cItemPanel.add(getCCountText(), gbc0105);
			
			cItemPanel.add(cManualDiscountTitleLabel, gbc0200);
			cItemPanel.add(cPriceListLabel, gbc0300);
			cItemPanel.add(getCPriceListText(), gbc0301);
			cItemPanel.add(getCApplicationPanel(), gbc0302);
			cItemPanel.add(cDiscountAmtLabel, gbc0402);
			cItemPanel.add(getCDiscountAmtText(), gbc0403);
			cItemPanel.add(cDiscountLabel, gbc0400);
			cItemPanel.add(getCDiscountText(), gbc0401);
			cItemPanel.add(cLineDescriptionTitleLabel, gbc0700);
			cItemPanel.add(cLineDescriptionLabel, gbc0800);
			cItemPanel.add(getCLineDescriptionText(), gbc0801);
			cItemPanel.add(cAuthTitleLabel, gbc0500);

			GridBagConstraints userAuthConstraints = new GridBagConstraints();
			userAuthConstraints.gridx = 0;
			userAuthConstraints.insets = new java.awt.Insets(V_SPAN, 5, 0, 0);
			userAuthConstraints.anchor = java.awt.GridBagConstraints.WEST;
			userAuthConstraints.gridy = 9;
			userAuthConstraints.gridwidth = 6;

			// Obtener la instancia del panel de autorización y el panel
			userAuthPanel = AUserAuth.get();
			userAuthPanel.setShortcutLabel(KeyUtils.getKeyStr(getActionKeys().get(CHANGE_FOCUS_USER_AUTH)));
			cItemPanel.add(userAuthPanel.getAuthPanel(), userAuthConstraints);

			// cItemPanel.add(cApplicationLabel, gridBagConstraints18);
			updateDiscountComponents();
			updateDiscountAmtText();
		}
		return cItemPanel;
	}
	
	protected VNumber getCProductBasePriceText() {
		if (cPriceBaseText == null) {
			cPriceBaseText = new VNumber();
			cPriceBaseText.setDisplayType(DisplayType.CostPrice);
			cPriceBaseText.setPreferredSize(new java.awt.Dimension(100, 20));
			cPriceBaseText.setValue(getOrderProduct().getPrice());
			cPriceBaseText.setMandatory(true);
			cPriceBaseText.setReadWrite(true);
			cPriceBaseText.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					VNumber target = (VNumber) e.getSource();
					//System.out.println(cPriceBaseText.getValue());
					//BigDecimal taxedPrice = 
					BigDecimal basePrice = (BigDecimal) target.getValue();
					if (basePrice != null) {
						OrderProduct op = getOrderProduct();
						// Se pisa el precio base del producto
						op.getProduct().setStdPrice(basePrice);
						op.setPrice(basePrice);

						getCProductTaxedPriceText().setValue(op.getTaxedPrice());
						getCPriceListText().setValue(op.getTaxedPriceList());
						updateDiscountAmtText();
					}
				}

			});
			FocusUtils.addFocusHighlight(cPriceBaseText);
		}
		return cPriceBaseText;
	}
	
} // @jve:decl-index=0:visual-constraint="10,10"
