package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Snippet88 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Snippet 188");
		shell.setLayout(new GridLayout());
		// final ScrolledComposite sc = new ScrolledComposite(shell, SWT.BORDER |
		// SWT.H_SCROLL | SWT.V_SCROLL);
		final ScrolledComposite sc = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		Composite c = new Composite(sc, SWT.NONE);
		Composite cd = new Composite(sc, SWT.NONE);
		c.setLayout(new GridLayout(2, true));
		Label label;
		Text text;
		Button button;

		for (int x = 0; x < 20; x++) {
			label = new Label(c, SWT.NONE);
			label.setText("TEST" + ":");
			label.setVisible(true);

			text = new Text(c, SWT.BORDER);
			text.setTextLimit(40);
		}

		sc.setContent(c);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setShowFocusedControl(true);

		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}