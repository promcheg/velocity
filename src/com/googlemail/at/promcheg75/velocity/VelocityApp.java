package com.googlemail.at.promcheg75.velocity;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 * @author promc
 *
 */

public class VelocityApp {
	protected Shell mainShell;
	private VelocityDisplay veloctiyContent;
	
	public static void main(String... args) {
		try {
			VelocityApp window = new VelocityApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		mainShell.open();
		mainShell.layout();
		while (!mainShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	protected void createContents() {
		mainShell = new Shell();
		mainShell.setSize(1200, 1200);
		mainShell.setText("Veloctiy simulator");
		
		this.veloctiyContent = new VelocityDisplay(mainShell);
	}
}
