package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class gridlayoutLab {
	public static void main(String[] args) {
		Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));

		Composite left = new Composite(shell, SWT.BORDER);
		Composite right = new Composite(shell, SWT.BORDER);

		final GridData leftData = new GridData(SWT.FILL, SWT.FILL, true, true);
		final GridData rightData = new GridData(SWT.FILL, SWT.FILL, true, true);

		left.setLayoutData(leftData);
		right.setLayoutData(rightData);

		shell.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				Point size = shell.getSize();

				leftData.widthHint = (int) (size.x * 0.75);
				rightData.widthHint = size.x - leftData.widthHint;

				System.out.println(leftData.widthHint + " + " + rightData.widthHint + " = " + size.x);
			}
		});

		shell.pack();
		shell.open();
		shell.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
