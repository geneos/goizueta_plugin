package ar.com.geneos.goizueta.plugin.client.pos.custom;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.openXpertya.apps.form.VComponentsFactory;
import org.openXpertya.grid.ed.VLookup;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MProductPrice;
import org.openXpertya.model.Query;
import org.openXpertya.pos.model.Product;
import org.openXpertya.swing.util.FocusUtils;
import org.openXpertya.util.DisplayType;
import org.openXpertya.util.Env;
import org.openXpertya.util.Trx;

import ar.com.geneos.goizueta.plugin.client.pos.PoSMainForm;
import ar.com.geneos.goizueta.plugin.client.pos.model.OrderProduct;
import ar.com.geneos.goizueta.plugin.client.pos.view.table.ProductTableModel;
import ar.com.geneos.goizueta.plugin.model.MCGParameter;

public class PosMainForm extends PoSMainForm {

	private static final long serialVersionUID = -9151130246417888867L;

	// CONTROL
	private boolean cOrderTopPanelInitialized = false;

	// PANELS
	private CPanel cgTripPanel = null;
	private CPanel cgOriginDestinationPanel = null;
	private CPanel cgDeclaredValuePanel = null;
	private CPanel cgSenderDetailsPanel = null;

	// LABELS
	private CLabel cgTripPanelTripLabel = null;
	private CLabel cgTripPanelOriginLabel = null;
	private CLabel cgTripPanelDestinationLabel = null;
	private CLabel cgTripPanelDeclaredValueLabel = null;
	private CLabel cgTripPanelSenderDetailsLabel = null;

	// MESSAGES
	private String MSG_TRIP_DATA;
	private String MSG_TRIP;
	private String MSG_TRIP_ORIGIN;
	private String MSG_TRIP_DESTINATION;
	private String MSG_SENDER_DETAILS;
	private String MSG_DECLARED_VALUE;
	private String MSG_ERROR_MANDATORY;
	private String MSG_ADITIONAL_ADDED;
	private String MSG_ERROR_MISSING_APVPRODUCT;
	private String MSG_ERROR_MISSING_APVRATE;
	private String MSG_ERROR_APVPRODUCT_NOTFOUND;

	// Combos
	private VLookup cgTripCombo = null;
	private VLookup cgOriginCombo = null;
	private VLookup cgDestinationCombo = null;

	// INPUTS
	private CTextField cgDeclaredValueText = null;

	/**
	 * This method initializes, and overrides PoSModel whit CGPoSModel
	 * 
	 */
	public PosMainForm() {
		super();
		model = new CGPoSModel();
		model.setProcessListener(this);
	}

	/**
	 * This method overrides cProductTopPanel, adding CGTripPanel on top
	 * 
	 * @return org.compiere.swing.CPanel
	 */
	protected CPanel getCOrderTopPanel() {
		if (!cOrderTopPanelInitialized) {
			super.getCOrderTopPanel().add(getCGTripPanel(), null);
			super.getCOrderTopPanel().setPreferredSize(new java.awt.Dimension(0, 130));
			super.getCOrderTopPanel().setMaximumSize(new java.awt.Dimension(0, 130));
			cOrderTopPanelInitialized = true;
		}
		return super.getCOrderTopPanel();
	}

