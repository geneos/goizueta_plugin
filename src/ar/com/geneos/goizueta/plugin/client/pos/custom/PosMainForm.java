package ar.com.geneos.goizueta.plugin.client.pos.custom;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.openXpertya.apps.form.VComponentsFactory;
import org.openXpertya.grid.ed.VLookup;
import org.openXpertya.swing.util.FocusUtils;
import org.openXpertya.util.DisplayType;

import ar.com.geneos.goizueta.plugin.client.pos.PoSMainForm;

public class PosMainForm extends PoSMainForm {

	private static final long serialVersionUID = -9151130246417888867L;

	private final int LABEL_SIZE = 90;
	private final int INPUT_SIZE = 170;

	// CONTROL
	private boolean cOrderTopPanelInitialized = false;

	// PANELS
	private CPanel cgTripPanel = null;

	// LABELS
	private CLabel cgTripPanelTripLabel = null;
	private CLabel cgTripPanelOriginLabel = null;
	private CLabel cgTripPanelDestinationLabel = null;
	private CLabel cgTripPanelDeclaredValueLabel = null;
	private CLabel cgTripPanelSenderDetailsLabel = null;
	private CPanel cgDeclaredValuePanel = null;

	// MESSAGES
	private String MSG_TRIP_DATA;
	private String MSG_TRIP;
	private String MSG_TRIP_ORIGIN;
	private String MSG_TRIP_DESTINATION;
	private String MSG_SENDER_DETAILS;
	private String MSG_DECLARED_VALUE;
	private String MSG_ERROR_MANDATORY;

	// Combos
	private VLookup cgTripCombo = null;
	private VLookup cgOriginCombo = null;
	private VLookup cgDestinationCombo = null;

	// INPUTS
	private CTextField cgSenderDetailsText = null;
	private CTextField cgDeclaredValueText = null;

