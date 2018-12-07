package promcheg.velocity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class VelocityDisplay  {
	private int width;
	private int height;
	private Shell parent;	
	private Composite content;
	private ArrayList<VelocityDrawable> entityList = new ArrayList<>();
	boolean drawGrid = true;

	public VelocityDisplay(Shell parent) {
		this.parent = parent;
		
		content = new Composite(parent, SWT.NONE);
		this.parent.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {
				width = parent.getClientArea().width;
				height = parent.getClientArea().height;
				
				entityList.forEach(entity->{
					entity.onCanvasResize(width, height);
				});
				
				parent.redraw();
			}			
		});		
		
		this.parent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(final MouseEvent e) {
				super.mouseUp(e);
				if(e.button == 1) 
				{
					entityList.add(VelocityDrawable.create(VelocityDrawableEntities.POINT, e.x, e.y, width, height));
					parent.redraw();					
				}
				else if(e.button == 3) 
				{
					Menu menu = createPopupMenu(parent, e.x, e.y);
					menu.setVisible(true);
				}
			}
		});
		
		parent.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent event) {
				GC gc = event.gc;
				if(drawGrid) {
					drawGrid(gc);	
				}
				
				final AtomicReference<GC> atomicGC = new AtomicReference<>(gc);
				
				entityList.forEach(entity->{
					entity.draw(atomicGC.get(), parent);
				});	
			}
		});	
		
		parent.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if((e.stateMask & SWT.CTRL) == SWT.CTRL && e.keyCode == 'g') {
					drawGrid = !drawGrid;
					parent.redraw();
				}
			}
			
		});
	}
	
	protected void drawGrid(GC gc) {
		final Color red = new Color(content.getDisplay(), 211, 211, 211);
		gc.setForeground(red);
		
		int x0 = BigDecimal.valueOf(width).divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP).intValue();
		int y0 = BigDecimal.valueOf(height).divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP).intValue();
		
		for(int x = x0; x < width; x += 10) {
			gc.drawLine(x, 0, x, height);
		}
		
		for(int x = x0; x > 0; x -=10) {
			gc.drawLine(x, 0, x, height);
		}
		
		for(int y = y0; y < height; y += 10) {
			gc.drawLine(0, y, width, y);
		}
		
		for(int y = y0; y > 0; y -=10) {
			gc.drawLine(0, y, width, y);
		}
		
		gc.setLineWidth(5);
		gc.setForeground(new Color(content.getDisplay(), 0, 0, 0));
		gc.drawLine(x0, 0, x0, height);
		gc.drawLine(0, y0, width, y0);
	}

	protected VelocityDrawable getEventEntity(final int x, final int y) {
		final AtomicReference<VelocityDrawable> searchEntity = new AtomicReference<VelocityDrawable>(null);
		final AtomicInteger minDistance = new AtomicInteger(Integer.MAX_VALUE); 
		final AtomicInteger currentDistance = new AtomicInteger(0);
		entityList.forEach(entity->{
			currentDistance.set( (int)entity.getDistance(x, y));
			if(currentDistance.get() < minDistance.get()) {
				minDistance.set(currentDistance.get());
				searchEntity.set(entity);
			}
		});
		return searchEntity.get();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public class NameDialog extends InputDialog {

	    public NameDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue, IInputValidator validator) {
	        super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	    }

	    @Override
	    protected int getInputTextStyle() {
	        return super.getInputTextStyle() ;
	    }
	}	
	
	public Menu createPopupMenu(Shell parent, int x, int y) {
		Menu result = new Menu(parent);
		
		MenuItem renameEntity = new MenuItem(result, SWT.NONE);
		renameEntity.setText("Rename entity");
		
		MenuItem setBoundary = new MenuItem(result, SWT.NONE);
		setBoundary.setText("Set expanse");
		
		renameEntity.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				
				VelocityDrawable entity = getEventEntity(x, y);
				if(entity == null) {
					return;
				}
				
				NameDialog dialog = new NameDialog(parent, "Object caption", "Input object caption", entity.getCaption(), new IInputValidator() {
					
					@Override
					public String isValid(String checkString) {
						return null;
					}
				});
				dialog.open();
				String caption = dialog.getValue();
				entity.setCaption(caption != null ? caption : entity.getCaption());
				parent.redraw();
			}			
		});
		
		setBoundary.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				
				InputDialog dialog = new InputDialog(parent, "Boundary", "Set boundary", "", new IInputValidator() {
					
					@Override
					public String isValid(String check) {
						try {
							Double.parseDouble(check);
						} catch(Exception ex) {
							return ex.getMessage();
						}
						return null;
					}
				});
				
				dialog.open();
			}			
		});
		
		return result;
	}
}
