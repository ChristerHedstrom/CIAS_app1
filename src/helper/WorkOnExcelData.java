package helper;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import readexcel.ErrorInExcelFile;
import readexcel.ReadExcel;
import readexcel.RowCollection;
import windows.SubWindow;

public class WorkOnExcelData {
	private GridData textConfig;
	private GridData labelConfig;
	private String[] frequency = { "D", "H", "M" };
	private int LABEL_WIDTH_HINT = 350;
	private static String frequencyChoice = "";
	private static int state = 0;

	public List<RowCollection> gatherAllScenarioIDInformation(ReadExcel readExcel) throws ErrorInExcelFile {
		List<RowCollection> rowColl = readExcel.getExcelRowsCW23(0);

		return rowColl;
	}

	public List<RowCollection> gatherAllAdditionalScenarioIDInformation(ReadExcel readExcel) throws ErrorInExcelFile {
		List<RowCollection> rowColl = readExcel.getExcelRowsCW23(1);

		return rowColl;
	}

	/**
	 * Is used to build beans from Excel sheet 2
	 * 
	 * @param order
	 * @param labelText
	 * @param number
	 * @param index
	 * @param compID    TODO
	 * 
	 * @return
	 */
	public LabelTextInputBean createLabelTextInputBeanFromSheet2(Composite result, int order, String labelText,
			String propertyText, String number, String outboundServiceID, int index, Shell shell, boolean isAdditional,
			String scenarioID, boolean isLast, boolean isLastAdditional, int specialCase, boolean isVisible,
			String compID) {
		LabelTextInputBean labelTextInputBean = new LabelTextInputBean();
		CustomLabel label = new CustomLabel(result, SWT.NONE, null);
		int typeIndex = 2; // typeIndex = 2 --> from Excel sheet 2
		textConfig = new GridData();
		textConfig.grabExcessHorizontalSpace = true;
		textConfig.horizontalAlignment = GridData.FILL;

		labelConfig = new GridData();
		labelConfig.widthHint = LABEL_WIDTH_HINT;

		if (specialCase == 15) {
			CustomCombobox comboBox = new CustomCombobox(result, SWT.DROP_DOWN | SWT.READ_ONLY, compID);
			comboBox.getComboBox().setSize(150, 100);
			comboBox.getComboBox().setItems(frequency);
			comboBox.getComboBox().select(0);
			comboBox.getComboBox().setVisible(true);
			comboBox.setId(compID);
			label.getLabel().setLayoutData(labelConfig);
			label.getLabel().setText(labelText);
			label.getLabel().setVisible(true);
			labelTextInputBean.setComboBox(comboBox);
			List<LabelTextInputBean> objList = SubWindow.getObjList();

			comboBox.getComboBox().addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					frequencyChoice = e.widget.toString().replace("Combo {", "").replace("}", "").trim();

					for (LabelTextInputBean bean : objList) {
						if (bean.getText() != null) {
							if (bean.getLabel() != null) {
								if (compID.equals(bean.getLabel().getId())) {
									if (!bean.getLabel().getLabel().getText().equals("PackageSize")) {
										if (!frequencyChoice.equals("D") && !frequencyChoice.equals("Days")) {
											bean.getText().getText().setEditable(false);
											bean.getText().getText().setText("");
										} else {
											bean.getText().getText().setEditable(true);
										}

									}

								}
							}
						}
					}
				}
			});

		} else if (specialCase == 10) {
			CustomText text = new CustomText(result, SWT.BORDER, compID);
			label.getLabel().setLayoutData(labelConfig);
			label.getLabel().setText(labelText);
			label.getLabel().setVisible(isVisible);
			label.setId(compID);
			text.getText().setLayoutData(textConfig);
			text.getText().setVisible(isVisible);
			text.setId(compID);
			labelTextInputBean.setText(text);
		} else {
			if (specialCase == 5) {
				Button checkbox = new Button(result, SWT.CHECK);
				checkbox.setSelection(false);
				label.getLabel().setText(labelText);
				label.getLabel().setLayoutData(labelConfig);
				labelTextInputBean.setLabel(label);
				labelTextInputBean.setCheckbox(checkbox);
			} else {
				CustomText text = new CustomText(result, SWT.BORDER, null);
				label.getLabel().setLayoutData(labelConfig);
				if (specialCase != 2) {
					label.getLabel().setText(labelText);
				} else {
					label.getLabel().setText(outboundServiceID);
				}

				label.getLabel().setVisible(true);
				text.getText().setLayoutData(textConfig);

				if (specialCase == 2) {
					text.getText().setVisible(false);
				} else if (specialCase == 1) {
					text.getText().setEditable(false);
					text.getText().setText(number);
					labelTextInputBean.setProvideNumberStr(number);
				}

				labelTextInputBean.setText(text);
			}
		}

		labelTextInputBean.setLabel(label);

		labelTextInputBean.setId(index);

		if (propertyText == null) {
			labelTextInputBean.setPropertyName(labelText);
		} else {
			labelTextInputBean.setPropertyName(propertyText);
		}

		labelTextInputBean.setFromSecondSheet(true);
		labelTextInputBean.setTypeIndex(typeIndex);
		labelTextInputBean.setAdditional(isAdditional);
		labelTextInputBean.setScenarioID(scenarioID);
		labelTextInputBean.setNumberStr(number);
		labelTextInputBean.setLast(isLast);
		labelTextInputBean.setLastAdditional(isLastAdditional);

		return labelTextInputBean;
	}

	public static int getState() {
		return state;
	}

	public static void setState(int state) {
		WorkOnExcelData.state = state;
	}
}
