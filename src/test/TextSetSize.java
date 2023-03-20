package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextSetSize {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(1000, 1000);
		Text text = new Text(shell, SWT.BORDER);
		text.setText("0123456789012345678901234567890123456789");
		text.setSize(340, 25);
//	    Point p = text.getSize();
//	    System.out.println("p: x " + p.x + " " + p.y); 
//	    shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
