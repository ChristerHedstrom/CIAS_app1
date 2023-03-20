package helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import readexcel.ColumnCollection;
import readexcel.RowCollection;

/**
 * Class containing methods helping to build the JSON structure (plus additional
 * helper methods)
 * 
 * @author I325927
 *
 */
public class Helper {
	private static boolean hasOutboundServiceTag = false;
	private static boolean setEndTag = false;
	private static int APP_SIZE_WIDTH = 720;
	private static int LABEL_WIDTH_HINT = 350;
	private static int APP_SIZE_HEIGTH_FORMFACTOR = 39;
	private static int numberOfParameters = 0;

	/**
	 * Return unique values from a collection with duplicates Note! It takes values
	 * from two Excel columns as input, if there are two columns with data! 
	 * 
	 * @param items
	 * @return
	 */
	static public String[] getUniqueItems(ColumnCollection colFromSheet0, ColumnCollection colFromSheet1) {
		HashSet<String> temp = new HashSet<String>();
		List<String> itemsFromSheet0 = (colFromSheet0 != null) ? colFromSheet0.getValues() : null;
		List<String> itemsFromSheet1 = (colFromSheet1 != null) ? colFromSheet1.getValues() : null;

		// Add an empty start item
		temp.add("");

		if (itemsFromSheet0 != null) {
			for (String item : itemsFromSheet0) {
				temp.add(item);
			}
		}

		if (itemsFromSheet1 != null) {
			for (String item : itemsFromSheet1) {
				temp.add(item);
			}
		}

		String[] itemsArr = new String[temp.size()];

		TreeSet<String> treeSet = new TreeSet<String>(temp);
		return treeSet.toArray(itemsArr);
	}

	static public String setFirstConstantPart() {
		return JSONConstantStrings.PROPERTY_SET;
	}

	static public String setLastConstantPart() {
		return JSONConstantStrings.PROPERTY_SET_END;
	}

	/**
	 * Build the list of
	 * INTERFACE_KEY_NAME/INTERFACE_KEY_DATA_ELEMENT/INTERFACE_KEY_VALUE that
	 * belongs to the scenarioID in question.
	 * 
	 * @return
	 */
	static private String getKeyNameDataValue(List<LabelTextInputBean> objectList) {
		String buildKeyNameDataValuestr = "";
		int last = 1;

		List<LabelTextInputBean> objectsFromSheetOneList = filterOutFromSheet(1, objectList);

		if (!objectsFromSheetOneList.isEmpty()) {
			buildKeyNameDataValuestr += Helper.setFirstConstantPart();
		}

		for (LabelTextInputBean tmp : objectsFromSheetOneList) {
			if (objectsFromSheetOneList.size() == 1) {
				buildKeyNameDataValuestr += "{"
						+ String.format(JSONConstantStrings.INTERFACE_KEY_NAME, tmp.getPropertyName())
						+ String.format(JSONConstantStrings.INTERFACE_KEY_VALUE, tmp.getText().getText().getText());

				if (!filterOutFromSheet(2, objectList).isEmpty()) {
					buildKeyNameDataValuestr += "}],";
				} else {
					buildKeyNameDataValuestr += "}]";
				}

				return buildKeyNameDataValuestr;
			}

			if (last != objectsFromSheetOneList.size()) {
				buildKeyNameDataValuestr += "{"
						+ String.format(JSONConstantStrings.INTERFACE_KEY_NAME, tmp.getPropertyName())
						+ String.format(JSONConstantStrings.INTERFACE_KEY_VALUE, tmp.getText().getText().getText())
						+ "},";

			} else {
				buildKeyNameDataValuestr += "{"
						+ String.format(JSONConstantStrings.INTERFACE_KEY_NAME, tmp.getPropertyName())
						+ String.format(JSONConstantStrings.INTERFACE_KEY_VALUE, tmp.getText().getText().getText());

				if (!filterOutFromSheet(2, objectList).isEmpty()) {
					buildKeyNameDataValuestr += "}],";
				} else {
					buildKeyNameDataValuestr += "}]";
				}
			}

			last++;
		}

		return buildKeyNameDataValuestr;
	}

