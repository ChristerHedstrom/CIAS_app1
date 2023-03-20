package windows;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import helper.GetExcelFilePath;
import helper.Helper;
import helper.LabelTextInputBean;
import readexcel.ColumnCollection;
import readexcel.ErrorInExcelFile;
import readexcel.ReadExcel;

public class MainWindow {
	private Shell shlConvertExcelTo;
	private Combo comboBox;
	private Label lblNewLabel;
	private ReadExcel readExcel;
	private List<LabelTextInputBean> objList;
	private Button button;
	private static MainWindow window;
	private String[] listOfScenarioIDs;
	private Menu menu;
	private MenuItem mntmFile;
	private Menu menu_1;
	private MenuItem exitItem;
	private Display display;
	private GridData textConfig;
	private GridData labelConfig;
	private int APP_SIZE_WIDTH = 720;
	private int APP_SIZE_HEIGTH_FORMFACTOR = 39;
	private int LABEL_WIDTH_HINT = 350;
	private int numberOfParameters = 0;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setup() {
		try {
			window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shlConvertExcelTo.open();
		openExcelFile();
		while (!shlConvertExcelTo.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void setAppSize() {
		int padding = 0;

		if (numberOfParameters == 0) {
			numberOfParameters = 15;
		} else if (numberOfParameters <= 10) {
			padding = 150;
		} else if (numberOfParameters <= 15) {
			padding = 70;
		}

		int app_height = (APP_SIZE_HEIGTH_FORMFACTOR * numberOfParameters) + padding;
		shlConvertExcelTo.setSize(APP_SIZE_WIDTH, app_height);
	}

	/**
	 * The Excel file is picked from a property file. In that file an absolute or
	 * relative file path can be given.
	 */
	private void openExcelFile() {
		try {
			readExcel = new ReadExcel(GetExcelFilePath.getFilePath("ExcelFilePath"));
			ColumnCollection colSheet0 = null;
			ColumnCollection colSheet1 = null;

			if (readExcel.readExcelSheet(0)) {
				colSheet0 = readExcel.getExcelColumn(0);
			} else {
				colSheet0 = null;
			}

			if (readExcel.readExcelSheet(1)) {
				colSheet1 = readExcel.getExcelColumn(0);
			} else {
				colSheet1 = null;
			}

			listOfScenarioIDs = Helper.getUniqueItems(colSheet0, colSheet1);
			chooseScenarioID(listOfScenarioIDs);
		} catch (ErrorInExcelFile | NullPointerException e) {
			Helper.FilePathErrorDialog(shlConvertExcelTo, "ExcelFilePath");
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlConvertExcelTo = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		setAppSize();
		shlConvertExcelTo.setText("Convert Excel to JSON");
		shlConvertExcelTo.setLayout(new GridLayout(2, false));

		textConfig = new GridData();
		textConfig.grabExcessHorizontalSpace = true;
		textConfig.horizontalAlignment = GridData.FILL;

		labelConfig = new GridData();
		labelConfig.widthHint = LABEL_WIDTH_HINT;

		menu = new Menu(shlConvertExcelTo, SWT.BAR);
		shlConvertExcelTo.setMenuBar(menu);

		mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		exitItem = new MenuItem(menu_1, SWT.PUSH);
		exitItem.setText("E&xit");

		lblNewLabel = new Label(shlConvertExcelTo, SWT.NONE);
		lblNewLabel.setText("Scenario ID:");
		lblNewLabel.setLayoutData(labelConfig);
		lblNewLabel.setVisible(false);
		comboBox = new Combo(shlConvertExcelTo, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboBox.setSize(150, 100);
		comboBox.setVisible(false);

		exitItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean exit = MessageDialog.openQuestion(shlConvertExcelTo, "Exiting Application",
						"Do you really want to exit?");

				if (exit)
					System.exit(0);
			}
		});
	}

	private void chooseScenarioID(String[] scenarioIDs) {
		shlConvertExcelTo.setLayout(new GridLayout(2, false));

		menu = new Menu(shlConvertExcelTo, SWT.BAR);
		shlConvertExcelTo.setMenuBar(menu);

		mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		exitItem = new MenuItem(menu_1, SWT.PUSH);
		exitItem.setText("E&xit");

		exitItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean exit = MessageDialog.openQuestion(shlConvertExcelTo, "Exiting Application",
						"Do you really want to exit?");
				if (exit)
					System.exit(0);
			}
		});

		lblNewLabel.setVisible(true);
		comboBox.setItems(scenarioIDs);
		comboBox.setVisible(true);

		shlConvertExcelTo.pack();
		setAppSize();
		shlConvertExcelTo.open();

		comboBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (objList != null) {
					for (LabelTextInputBean tmp : objList) {
						if (!tmp.getLabel().getLabel().isDisposed()) {
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

						if (!button.isDisposed()) {
							button.dispose();
						}
					}
				}

				String id = e.widget.toString().replace("Combo {", "").replace("}", "").trim();

				try {
					if ((id != null) && (!id.equals(""))) {
						SubWindow.open(id, shlConvertExcelTo, listOfScenarioIDs, readExcel);
					}
				} catch (ErrorInExcelFile e1) {
					MessageDialog.openError(shlConvertExcelTo, "An error occured",
							"The chosen file was not correct. Please try again with another.");
					return;
				}
			}
		});
	}
}