	// BUTTONS
	private CButton cgDeclaredValueButton = null;

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
			super.getCOrderTopPanel().setPreferredSize(new java.awt.Dimension(0, 110));
			cOrderTopPanelInitialized = true;
		}
		return super.getCOrderTopPanel();
	}

	private CPanel getCGTripPanel() {
		if (cgTripPanel == null) {

			cgTripPanel = new CPanel();
			cgTripPanel.setPreferredSize(new java.awt.Dimension(500, 115));

			cgTripPanel.setLayout(new GridBagLayout());
			cgTripPanel.setName("");
			cgTripPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray, 1), MSG_TRIP_DATA, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12), new java.awt.Color(51, 51, 51)),
					javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)));

			cgTripPanelTripLabel = new CLabel();
			cgTripPanelTripLabel.setPreferredSize(new java.awt.Dimension(LABEL_SIZE, 20));
			cgTripPanelTripLabel.setText(MSG_TRIP);

			cgTripPanelSenderDetailsLabel = new CLabel();
			cgTripPanelSenderDetailsLabel.setPreferredSize(new java.awt.Dimension(LABEL_SIZE, 20));
			cgTripPanelSenderDetailsLabel.setText(MSG_SENDER_DETAILS);

			cgTripPanelOriginLabel = new CLabel();
			cgTripPanelOriginLabel.setPreferredSize(new java.awt.Dimension(LABEL_SIZE, 20));
			cgTripPanelOriginLabel.setText(MSG_TRIP_ORIGIN);

			cgTripPanelDestinationLabel = new CLabel();
			cgTripPanelDestinationLabel.setPreferredSize(new java.awt.Dimension(LABEL_SIZE, 20));
			cgTripPanelDestinationLabel.setText(MSG_TRIP_DESTINATION);

			cgTripPanelDeclaredValueLabel = new CLabel();
			cgTripPanelDeclaredValueLabel.setPreferredSize(new java.awt.Dimension(LABEL_SIZE, 20));
			cgTripPanelDeclaredValueLabel.setText(MSG_DECLARED_VALUE);

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.insets = new java.awt.Insets(2, 0, 0, 5);
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.gridwidth = 1;

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.insets = new java.awt.Insets(2, 0, 0, 0);
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridwidth = GridBagConstraints.REMAINDER;

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.insets = new java.awt.Insets(0, 0, 7, 10);
			gridBagConstraints10.gridy = 1;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.insets = new java.awt.Insets(0, 0, 7, 10);
			gridBagConstraints11.gridy = 1;

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 2;
			gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints12.insets = new java.awt.Insets(0, 0, 7, 5);
			gridBagConstraints12.gridy = 1;

			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 3;
			gridBagConstraints13.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints13.insets = new java.awt.Insets(0, 0, 7, 10);
			gridBagConstraints13.gridy = 1;

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
			gridBagConstraints03.insets = new java.awt.Insets(0, 0, 7, 10);
			gridBagConstraints03.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints03.gridx = 3;

			cgTripPanel.add(cgTripPanelTripLabel, gridBagConstraints00);
			cgTripPanel.add(getCGTripCombo(), gridBagConstraints01);
			cgTripPanel.add(cgTripPanelDeclaredValueLabel, gridBagConstraints02);
			cgTripPanel.add(getCGDeclaredValuePanel(), gridBagConstraints03);

			cgTripPanel.add(cgTripPanelOriginLabel, gridBagConstraints10);
			cgTripPanel.add(getCGOriginCombo(), gridBagConstraints11);
			cgTripPanel.add(cgTripPanelDestinationLabel, gridBagConstraints12);
			cgTripPanel.add(getCGDestinationCombo(), gridBagConstraints13);

			cgTripPanel.add(cgTripPanelSenderDetailsLabel, gridBagConstraints20);
			cgTripPanel.add(getCGSenderDetailsText(), gridBagConstraints21);

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
			cgTripCombo.setPreferredSize(new java.awt.Dimension(INPUT_SIZE, 20));
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
			cgDestinationCombo.setPreferredSize(new java.awt.Dimension(120, 20));
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
			cgOriginCombo.setPreferredSize(new java.awt.Dimension(120, 20));
			cgOriginCombo.setValue(null);
			FocusUtils.addFocusHighlight(cgOriginCombo);
		}
		return cgOriginCombo;
	}

	/**
	 * This method initializes cgSenderDetailsText
	 * 
	 * @return org.compiere.swing.CTextField
	 */
	private CTextField getCGSenderDetailsText() {
		if (cgSenderDetailsText == null) {
			cgSenderDetailsText = new CTextField();
			cgSenderDetailsText.setPreferredSize(new java.awt.Dimension(INPUT_SIZE + 100, 20));
			FocusUtils.addFocusHighlight(cgSenderDetailsText);
		}
		return cgSenderDetailsText;
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

	/**
	 * This method initializes cgDeclaredValueButton
	 * 
	 * @return org.compiere.swing.CButton
	 */
	private CButton getCGDeclaredValueButton() {
		if (cgDeclaredValueButton == null) {
			cgDeclaredValueButton = new CButton();
			cgDeclaredValueButton.setIcon(getImageIcon("Refresh16.gif"));
			cgDeclaredValueButton.setPreferredSize(new java.awt.Dimension(35, 20));
			/*
			 * cgDeclaredValueButton.addActionListener(new
			 * java.awt.event.ActionListener() { public void
			 * actionPerformed(java.awt.event.ActionEvent e) { goToPayments(); }
			 * });
			 */
			FocusUtils.addFocusHighlight(cgDeclaredValueButton);
		}
		return cgDeclaredValueButton;
	}

	private CPanel getCGDeclaredValuePanel() {
		if (cgDeclaredValuePanel == null) {
			cgDeclaredValuePanel = new CPanel();
			cgDeclaredValuePanel.setLayout(new GridBagLayout());

			GridBagConstraints gridBagConstraints00 = new GridBagConstraints();
			gridBagConstraints00.gridx = 0;
			gridBagConstraints00.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints00.insets = new java.awt.Insets(0, 0, 7, 0);
			gridBagConstraints00.gridy = 0;

			GridBagConstraints gridBagConstraints01 = new GridBagConstraints();
			gridBagConstraints01.gridy = 0;
			gridBagConstraints01.insets = new java.awt.Insets(0, 0, 7, 0);
			gridBagConstraints01.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints01.gridx = 1;

			cgDeclaredValuePanel.add(getCGDeclaredValueText(), gridBagConstraints00);
			cgDeclaredValuePanel.add(getCGDeclaredValueButton(), gridBagConstraints01);

		}
		return cgDeclaredValuePanel;
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
	}

	// Overrides goToPayments adding mandatory fields check
	protected void goToPayments() {
		if (cgTripCombo.getValue() == null || cgOriginCombo.getValue() == null || cgDestinationCombo.getValue() == null
				|| (new BigDecimal((String) cgDeclaredValueText.getValue())).equals(BigDecimal.ZERO)
				|| ((String) cgSenderDetailsText.getValue()).trim().isEmpty())

			errorMsg(MSG_ERROR_MANDATORY);

		else {
			// Update trip data in cgOrder
			((CGOrder) getOrder()).setCg_trip_id((Integer) cgTripCombo.getValue());
			((CGOrder) getOrder()).setCg_origin_id((Integer) cgOriginCombo.getValue());
			((CGOrder) getOrder()).setCg_destination_id((Integer) cgDestinationCombo.getValue());
			((CGOrder) getOrder()).setDeclaredValue(new BigDecimal((String) cgDeclaredValueText.getValue()));
			((CGOrder) getOrder()).setSenderDetails((String) cgSenderDetailsText.getValue());

			// Update aditional per value
			super.goToPayments();
		}
	}

	protected void newOrder() {
		super.newOrder();
		cgOriginCombo.setValue(null);
		cgDestinationCombo.setValue(null);
		cgDeclaredValueText.setText("0");
		cgSenderDetailsText.setValue("");
	}
}
