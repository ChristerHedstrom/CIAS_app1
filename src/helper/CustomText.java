package helper;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Extension of the swt Text class with an id parameter. This makes it possible to
 * group text with (custom) Combo and Label classes 
 * @author I325927
 *
 */
public class CustomText {
	private String id = "";
	private Text text;

	public CustomText(Composite parent, int style, String compID) {
		text = new Text(parent, style);
		id = compID;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
