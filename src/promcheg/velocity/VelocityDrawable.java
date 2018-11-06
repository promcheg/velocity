package promcheg.velocity;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

public abstract class VelocityDrawable {
	public abstract void draw(GC gc, Composite content);
	private static int idx = 0;
	/**
	 * 
	 * @param entityType
	 * @param x
	 * @param y
	 * @return
	 */
	public static VelocityDrawable create(VelocityDrawableEntities entityType, int x, int y, int width, int height) {
		VelocityPoint result;
		switch(entityType) {
		case CIRCLE:
		case LINE:
		case POINT:
		default:
			result =new VelocityPoint(x, y, width, height);
			result.setCaption("Point_"+idx++);
			return result;
		}
	}
	
	public abstract double getDistance(int x1, int y1);
	public abstract String getCaption();
	public abstract void setCaption(String caption);

	protected abstract void onCanvasResize(int width, int height);
}