	/**
	 * Get the data from sheetnumber 1 or 2
	 * 
	 * @param sheetNumber
	 * @param objList
	 * @return
	 */
	static private List<LabelTextInputBean> filterOutFromSheet(int sheetNumber, List<LabelTextInputBean> objList) {
		List<LabelTextInputBean> fromSheetList = new ArrayList<LabelTextInputBean>();

		for (LabelTextInputBean tmp : objList) {
			if (sheetNumber == 1) {
				if (!tmp.isFromSecondSheet()) {
					if (tmp.getTypeIndex() == 1) {
						fromSheetList.add(tmp);
					}
				}
			} else if (sheetNumber == 2) {
				if (tmp.isFromSecondSheet() && !tmp.isAdditional()) { // Additional params goes in to a list of it's own
																		// later for later process.
					fromSheetList.add(tmp);
				}
			}
		}

		return fromSheetList;
	}

	/**
	 * Collect all LabelTextInputBean that has additional information (isAdditional
	 * = true)
	 * 
	 * @param objectList
	 * @return
	 */
	static private List<LabelTextInputBean> filterOutAdditionalParameters(List<LabelTextInputBean> objectList) {
		List<LabelTextInputBean> addParamList = new ArrayList<LabelTextInputBean>();

		for (LabelTextInputBean tmp : objectList) {
			if (tmp.isAdditional()) {
				addParamList.add(tmp);
			}
		}

		return addParamList;
	}

