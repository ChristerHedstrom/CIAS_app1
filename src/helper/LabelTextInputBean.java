package helper;

import org.eclipse.swt.widgets.Button;

public class LabelTextInputBean {
	private int id;
	private CustomLabel label;
	private CustomText text;
	private Button checkbox;
	private CustomCombobox comboBox;
	private String propertyName;
	private String dataElement;
	private String providerNumberStr;
	private boolean fromSecondSheet = false;
	private int typeIndex;
	private String scenarioID;
	private boolean isAdditional;
	private boolean isLast;
	private boolean isLastAdditional;
	private String numberStr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomLabel getLabel() {
		return label;
	}

	public void setLabel(CustomLabel label) {
		this.label = label;
	}

	public CustomText getText() {
		return text;
	}

	public void setText(CustomText text) {
		this.text = text;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getDataElement() {
		return dataElement;
	}

	public void setDataElement(String dataElement) {
		this.dataElement = dataElement;
	}

	public Button getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(Button checkbox) {
		this.checkbox = checkbox;
	}

	public boolean isFromSecondSheet() {
		return fromSecondSheet;
	}

	public void setFromSecondSheet(boolean fromSecondSheet) {
		this.fromSecondSheet = fromSecondSheet;
	}

	public int getTypeIndex() {
		return typeIndex;
	}

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public String getProvideNumberStr() {
		return providerNumberStr;
	}

	public void setProvideNumberStr(String provideNumberStr) {
		this.providerNumberStr = provideNumberStr;
	}

	public String getScenarioID() {
		return scenarioID;
	}

	public void setScenarioID(String scenarioID) {
		this.scenarioID = scenarioID;
	}

	public boolean isAdditional() {
		return isAdditional;
	}

	public void setAdditional(boolean isAdditional) {
		this.isAdditional = isAdditional;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean isLastAdditional() {
		return isLastAdditional;
	}

	public void setLastAdditional(boolean isLastAdditional) {
		this.isLastAdditional = isLastAdditional;
	}

	public String getProviderNumberStr() {
		return providerNumberStr;
	}

	public void setProviderNumberStr(String providerNumberStr) {
		this.providerNumberStr = providerNumberStr;
	}

	public String getNumberStr() {
		return numberStr;
	}

	public void setNumberStr(String numberStr) {
		this.numberStr = numberStr;
	}

	public CustomCombobox getComboBox() {
		return comboBox;
	}

	public void setComboBox(CustomCombobox comboBox) {
		this.comboBox = comboBox;
	}
}
