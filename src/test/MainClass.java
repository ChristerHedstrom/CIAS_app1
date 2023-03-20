package test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainClass {

	public static void main(String[] a) {
		Display d = new Display();
		Shell s = new Shell(d);
		s.setSize(1000, 1000);
		s.open();
		while (!s.isDisposed()) {
			if (!d.readAndDispatch())
				d.sleep();
		}
		d.dispose();
	}
}
