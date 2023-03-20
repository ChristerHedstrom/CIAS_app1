package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class StyledTextCreate {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		StyledText text = new StyledText(shell, SWT.V_SCROLL | SWT.WRAP | SWT.BORDER);

		text.setBounds(10, 10, 200, 300);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}