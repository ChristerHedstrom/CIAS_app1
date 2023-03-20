package windows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Test to see that it's possible (or easy) to put a window before the Main
 * Window to choose what function to pick. (This can be a way to go when the
 * second app will be created. Might be simpler/better to just have one app.
 * 
 * If the main method here is run, then the Main Window can be chosen, named
 * "Function1", and started (while this window is hidden).
 * 
 * @author I325927
 *
 */
public class SelectFunc {
	private static SelectFunc selectFunc;
	private Display display;
	private Shell selectFuncShell;
	private int APP_SIZE_WIDTH = 720;
	private int APP_SIZE_HEIGTH_FORMFACTOR = 39;
	private int numberOfParameters = 0;
	private Combo comboBox;
	private Label lblNewLabel;
	private GridData labelConfig;
	private static MainWindow window;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			selectFunc = new SelectFunc();
			selectFunc.open();
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
		selectFuncShell.open();
		while (!selectFuncShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		String[] functions = { "Function1", "Function2" };
		selectFuncShell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		setAppSize();

		selectFuncShell.setText("Select Function");
		selectFuncShell.setLayout(new GridLayout(2, false));
		lblNewLabel = new Label(selectFuncShell, SWT.NONE);
		lblNewLabel.setText("Select which function to run:");
		lblNewLabel.setLayoutData(labelConfig);
		lblNewLabel.setVisible(true);
		comboBox = new Combo(selectFuncShell, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboBox.setSize(150, 100);
		comboBox.setItems(functions);
		comboBox.setVisible(true);

		comboBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String function = e.widget.toString().replace("Combo {", "").replace("}", "").trim();

				if (function.equals("Function1")) {
					selectFuncShell.setVisible(false);
					window.setup();
				}
			}
		});
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
		selectFuncShell.setSize(APP_SIZE_WIDTH, app_height);
	}
}
