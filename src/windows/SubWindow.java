package windows;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import helper.CustomLabel;
import helper.CustomText;
import helper.Helper;
import helper.LabelTextInputBean;
import helper.WorkOnExcelData;
import readexcel.ErrorInExcelFile;
import readexcel.ReadExcel;
import readexcel.RowCollection;

public class SubWindow {
	private static Shell subShell;
	private static Shell outerShell;
	private static Display display;
	private static GridData textConfig;
	private static GridData labelConfig;
	private static Menu menu;
	private static MenuItem mntmFile;
	private static Menu menu_1;
	private static MenuItem goBackItem;
	private static MenuItem exitItem;
	private static int APP_SIZE_WIDTH = 720;
	private static int APP_SIZE_HEIGTH_FORMFACTOR = 39;
	private static int LABEL_WIDTH_HINT = 350;
	private static String scenarioID;
	private static List<LabelTextInputBean> objList;
	private static int numberOfParameters = 0;
	private static Button convertToJSONBtn;
	private static CustomText text;
	private static String jsonStr;
	private static String[] listOfScenIDs;
	private static ReadExcel readExcel;
	private static ScrolledComposite scrolledComposite;
	private static int gotOnlyScenarioID = 0;

	/**
	 * Open the window.
	 * 
	 * @throws ErrorInExcelFile
	 */
	public static void open(String id, Shell mainShell, String[] listOfScenarioIDs, ReadExcel readExc)
			throws ErrorInExcelFile {
		readExcel = readExc;
		listOfScenIDs = listOfScenarioIDs;
		outerShell = mainShell;
		scenarioID = id;

		Display.getDefault();

		setupScrolling(id);
		layoutControls();
		openShell();
		mainShell.setVisible(false);
		runEventLoop();
	}

