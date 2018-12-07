package promcheg.api.dialogs;

import java.util.Arrays;
import java.util.HashMap;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import promcheg.api.entities.Value;

/**
 * 
 * @author promc
 *
 */
public class ValueInputDialog extends TitleAreaDialog {

	private String title;
	private String message;
	private HashMap<String, Value<?>> valueMap = new HashMap<>();
	private HashMap<String, Text> inputMap = new HashMap<>();

	public ValueInputDialog(Shell parentShell, String title, String message, Value... values) {
		super(parentShell);
		this.title = title;
		this.message = message;
		Arrays.stream(values).forEach(value->{
			valueMap.put(value.getKey(), value);
		});
	}

	@Override
	public void create() {
		super.create();
		setTitle(this.title);
		setMessage(this.message);
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	protected Control createDeailogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);
        
        createInputs(parent);

        return area;
	}

	private void createInputs(Composite container) {
        Label lbtFirstName = new Label(container, SWT.NONE);
        lbtFirstName.setText("First Name");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;
        
        valueMap.forEach((k,v)->{
            Text input = new Text(container, SWT.BORDER);
            input.setLayoutData(dataFirstName);
            
            inputMap.put(k, input);
        });        
	}
	
    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

	private void saveInput() {
		inputMap.forEach((k,input)->{
			valueMap.get(k).parseValue(input.getText());
		});
	}
}
