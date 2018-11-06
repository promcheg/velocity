package com.googlemail.at.promcheg75.velocity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class VelocityPoint extends VelocityDrawable {
	private static final int HEIGHT = 5;
	private static final int WIDTH = 5;
	Point coords;
	private String caption;

	public VelocityPoint(int x, int y) {
		coords = new Point(x, y);
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
		gc.drawRoundRectangle(coords.x, coords.y, WIDTH, HEIGHT, 2, 2);
		Point size = gc.textExtent(caption);
		gc.drawString(caption, coords.x - size.x/2, coords.y + size.y);
	}
	
	public double getDistance(int x1, int y1) {
		double result = Math.sqrt((coords.x-x1)*(coords.x-x1) + (coords.y-y1)*(coords.y-y1));	
		return result;
	}

	@Override
	protected void onCanvasResize(int oldWidth, int oldHeight, int width, int height) {
		System.out.println(coords.x +", " +oldWidth + ":" + BigDecimal.valueOf(coords.x).divide(BigDecimal.valueOf(oldWidth), 10, RoundingMode.HALF_UP ).doubleValue());
		coords.x = BigDecimal.valueOf(coords.x).multiply(BigDecimal.valueOf(width)).divide(BigDecimal.valueOf(oldWidth), 10, RoundingMode.HALF_UP).intValue();
		coords.y = BigDecimal.valueOf(coords.y).multiply(BigDecimal.valueOf(height)).divide(BigDecimal.valueOf(oldHeight), 10, RoundingMode.HALF_UP).intValue();
	}
}