	/**
	 * Extract the information from the fields belonging to the second Excel sheet
	 * 
	 * @param objectList
	 * @return
	 */
	static private String getDataFromSheetTwoDataValue(List<LabelTextInputBean> objectList) {
		String buildSheetTwoDataValuestr = "";
		List<LabelTextInputBean> objectsFromSheetTwoList = filterOutFromSheet(2, objectList);
		List<LabelTextInputBean> addParamList = filterOutAdditionalParameters(objectList);

		hasOutboundServiceTag = false;
		setEndTag = false;

		int index = 1;
		String fill;
		int listSize = objectsFromSheetTwoList.size();
		int paramIndex = 0;

		if (!objectsFromSheetTwoList.isEmpty()) {
			buildSheetTwoDataValuestr = JSONConstantStrings.TO_OUTBOUND_SERVICES + JSONConstantStrings.LEFT_WING;
		}

		for (LabelTextInputBean tmp : objectsFromSheetTwoList) {
			fill = "";
			index++;
			paramIndex++;
			String text = "";
			boolean endOfAddParams = false;

			if (tmp.getCheckbox() != null) {
				text = tmp.getCheckbox().getText();
			} else if (tmp.getComboBox() != null) {
				text = tmp.getComboBox().getComboBox().getText();
			} else {
				text = tmp.getText().getText().getText();
			}

			if (tmp.getCheckbox() == null) {
				if (isPropertyNumber(tmp.getProviderNumberStr())) {
					buildSheetTwoDataValuestr += JSONConstantStrings.HELP_CHAR + tmp.getPropertyName()
							+ JSONConstantStrings.HELP_CHAR + ":" + JSONConstantStrings.HELP_CHAR
							+ tmp.getProvideNumberStr() + JSONConstantStrings.HELP_CHAR + ",";
				} else {
					if (tmp.getPropertyName() != null) {
						if (tmp.isLast()) {
							fill = "";
						} else {
							fill = ",";
						}

						buildSheetTwoDataValuestr += JSONConstantStrings.HELP_CHAR + tmp.getPropertyName()
								+ JSONConstantStrings.HELP_CHAR + ":" + JSONConstantStrings.HELP_CHAR + text
								+ JSONConstantStrings.HELP_CHAR + fill;
					}
				}
			} else {
				if (index <= objectsFromSheetTwoList.size()) {
					fill = ",";
				}

				if (tmp.isLast() && foundAddParams(tmp.getScenarioID(), tmp.getNumberStr(), addParamList)) {
					fill = ",";
				} else if (tmp.isLast()) {
					fill = "";
				} else {
					fill = ",";
				}

				buildSheetTwoDataValuestr += JSONConstantStrings.HELP_CHAR + tmp.getPropertyName()
						+ JSONConstantStrings.HELP_CHAR + ": " + tmp.getCheckbox().getSelection() + fill;
			}

			if (tmp.isLast()) {
				if (foundAddParams(tmp.getScenarioID(), tmp.getNumberStr(), addParamList)) {
					// If no "," prior to the additional list add one
					if (buildSheetTwoDataValuestr.charAt(buildSheetTwoDataValuestr.length() - 1) != ',') {
						buildSheetTwoDataValuestr += ",";
					}

					buildSheetTwoDataValuestr += setFirstConstantPart();
					for (LabelTextInputBean addParam : addParamList) {
						if (addParam.getScenarioID().equals(tmp.getScenarioID())
								&& (addParam.getNumberStr().equals(tmp.getNumberStr()))) {
							if (!addParam.isLastAdditional()) {
								buildSheetTwoDataValuestr += JSONConstantStrings.LEFT_WING
										+ String.format(JSONConstantStrings.INTERFACE_KEY_NAME,
												addParam.getPropertyName())
										+ String.format(JSONConstantStrings.INTERFACE_KEY_VALUE,
												addParam.getText().getText().getText())
										+ JSONConstantStrings.RIGHT_WING + JSONConstantStrings.COMMA_CHAR;
							} else {
								buildSheetTwoDataValuestr += JSONConstantStrings.LEFT_WING
										+ String.format(JSONConstantStrings.INTERFACE_KEY_NAME,
												addParam.getPropertyName())
										+ String.format(JSONConstantStrings.INTERFACE_KEY_VALUE,
												addParam.getText().getText().getText());
								if (paramIndex < listSize) {
									buildSheetTwoDataValuestr += JSONConstantStrings.PROPERTY_SET_END_LAST
											+ JSONConstantStrings.CURLY_RIGHT_LEFT;
									endOfAddParams = true;
								} else {
									buildSheetTwoDataValuestr += JSONConstantStrings.PROPERTY_SET_END_LAST;
								}
							}
						}
					}
				}

				if (listSize > paramIndex) {
					if (!endOfAddParams) {
						buildSheetTwoDataValuestr += JSONConstantStrings.RIGHT_WING + JSONConstantStrings.COMMA_CHAR
								+ JSONConstantStrings.LEFT_WING;
					}
				}
			}

		}

		if (paramIndex == listSize) {
			if (paramIndex != 0) {
				setEndTag = true;
			}

		} else if (!objectsFromSheetTwoList.isEmpty()) {
			buildSheetTwoDataValuestr += JSONConstantStrings.RIGHT_WING + JSONConstantStrings.TO_OUTBOUND_SERVICES_END;
			hasOutboundServiceTag = true;
		}

		return buildSheetTwoDataValuestr;
	}

