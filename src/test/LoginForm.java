package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoginForm {
	static Text text = null;
	static Text text1 = null;
	static Shell shell = null;
	static Shell shell2 = null;

	public static void main(String[] args) {
		final Display display = new Display();
		shell = new Shell(display);

		shell.setLayout(new GridLayout());
		shell.setText("Login form");

		Label label = new Label(shell, SWT.NONE);
		label.setText("User Name");

		text = new Text(shell, SWT.BORDER);
		text.setTextLimit(5);

		Label label1 = new Label(shell, SWT.NONE);
		label1.setText("Password");

		text1 = new Text(shell, SWT.BORDER);
		text1.setTextLimit(5);

		Button submit = new Button(shell, SWT.PUSH);
		submit.setText("submit");

		submit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				/*
				 * shell.addListener(SWT.Close, new Listener() { public void handleEvent(Event
				 * event) { event.doit = false; } });
				 */
				shell.close();

				shell = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				shell.setText("Dialog Shell");

				shell.setLayout(new GridLayout());

				Button b = new Button(shell, SWT.PUSH);
				b.setText("Define user interface");
				b.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						shell2 = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
						shell2.setLayout(new GridLayout());
						shell2.setText("TESTER!");

						shell.setVisible(false);

						Button c = new Button(shell2, SWT.PUSH);
						c.setText("Get the old shell back!!");
						c.addListener(SWT.Selection, new Listener() {
							public void handleEvent(Event event) {
								reCreate(display);
								shell2.setVisible(false);
							}
						});

						shell2.pack();
						shell2.open();
					}
				});

				shell.pack();
				shell.open();
			}
		});

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void reCreate(Display display) {
		// final Display display = new Display();
		Shell shell = new Shell(display);

		shell.setLayout(new GridLayout());
		shell.setText("Login form");

		Label label = new Label(shell, SWT.NONE);
		label.setText("User Name");

		text = new Text(shell, SWT.BORDER);
		text.setTextLimit(5);

		Label label1 = new Label(shell, SWT.NONE);
		label1.setText("Password");

		text1 = new Text(shell, SWT.BORDER);
		text1.setTextLimit(5);

		Button submit = new Button(shell, SWT.PUSH);
		submit.setText("submit");

		shell.pack();
		shell.open();
	}

}