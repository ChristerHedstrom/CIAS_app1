package helper;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Extension of the swt Combo class with an id parameter. This makes it possible to
 * group one combobox with (custom) Label and Text classes on the id.
 * @author I325927
 *
 */
public class CustomCombobox {
	private String id;
	private Combo comboBox;

	public CustomCombobox(Composite parent, int style, String comboID) {
		comboBox = new Combo(parent, style);
		id = comboID;
	}

	public Combo getComboBox() {
		return comboBox;
	}

	public void setComboBox(Combo comboBox) {
		this.comboBox = comboBox;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
