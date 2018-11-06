package promcheg.velocity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class VelocityDisplay  {
	private int width;
	private int height;
	private Shell parent;	
	private Composite content;
	private ArrayList<VelocityDrawable> entityList = new ArrayList<>();
	
	public VelocityDisplay(Shell parent) {
		this.parent = parent;
		
		content = new Composite(parent, SWT.NONE);
		this.parent.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {
				final int oldWidth = width;
				final int oldHeight = height;
				
				width = parent.getSize().x;
				height = parent.getSize().y;
				
				entityList.forEach(entity->{
					entity.onCanvasResize(oldWidth, oldHeight, width, height);
				});
				
				parent.redraw();
			}			
		});
		
		this.content.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				super.mouseUp(e);
				entityList.add(VelocityDrawable.create(VelocityDrawableEntities.POINT, e.x, e.y));
				content.redraw();
			}			
		});
		
		this.parent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(final MouseEvent e) {
				super.mouseUp(e);
				if(e.button == 1) 
				{
					entityList.add(VelocityDrawable.create(VelocityDrawableEntities.POINT, e.x, e.y));
					parent.redraw();					
				}
				else if(e.button == 3) 
				{
					final int x = e.x;
					final int y = e.y;
					
					VelocityDrawable entity = getEventEntity(x, y);
					
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
			}
		});
		
		parent.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent event) {
				GC gc = event.gc;
				
				final AtomicReference<GC> atomicGC = new AtomicReference<>(gc);
				
				entityList.forEach(entity->{
					entity.draw(atomicGC.get(), parent);
				});	
			}
		});		
	}
	
	protected VelocityDrawable getEventEntity(final int x, final int y) {
		final AtomicReference<VelocityDrawable> searchEntity = new AtomicReference<VelocityDrawable>(null);
		final AtomicInteger minDistance = new AtomicInteger(Integer.MAX_VALUE); 
		final AtomicInteger currentDistance = new AtomicInteger(0);
		entityList.forEach(entity->{
			currentDistance.set( (int)entity.getDistance(x, y));
			System.out.println(currentDistance.get());
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
}
