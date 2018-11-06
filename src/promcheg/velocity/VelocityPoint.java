package promcheg.velocity;

import java.math.BigDecimal;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import promcheg.api.utils.GraphUtils;

public class VelocityPoint extends VelocityDrawable {
	private static final int HEIGHT = 5;
	private static final int WIDTH = 5;
	private String caption;
	BigDecimal rx;
	BigDecimal ry;
	private int displayWidth;
	private int displayHeight;

	public VelocityPoint(int x, int y, int width, int height) {
		BigDecimal[] translated = GraphUtils.toRelativeCoordinates(x, y, width, height);
		rx = translated[0];
		ry = translated[1];
		this.displayWidth = width;
		this.displayHeight = height;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	@Override
	public void draw(GC gc, Composite content) {
		final Color red = new Color(content.getDisplay(), 0xFF, 0, 0);
		gc.setForeground(red);
		gc.setLineWidth(1);
		Point coords = GraphUtils.toDisplayCoordinates(rx, ry, displayWidth, displayHeight);
		
		gc.drawRoundRectangle(coords.x, coords.y, WIDTH, HEIGHT, 2, 2);
		Point size = gc.textExtent(caption);
		gc.drawString(caption, coords.x - size.x/2, coords.y + size.y);
	}
	
	public double getDistance(int x1, int y1) {
		Point coords = GraphUtils.toDisplayCoordinates(rx, ry, displayWidth, displayHeight);
		double result = Math.sqrt((coords.x-x1)*(coords.x-x1) + (coords.y-y1)*(coords.y-y1));	
		return result;
	}

	@Override
	protected void onCanvasResize(int width, int height) {
		this.displayHeight = height;
		this.displayWidth = width;
	}
}
