package helper;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Extension of the swt Label class with an id parameter. This makes it possible to
 * group labels with (custom) Combo and Text classes 
 * @author I325927
 *
 */
public class CustomLabel {
	private String id = "";
	private Label label;

	public CustomLabel(Composite parent, int style, String labelID) {
		label = new Label(parent, style);
		id = labelID;
	}
	
	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