	private CPanel getCGTripPanel() {
		if (cgTripPanel == null) {

			cgTripPanel = new CPanel();
			cgTripPanel.setPreferredSize(new java.awt.Dimension(350, 90));

			cgTripPanel.setLayout(new GridBagLayout());
			cgTripPanel.setName("");
			cgTripPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray, 1), MSG_TRIP_DATA, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12), new java.awt.Color(51, 51, 51)),
					javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)));
			// cgTripPanel.set

			cgTripPanelTripLabel = new CLabel();
			cgTripPanelTripLabel.setPreferredSize(new java.awt.Dimension(35, 20));
			cgTripPanelTripLabel.setText(MSG_TRIP);

			cgTripPanelSenderDetailsLabel = new CLabel();
			cgTripPanelSenderDetailsLabel.setPreferredSize(new java.awt.Dimension(90, 20));
			cgTripPanelSenderDetailsLabel.setText(MSG_SENDER_DETAILS);

			cgTripPanelOriginLabel = new CLabel();
			cgTripPanelOriginLabel.setPreferredSize(new java.awt.Dimension(45, 20));
			cgTripPanelOriginLabel.setText(MSG_TRIP_ORIGIN);

			cgTripPanelDestinationLabel = new CLabel();
			cgTripPanelDestinationLabel.setPreferredSize(new java.awt.Dimension(50, 20));
			cgTripPanelDestinationLabel.setText(MSG_TRIP_DESTINATION);

			cgTripPanelDeclaredValueLabel = new CLabel();
			cgTripPanelDeclaredValueLabel.setPreferredSize(new java.awt.Dimension(80, 20));
			cgTripPanelDeclaredValueLabel.setText(MSG_DECLARED_VALUE);

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.insets = new java.awt.Insets(0, 0, 0, 0);
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.gridwidth = GridBagConstraints.REMAINDER;

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.insets = new java.awt.Insets(0, 0, 7, 0);
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.gridwidth = GridBagConstraints.REMAINDER;

			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
			gridBagConstraints00.gridx = 0;
			gridBagConstraints00.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints00.insets = new java.awt.Insets(0, 0, 7, 5);
			gridBagConstraints00.gridy = 0;

			GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
			gridBagConstraints01.gridy = 0;
			gridBagConstraints01.insets = new java.awt.Insets(0, 0, 7, 10);
			gridBagConstraints01.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints01.gridx = 1;

			GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
			gridBagConstraints02.gridy = 0;
			gridBagConstraints02.insets = new java.awt.Insets(0, 0, 7, 5);
			gridBagConstraints02.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints02.gridx = 2;

			GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
			gridBagConstraints03.gridy = 0;
			gridBagConstraints03.insets = new java.awt.Insets(2, 0, 7, 0);
			gridBagConstraints03.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints03.gridx = 3;

			cgTripPanel.add(cgTripPanelTripLabel, gridBagConstraints00);
			cgTripPanel.add(getCGTripCombo(), gridBagConstraints01);
			cgTripPanel.add(cgTripPanelDeclaredValueLabel, gridBagConstraints02);
			cgTripPanel.add(getCGDeclaredValuePanel(), gridBagConstraints03);

			cgTripPanel.add(getCGOriginDestinationPanel(), gridBagConstraints10);

			cgTripPanel.add(getCGSenderDetailsPanel(), gridBagConstraints20);
		}
		return cgTripPanel;
	}

	/**
	 * This method initializes cgTripCombo
	 * 
	 * @return org.compiere.swing.CComboBox
	 */
	private VLookup getCGTripCombo() {
		if (cgTripCombo == null) {
			cgTripCombo = VComponentsFactory.VLookupFactory("CG_Trip_ID", "CG_Trip", getWindowNo(), DisplayType.Table,
					"CG_Trip.finished = 'N' and CG_Trip.isactive='Y'", true);
			cgTripCombo.setPreferredSize(new java.awt.Dimension(160, 20));
			cgTripCombo.setValue(null);
			FocusUtils.addFocusHighlight(cgTripCombo);
		}
		return cgTripCombo;
	}

	/**
	 * This method initializes cgDestinationCombo
	 * 
	 * @return org.compiere.swing.CComboBox
	 */
	private VLookup getCGDestinationCombo() {
		if (cgDestinationCombo == null) {
			cgDestinationCombo = VComponentsFactory.VLookupFactory("CG_Trip_Point_ID", "CG_Trip_Point", getWindowNo(), DisplayType.Table,
					"CG_Trip_Point.isdestination = 'Y' and CG_Trip_Point.isactive='Y'", true);
			cgDestinationCombo.setPreferredSize(new java.awt.Dimension(125, 20));
			cgDestinationCombo.setValue(null);
			FocusUtils.addFocusHighlight(cgDestinationCombo);
		}
		return cgDestinationCombo;
	}

	/**
	 * This method initializes cgOriginCombo
	 * 
	 * @return org.compiere.swing.CComboBox
	 */
	private VLookup getCGOriginCombo() {
		if (cgOriginCombo == null) {
			cgOriginCombo = VComponentsFactory.VLookupFactory("CG_Trip_Point_ID", "CG_Trip_Point", getWindowNo(), DisplayType.Table,
					"CG_Trip_Point.isorigin = 'Y' and CG_Trip_Point.isactive='Y'", true);
			cgOriginCombo.setPreferredSize(new java.awt.Dimension(125, 20));
			cgOriginCombo.setValue(null);
			FocusUtils.addFocusHighlight(cgOriginCombo);
		}
		return cgOriginCombo;
	}

	/**
	 * This method initializes cProductCodePanel
	 * 
	 * @return org.compiere.swing.CPanel
	 */

	private CTextField getCGDeclaredValueText() {
		if (cgDeclaredValueText == null) {
			cgDeclaredValueText = new CTextField();
			cgDeclaredValueText.setPreferredSize(new java.awt.Dimension(65, 20));
			cgDeclaredValueText.setText("0");
			cgDeclaredValueText.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					char keyChar = e.getKeyChar();
					String countStr = cgDeclaredValueText.getText();

					// Si es punto y ya existe uno dentro del string, entonces
					// consumo
					if (keyChar == '.' && countStr.indexOf('.') > -1) {
						e.consume();
					}
					if (!Character.isDigit(keyChar) && keyChar != '.') {
						e.consume();
					}
					if ((!Character.isDigit(e.getKeyChar()) && countStr.length() == 0)) {
						e.consume();
						cgDeclaredValueText.setText("0");
						cgDeclaredValueText.selectAll();
					}
				}

				@Override
				public void keyReleased(KeyEvent event) {
					String countStr = cgDeclaredValueText.getText();
					if (countStr.length() > 1 && countStr.startsWith("0") && !countStr.startsWith("0.")) {
						cgDeclaredValueText.setText("0." + countStr.substring(1, countStr.length()));
					}
					if (countStr.startsWith(".")) {
						cgDeclaredValueText.setText("0" + countStr);
					}
				}

			});
			cgDeclaredValueText.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent event) {
					cgDeclaredValueText.selectAll();
				}

				public void focusLost(FocusEvent event) {

				}
			});
			FocusUtils.addFocusHighlight(cgDeclaredValueText);
		}
		return cgDeclaredValueText;
	}

	private CPanel getCGDeclaredValuePanel() {
		if (cgDeclaredValuePanel == null) {
			cgDeclaredValuePanel = new CPanel();
			cgDeclaredValuePanel.setLayout(new GridBagLayout());

			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
			gridBagConstraints00.gridx = 0;
			gridBagConstraints00.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints00.insets = new java.awt.Insets(2, 0, 0, 0);
			gridBagConstraints00.gridy = 0;

			cgDeclaredValuePanel.add(getCGDeclaredValueText(), gridBagConstraints00);

		}
		return cgDeclaredValuePanel;
	}

	private CPanel getCGOriginDestinationPanel() {
		if (cgOriginDestinationPanel == null) {
			cgOriginDestinationPanel = new CPanel();
			cgOriginDestinationPanel.setLayout(new GridBagLayout());

			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
			gridBagConstraints00.gridy = 0;
			gridBagConstraints00.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints00.insets = new java.awt.Insets(0, 0, 0, 5);
			gridBagConstraints00.gridx = 0;

			GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
			gridBagConstraints01.gridy = 0;
			gridBagConstraints01.insets = new java.awt.Insets(0, 0, 0, 5);
			gridBagConstraints01.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints01.gridx = 1;

			GridBagConstraints gridBagConstraints02 = new GridBagConstraints();
			gridBagConstraints02.gridy = 0;
			gridBagConstraints02.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints02.insets = new java.awt.Insets(0, 0, 0, 5);
			gridBagConstraints02.gridx = 2;

			GridBagConstraints gridBagConstraints03 = new GridBagConstraints();
			gridBagConstraints03.gridy = 0;
			gridBagConstraints03.insets = new java.awt.Insets(0, 0, 0, 0);
			gridBagConstraints03.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints03.gridx = 3;

			cgOriginDestinationPanel.add(cgTripPanelOriginLabel, gridBagConstraints00);
			cgOriginDestinationPanel.add(getCGOriginCombo(), gridBagConstraints01);
			cgOriginDestinationPanel.add(cgTripPanelDestinationLabel, gridBagConstraints02);
			cgOriginDestinationPanel.add(getCGDestinationCombo(), gridBagConstraints03);

		}
		return cgOriginDestinationPanel;
	}

	private CPanel getCGSenderDetailsPanel() {
		if (cgSenderDetailsPanel == null) {
			cgSenderDetailsPanel = new CPanel();
			cgSenderDetailsPanel.setLayout(new GridBagLayout());

			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
			gridBagConstraints00.gridy = 0;
			gridBagConstraints00.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints00.insets = new java.awt.Insets(0, 0, 7, 5);
			gridBagConstraints00.gridx = 0;

			GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
			gridBagConstraints01.gridy = 0;
			gridBagConstraints01.insets = new java.awt.Insets(0, 0, 7, 0);
			gridBagConstraints01.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints01.gridx = 1;

			cgSenderDetailsPanel.add(cgTripPanelSenderDetailsLabel, gridBagConstraints00);
		}
		return cgSenderDetailsPanel;
	}

	// Overrides initMsgs, adding new Messages
	protected void initMsgs() {
		super.initMsgs();
		MSG_TRIP_DATA = getMsg("CG_TripData");
		MSG_TRIP = getMsg("CG_Trip");
		MSG_SENDER_DETAILS = getMsg("CG_TripSenderDetails");
		MSG_TRIP_ORIGIN = getMsg("CG_TripOrigin");
		MSG_TRIP_DESTINATION = getMsg("CG_TripDestination");
		MSG_DECLARED_VALUE = getMsg("CG_TripDeclaredValue");
		MSG_ERROR_MANDATORY = getMsg("CG_TripErrorMandatory");
		MSG_ADITIONAL_ADDED = getMsg("CG_TripAditionalAdded");
		MSG_ERROR_MISSING_APVPRODUCT = getMsg("CG_TripErrorMissingAPVProduct");
		MSG_ERROR_MISSING_APVRATE = getMsg("CG_TripErrorMissingAPVRate");
		MSG_ERROR_APVPRODUCT_NOTFOUND = getMsg("CG_TripErrorAPVProductNotFound");
	}

	// Overrides goToPayments adding mandatory fields check
	protected void goToPayments() {
		if (cgTripCombo.getValue() == null || cgOriginCombo.getValue() == null || cgDestinationCombo.getValue() == null)

			errorMsg(MSG_ERROR_MANDATORY);

		else {

			int productId = 0;
			String productIdString = MCGParameter.getValueForName(Env.getCtx(), MCGParameter.APV_PRODUCT_ID, null);
			try {
				productId = Integer.valueOf(productIdString);
			} catch (Exception e) {
				errorMsg(MSG_ERROR_MISSING_APVPRODUCT);
				return;
			}

			BigDecimal rate = BigDecimal.ZERO;
			String rateString = MCGParameter.getValueForName(Env.getCtx(), MCGParameter.APV_RATE, null);
			try {
				rate = new BigDecimal(rateString);
			} catch (Exception e) {
				errorMsg(MSG_ERROR_MISSING_APVRATE);
				return;
			}

			Product product = getModel().getProduct(productId, 0);
			if (product == null) {
				errorMsg(MSG_ERROR_APVPRODUCT_NOTFOUND);
				return;
			}

			// Update trip data in cgOrder
			((CGOrder) getOrder()).setCg_trip_id((Integer) cgTripCombo.getValue());
			((CGOrder) getOrder()).setCg_origin_id((Integer) cgOriginCombo.getValue());
			((CGOrder) getOrder()).setCg_destination_id((Integer) cgDestinationCombo.getValue());
			((CGOrder) getOrder()).setDeclaredValue(new BigDecimal((String) cgDeclaredValueText.getValue()));

			// Update aditional per value

			// Primero borro la linea existente
			List<OrderProduct> copyProducts = new ArrayList<OrderProduct>(getOrder().getOrderProducts());
			for (OrderProduct op : copyProducts) {
				if (op.getProduct().getId() == productId)
					removeOrderProduct(op);
			}
			if (!new BigDecimal((String) cgDeclaredValueText.getValue()).equals(BigDecimal.ZERO)) {
				// Agrego nueva
				BigDecimal adicionalPorValor = BigDecimal.ZERO;
				adicionalPorValor = new BigDecimal((String) cgDeclaredValueText.getValue()).multiply(rate);
				// Verifico / Agrego producto de adicional por valor en la lista
				// de
				// precios
				// Adds Additional value product to Current Price List

				MProduct mproduct = new MProduct(Env.getCtx(), productId, null);
				if (mproduct != null) {
					int priceListVersionID = getModel().getPriceListVersion().getId();
					MProductPrice pp = new Query(Env.getCtx(), MProductPrice.Table_Name, "isactive = 'Y' and m_product_id = " + productId
							+ " and M_PriceList_Version_ID = " + priceListVersionID, null).<MProductPrice> first();
					if (pp == null) {
						// Force save out of transaction
						Trx outTrx = Trx.createTrx("ProductPrice" + System.currentTimeMillis());
						pp = new MProductPrice(Env.getCtx(), 0, outTrx.getTrxName());
						pp.setM_Product_ID(productId);
						pp.setM_PriceList_Version_ID(priceListVersionID);
						pp.setPrices(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
						pp.setIsActive(true);
						pp.save();
						outTrx.commit();
					}
				}

				product.setStdPrice(adicionalPorValor);
				addOrderProduct(product);

				// Itero la tabla y seteo los sender Details de cada linea
				List<OrderProduct> orderProducts = getOrder().getOrderProducts();
				int aux = 0;

				for (OrderProduct op : orderProducts) {
					((ProductTableModel) getCOrderTable().getModel()).getValueAt(aux, 7);
					((CGOrderProduct) op).setSenderDetails((String) ((ProductTableModel) getCOrderTable().getModel()).getValueAt(aux, 7));
					aux++;
				}
			}
			super.goToPayments();
			// infoMsg(MSG_ADITIONAL_ADDED);
		}
	}

	protected void newOrder() {
		super.newOrder();
		cgDeclaredValueText.setText("0");
	}
}
