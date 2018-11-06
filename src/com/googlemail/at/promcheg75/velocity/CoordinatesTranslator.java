package com.googlemail.at.promcheg75.velocity;

import java.math.BigDecimal;

import org.eclipse.swt.graphics.Point;

public interface CoordinatesTranslator {
	Point getDisplayCoords(BigDecimal x, BigDecimal y);
}
