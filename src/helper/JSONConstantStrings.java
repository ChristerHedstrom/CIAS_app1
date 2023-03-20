package helper;

public interface JSONConstantStrings {
//	public static final String START_SECTION = "/ui?saml2=disabled#CommunicationArrangement-createCommunicationArrangementByJson?jsonData=";
	public static final String START_SECTION = "/ui?#CommunicationArrangement-createCommunicationArrangementByJson?jsonData=";

	public static final String COMM_SCENARIO_ID = "\"CommScenarioID\": \"%s\",";
	public static final String COMM_ARRANGEMENT_NAME = "\"Name\": \"%s\",";
	public static final String COMM_ARRANGEMENT_NAME_NO_COMMA = "\"Name\": \"%s\"";
	public static final String PROPERTY_SET = "\"ToProperty\":[ ";
	public static final String PROPERTY_SET_ADD = "\"ToProperty\":[ {";
	public static final String INTERFACE_KEY_NAME = "\"InterfaceKeyName\": \"%s\",";
	public static final String INTERFACE_KEY_VALUE = "\"InterfaceKeyValue\": \"%s\"";
	public static final String PROPERTY_SET_END = "        }\r\n" + "    ],\r\n" + "";
	public static final String PROPERTY_SET_END_LAST = "        }\r\n" + "    ]\r\n" + "";
	public static final String COMM_SYSTEM_ID = "\"CommSystemID\": \"%s\",";

	// From the second Excel sheet
	public static final String TO_OUTBOUND_SERVICES = "\"ToOutboundServices\":[ ";
	public static final String TO_OUTBOUND_SERVICES_END = "]";
	public static final String HELP_CHAR = "\"";
	public static final String COMMA_CHAR = ",";
	public static final String LEFT_WING = "{";
	public static final String RIGHT_WING = "}";

	public static final String CURLY_RIGHT_LEFT = "},{";
}