	static boolean foundAddParams(String id, String number, List<LabelTextInputBean> addParamList) {
		for (LabelTextInputBean tmp : addParamList) {
			if (tmp.getScenarioID().equals(id) && tmp.getNumberStr().equals(number)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * The JSON string is built around the chosen scenarioID and corresponding list
	 * of properties
	 * 
	 * @param scenarioID
	 * @return
	 */
	static public String createJSONString(String scenarioID, List<LabelTextInputBean> objList, int gotOnlyScenarioID) {
		String endOutboundTag = "";
		String endTag = "";

		String buildJSON = JSONConstantStrings.START_SECTION + JSONConstantStrings.LEFT_WING
				+ String.format(JSONConstantStrings.COMM_SCENARIO_ID, scenarioID)
				+ String.format(JSONConstantStrings.COMM_SYSTEM_ID, "ComSystem");

		if (gotOnlyScenarioID == 2) {
			buildJSON += String.format(JSONConstantStrings.COMM_ARRANGEMENT_NAME_NO_COMMA,
					objList.get(0).getText().getText().getText());
		} else {
			buildJSON += String.format(JSONConstantStrings.COMM_ARRANGEMENT_NAME,
					objList.get(0).getText().getText().getText()) + Helper.getKeyNameDataValue(objList)
					+ Helper.getDataFromSheetTwoDataValue(objList);
		}

		if (hasOutboundServiceTag) {
			endOutboundTag = "}]";
		}

		if (setEndTag) {
			endTag = "]}";
		}

		buildJSON += endOutboundTag + "}" + endTag;

		hasOutboundServiceTag = false;
		setEndTag = false;
		gotOnlyScenarioID = 0;

		return buildJSON;
	}

	/**
	 * Calculate the need for a divider. Is checking if there are any services after
	 * the scenario data
	 * 
	 * @param rowColl
	 * @param id
	 * @return
	 */
	static public boolean needForDivider(List<RowCollection> rowColl, String id) {
		for (RowCollection addRowColl : rowColl) {
			if (addRowColl.getScenarioID().equals(id)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Calculates the number of services belonging to a ScenarioID
	 * 
	 * @param rowColl
	 * @param id
	 * @return
	 */
	static public int numberOfServices(List<RowCollection> rowColl, String ScenarioID) {
		int numberofServices = 0;
		for (RowCollection addRowColl : rowColl) {
			if (addRowColl.getScenarioID().equals(ScenarioID)) {
				numberofServices++;
			}
		}

		return numberofServices;
	}

	/**
	 * Add a divider
	 * 
	 * @param result
	 * @return
	 */
	static public LabelTextInputBean addDivider(Composite result) {
		CustomLabel separator = new CustomLabel(result, SWT.HORIZONTAL | SWT.SEPARATOR, null);
		separator.getLabel().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		LabelTextInputBean specialBean = new LabelTextInputBean();
		specialBean.setLabel(separator);
		specialBean.setText(createNonVisibleTextInput(result));

		return specialBean;
	}

	/**
	 * Add a header
	 * 
	 * @param result
	 * @param strHeader - The header
	 * @return
	 */
	static public LabelTextInputBean addHeader(Composite result, String strHeader) {
		LabelTextInputBean headerBean = new LabelTextInputBean();
		CustomLabel header = new CustomLabel(result, SWT.NONE, null);
		header.getLabel().setText(strHeader);
		FontDescriptor boldDescriptor = FontDescriptor.createFrom(header.getLabel().getFont()).setStyle(SWT.ITALIC);
		Font boldFont = boldDescriptor.createFont(header.getLabel().getDisplay());
		header.getLabel().setFont(boldFont);
		headerBean.setLabel(header);
		headerBean.setText(createNonVisibleTextInput(result));

		return headerBean;
	}

	/**
	 * Create a non-visible text input placeholder
	 * 
	 * @param result
	 * @return
	 */
	static private CustomText createNonVisibleTextInput(Composite result) {
		CustomText text = new CustomText(result, SWT.NONE, null);
		text.getText().setVisible(false);

		return text;
	}

	/**
	 * Check if a str is a number or not
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static boolean isPropertyNumber(String str) {
		try {
			new Integer(str);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

	public static List<RowCollection> getAdditionalParameters(List<RowCollection> totalColl, String id,
			String numberString) {
		List<RowCollection> result = new ArrayList<RowCollection>();

		for (RowCollection temp : totalColl) {
			if (temp.getNumberStr() != null) {
				if ((temp.getScenarioID().equals(id)) && (temp.getNumberStr().equals(numberString))) {
					if (!temp.getPropertyFieldLabel().isEmpty()) {
						RowCollection rowColl = new RowCollection();
						rowColl.setPropertyFieldLabel(temp.getPropertyFieldLabel());
						rowColl.setPropertyName(temp.getPropertyName());

						result.add(rowColl);
					}
				}
			}
		}

		return result;
	}

	public static void FilePathErrorDialog(Shell shell, String filePath) {
		MessageDialog.openError(shell, "An error occured",
				"The chosen file was not correct. Please try again with another.\n" + "Change the " + filePath
						+ " in ExcelFilePath.properties and run\nthe app again.");
		System.exit(0);
	}
}