	protected static void createMenu() {
		subShell.setText("Convert Excel to JSON");
		subShell.setLayout(new GridLayout(2, false));

		subShell.setSize(1000, 1000);

		textConfig = new GridData();
		textConfig.grabExcessHorizontalSpace = true;
		textConfig.horizontalAlignment = GridData.FILL;

		labelConfig = new GridData();
		labelConfig.widthHint = LABEL_WIDTH_HINT;

		menu = new Menu(subShell, SWT.BAR);
		subShell.setMenuBar(menu);

		mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("Exit");

		menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		exitItem = new MenuItem(menu_1, SWT.PUSH);
		exitItem.setText("E&xit");

		exitItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean exit = MessageDialog.openQuestion(subShell, "Exiting Application",
						"Do you really want to exit?");

				if (exit)
					System.exit(0);
			}
		});
	}

	private static void setAppSize() {
		int padding = 0;
		
		//System.out.println("numberOfParameters: " + numberOfParameters);

		if (numberOfParameters == 0) {
			numberOfParameters = 15;
		} else if (numberOfParameters <= 10) {
			padding = 150;
		} else if (numberOfParameters <= 15) {
			padding = 70;
		} else if (numberOfParameters <= 50) {
			//padding = 200;
			numberOfParameters = 60;
		}

		int app_height = (APP_SIZE_HEIGTH_FORMFACTOR * numberOfParameters) + padding;
		subShell.setSize(APP_SIZE_WIDTH, app_height);
	}

	private static void setupScrolling(String scenarioID) throws ErrorInExcelFile {
		subShell = new Shell(display, SWT.SHELL_TRIM | SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX | SWT.RESIZE);
		subShell.setSize(500, 1000);
		subShell.pack();
		scrolledComposite = new ScrolledComposite(subShell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE);

		createMenu();

		Composite result = new Composite(scrolledComposite, SWT.NONE);
		result.setLayout(new GridLayout(2, false));
		Composite parent = getComponentsForScenarioID(scenarioID, result);
		scrolledComposite.setContent(parent);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);

		scrolledComposite.addListener(SWT.Resize, event -> {
			int width = scrolledComposite.getClientArea().width;
			int height = 34 * numberOfParameters;
			scrolledComposite.setMinSize(parent.computeSize(width, height));
		});
	}

	private static void layoutControls() {
		subShell.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.heightHint = computePreferredHeight();
		gridData.widthHint = computePreferredWidth();
		scrolledComposite.setLayoutData(gridData);
	}

	private static void openShell() {
		subShell.pack();
		subShell.open();
	}

	private static void runEventLoop() {
		while (!subShell.isDisposed()) {
			if (!subShell.getDisplay().readAndDispatch()) {
				subShell.getDisplay().sleep();
			}
		}
	}

	private static int computePreferredHeight() {
		int numberOfLines = numberOfParameters;

		if (numberOfLines <= 15) {
			numberOfLines += 6;
		} else if (numberOfParameters <= 20) {
			numberOfLines = numberOfParameters + 4;
		}
		if (numberOfLines <= 38) {
			numberOfLines -= 4;
		} else if (numberOfLines <= 40) {
			numberOfLines -= 15;
		} else if (numberOfLines <= 80) {
			numberOfLines -= 35;
		}

		int defaultHorizontalSpacing = 5;
		Text text = new Text(subShell, SWT.BORDER);
		text.setText("0123456789012345678901234567890123456789");
		Point preferredSize = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		text.dispose();

		return numberOfLines * (preferredSize.y + defaultHorizontalSpacing);
	}

	private static int computePreferredWidth() {
		Text text = new Text(subShell, SWT.BORDER);
		text.setText("0123456789012345678901234567890123456789");
		Point preferredSize = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		text.dispose();

		return (preferredSize.x - 30) * 3;
	}

	/**
	 * Builds the label/input for each parameter under the scenario. Very big
	 * method, could perhaps be refactored and split up...
	 * 
	 * @throws ErrorInColumnIndex
	 */
	private static Composite getComponentsForScenarioID(String id, Composite result) throws ErrorInExcelFile {
		WorkOnExcelData workExcel = new WorkOnExcelData();
		List<RowCollection> scenarioIDInformation = scenarioIDInformation(
				workExcel.gatherAllScenarioIDInformation(readExcel), id);
		List<RowCollection> additionalScenarioIDInformation = scenarioIDInformation(
				workExcel.gatherAllAdditionalScenarioIDInformation(readExcel), id);

		scenarioID = id;
		objList = new ArrayList<LabelTextInputBean>();
		int index = 0;

		Label scenarioIDLabel = new Label(result, SWT.NONE);
		scenarioIDLabel.setText("ScenarioID: ");
		scenarioIDLabel.setVisible(true);

		Label scenarioIDValue = new Label(result, SWT.NONE);
		scenarioIDValue.setText(id);
		scenarioIDValue.setVisible(true);

		// Scenario name
		CustomLabel scenarioName = new CustomLabel(result, SWT.NONE, null);
		CustomText scenarioNameText = new CustomText(result, SWT.BORDER, null);
		scenarioName.getLabel().setText("Scenario name:");
		scenarioName.getLabel().setVisible(true);
		scenarioNameText.getText().setLayoutData(textConfig);
		scenarioNameText.getText().setTextLimit(40);

		LabelTextInputBean scenarioNameInputBean = new LabelTextInputBean();
		scenarioNameInputBean.setLabel(scenarioName);
		scenarioNameInputBean.setText(scenarioNameText);
		scenarioNameInputBean.setId(index++);

		scenarioNameInputBean.setTypeIndex(0); // typeIndex = 0 ---> "Free text" input

		objList.add(scenarioNameInputBean);

		boolean isFirst = true;

		for (RowCollection rowColl : scenarioIDInformation) {
			if (rowColl.getNumberStr() == null) {
				// First horizontal separator, if needed
				if (isFirst && !rowColl.getPropertyFieldLabel().isEmpty()) {
					objList.add(Helper.addDivider(result));
					isFirst = false;
				}

				if (!rowColl.getPropertyFieldLabel().isEmpty()) { // Check if it's the special case with only the
																	// ScenarioID
					CustomLabel label = new CustomLabel(result, SWT.NONE, null);

					label.getLabel().setLayoutData(labelConfig);

					label.getLabel().setText(rowColl.getPropertyFieldLabel() + ":");
					label.getLabel().setVisible(true);

					text = new CustomText(result, SWT.BORDER, null);

					text.getText().setLayoutData(textConfig);

					text.getText().setText(rowColl.getDefaultValue());
					text.getText().setTextLimit(40);

					LabelTextInputBean labelTextInputBean = new LabelTextInputBean();
					labelTextInputBean.setLabel(label);
					labelTextInputBean.setText(text);
					labelTextInputBean.setId(index++);
					labelTextInputBean.setPropertyName(rowColl.getPropertyName());
					labelTextInputBean.setDataElement(rowColl.getDataElement());
					labelTextInputBean.setTypeIndex(1); // typeIndex = 1 --> from Excel sheet 1

					objList.add(labelTextInputBean);
				} else {
					gotOnlyScenarioID++;
				}
			}
		}

		// Create a start horizontal separator if needed
		if (Helper.needForDivider(additionalScenarioIDInformation, id) == true) {
			objList.add(Helper.addDivider(result));
		}

		int end = Helper.numberOfServices(additionalScenarioIDInformation, id);
		int recCount = 0;
		boolean isLast = false;
		boolean haveSetdivider = false;

		for (RowCollection addRowColl : additionalScenarioIDInformation) {
			haveSetdivider = false;

			objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 2, null, null, addRowColl.getNumberStr(),
					addRowColl.getOutboundServiceID(), index++, subShell, false, id, false, false, 2, true, null));
			objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 1, "CommScenarioServiceID", null,
					addRowColl.getNumberStr(), addRowColl.getOutboundServiceID(), index++, subShell, false, id, false,
					false, 1, true, null));

			recCount++;

			objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 3, "URLPath", null,
					addRowColl.getNumberStr(), null, index++, subShell, false, id, false, false, 0, true, null));
			objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 4, "SoapWsrmVersion", null,
					addRowColl.getNumberStr(), null, index++, subShell, false, id, false, false, 0, true, null));

			if ((addRowColl.getJobDefinitionName() == null) || (addRowColl.getJobDefinitionName().isEmpty())) {
				isLast = true;
			} else {
				isLast = false;
			}

			objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 5, "IsServiceActive", null,
					addRowColl.getNumberStr(), null, index++, subShell, false, id, isLast, false, 5, true, null));

			// To be used later in the code
			List<RowCollection> additionalParametersList = Helper.getAdditionalParameters(scenarioIDInformation, id,
					addRowColl.getNumberStr());

			if ((!addRowColl.getJobDefinitionName().isEmpty())) {
				objList.add(Helper.addHeader(result, "Job"));

				String jobDefID = recCount + "jobdefID";
				// CHTEST081021: Should "RecurrenceValue" be linked/connected to "Frequency"? Is
				// not for now
				objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 7, "RecurrenceValue", null,
						addRowColl.getNumberStr(), null, index++, subShell, false, id, false, false, 10, true, null));

				objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 8, "Frequency", null,
						addRowColl.getNumberStr(), null, index++, subShell, false, id, false, false, 15, true,
						jobDefID));
				objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 9, "StartTime", null,
						addRowColl.getNumberStr(), null, index++, subShell, false, id, false, false, 10, true,
						jobDefID));
				objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 10, "TimezoneCode", null,
						addRowColl.getNumberStr(), null, index++, subShell, false, id, false, false, 10, true,
						jobDefID));
				objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, 11, "PackageSize", null,
						addRowColl.getNumberStr(), null, index++, subShell, false, id, true, false, 10, true,
						jobDefID));

				if (additionalParametersList.isEmpty()) {
					objList.add(Helper.addDivider(result));
					haveSetdivider = true;
				}
			}

			if (!additionalParametersList.isEmpty()) {
				int addIndex = 6;
				int addCount = 0;
				int numberOfAddPArams = additionalParametersList.size();
				boolean isLastAdditionalParam = false;

				objList.add(Helper.addHeader(result, "Properties"));

				for (RowCollection addparamColl : additionalParametersList) {
					addCount++;
					if (numberOfAddPArams == addCount) {
						isLastAdditionalParam = true;
					}

					objList.add(workExcel.createLabelTextInputBeanFromSheet2(result, addIndex++,
							addparamColl.getPropertyFieldLabel(), addparamColl.getPropertyName(),
							addRowColl.getNumberStr(), null, index++, subShell, true, id, false, isLastAdditionalParam,
							0, true, null));
				}

				// Create a horizontal separator after each service, but not after the last
				if (recCount < end) {
					objList.add(Helper.addDivider(result));
					haveSetdivider = true;
				}
				// If not set a devider, set it here
			} else if (!haveSetdivider) {
				objList.add(Helper.addDivider(result));
			}
		}

		if (recCount == 0) {
			gotOnlyScenarioID++;
		}
		if (objList != null) {
			numberOfParameters = objList.size();
		}

		menu = new Menu(subShell, SWT.BAR);
		subShell.setMenuBar(menu);

		mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		goBackItem = new MenuItem(menu_1, SWT.PUSH);
		goBackItem.setText("Go Back");

		exitItem = new MenuItem(menu_1, SWT.PUSH);
		exitItem.setText("E&xit");

		goBackItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				subShell.close();
				subShell = new Shell();
				outerShell.setVisible(true);
				outerShell.open();
				numberOfParameters = 0; // "Reset" the app window
				gotOnlyScenarioID = 0;
			}
		});

		convertToJSONBtn = new Button(result, SWT.BORDER);
		convertToJSONBtn.setText("Create the JSON content");
		convertToJSONBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				jsonStr = Helper.createJSONString(scenarioID, objList, gotOnlyScenarioID);

				for (LabelTextInputBean tmp : objList) {
					if ((tmp.getLabel() != null) && !tmp.getLabel().getLabel().isDisposed()) {
						tmp.getLabel().getLabel().dispose();
					}

					if ((tmp.getText() != null) && !tmp.getText().getText().isDisposed()) {
						tmp.getText().getText().dispose();
					}

					if ((tmp.getCheckbox() != null) && !tmp.getCheckbox().isDisposed()) {
						tmp.getCheckbox().dispose();
					}

					if ((tmp.getComboBox() != null) && !tmp.getComboBox().getComboBox().isDisposed()) {
						tmp.getComboBox().getComboBox().dispose();
					}
				}

				if (!convertToJSONBtn.isDisposed()) {
					convertToJSONBtn.dispose();
				}

				if (jsonStr != null) {
					subShell.dispose();
					subShell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MAX | SWT.RESIZE);
					subShell.setSize(500, 500);
					subShell.setText("JSON string for ScenarioID " + scenarioID);
					menu = new Menu(subShell, SWT.BAR);
					subShell.setMenuBar(menu);

					mntmFile = new MenuItem(menu, SWT.CASCADE);
					mntmFile.setText("File");

					menu_1 = new Menu(mntmFile);
					mntmFile.setMenu(menu_1);

					goBackItem = new MenuItem(menu_1, SWT.PUSH);
					goBackItem.setText("Go Back");
					exitItem = new MenuItem(menu_1, SWT.PUSH);
					exitItem.setText("Exit");

					goBackItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							subShell.close();
							subShell = new Shell();
							outerShell.setVisible(true);
							outerShell.open();
							numberOfParameters = 0; // "Reset" the app window
							gotOnlyScenarioID = 0;
						}
					});

					exitItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							boolean exit = MessageDialog.openQuestion(subShell, "Exiting Application",
									"Do you really want to exit?");
							if (exit)
								System.exit(0);
						}
					});

					subShell.setLayout(new RowLayout());

					StyledText jsonText = new StyledText(subShell,
							SWT.V_SCROLL | SWT.V_SCROLL | SWT.RESIZE | SWT.WRAP | SWT.BORDER);
					jsonText.setBounds(10, 10, 300, 300);
					jsonText.setText(jsonStr);
					jsonText.setEditable(false);
					subShell.pack();

					final Clipboard cb = new Clipboard(display);
					Button copy = new Button(subShell, SWT.PUSH);
					copy.setText("Copy to clipboard");
					copy.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event e) {
							String textData = jsonText.getText();
							TextTransfer textTransfer = TextTransfer.getInstance();
							cb.setContents(new Object[] { textData }, new Transfer[] { textTransfer });
						}
					});

					copy.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							MessageDialog.openInformation(subShell, "",
									"The JSON content has been copied to the clipboard!");
						}
					});

					subShell.pack();
					setAppSize();
					subShell.open();
				}

				gotOnlyScenarioID = 0;
			}
		});

		exitItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				boolean exit = MessageDialog.openQuestion(subShell, "Exiting Application",
						"Do you really want to exit?");
				if (exit)
					System.exit(0);
			}
		});

		subShell.pack();
		setAppSize();
		subShell.open();

		return result;
	}

	private static List<RowCollection> scenarioIDInformation(List<RowCollection> information, String scenarioID) {
		List<RowCollection> scenarioIDInformation = new ArrayList<RowCollection>();

		for (RowCollection addRowColl : information) {
			if (addRowColl.getScenarioID().equals(scenarioID)) {
				scenarioIDInformation.add(addRowColl);
			}
		}

		return scenarioIDInformation;
	}

	public static List<LabelTextInputBean> getObjList() {
		return objList;
	}

	public static void setObjList(List<LabelTextInputBean> objList) {
		SubWindow.objList = objList;
	}
}
