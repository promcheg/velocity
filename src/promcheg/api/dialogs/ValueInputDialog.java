package promcheg.api.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author promc
 *
 */
public class ValueInputDialog extends TitleAreaDialog {

	private String title;
	private String message;

	public ValueInputDialog(Shell parentShell, String title, String message) {
		super(parentShell);
		this.title = title;
		this.message = message;
	}

	@Override
	public void create() {
		super.create();
		setTitle(this.title);
		setMessage(this.message);
	}	
}
