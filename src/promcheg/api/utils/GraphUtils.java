package promcheg.api.utils;

import java.math.BigDecimal;

import org.eclipse.swt.graphics.Point;

import promcheg.api.Constants;

public class GraphUtils {
	/**
	 * 
	 * @param rx
	 * @param ry
	 * @param w
	 * @param h
	 * @return
	 */
	public static  Point toDisplayCoordinates(BigDecimal rx, BigDecimal ry, int w, int h) {
		BigDecimal px = rx
										.add(BigDecimal.valueOf(1))
										.multiply(BigDecimal.valueOf(w))
										.divide(BigDecimal.valueOf(2), 
												Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE);
		
		BigDecimal py = BigDecimal.valueOf(h)
										.subtract(BigDecimal.valueOf(h)
										.multiply(ry))
										.divide(BigDecimal.valueOf(2),
												Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE);	
		
		return new Point(px.intValue(), py.intValue());
	}
	
	/**
	 * 
	 * @param px
	 * @param py
	 * @param w
	 * @param h
	 * @return
	 */
	public static  BigDecimal[] toRelativeCoordinates(int px, int py, int w, int h) {
		BigDecimal rx = BigDecimal.valueOf(px)
													.subtract(BigDecimal.valueOf(w)
													.divide(BigDecimal.valueOf(2),
															Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE))
													.divide(BigDecimal.valueOf(w)
															.divide(BigDecimal.valueOf(2),
																	Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE), 
															Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE);
		BigDecimal ry = BigDecimal.valueOf(-1)
													.multiply(BigDecimal.valueOf(py)
													.subtract(BigDecimal.valueOf(h)
													.divide(BigDecimal.valueOf(2),
															Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE))
													.divide(BigDecimal.valueOf(h)
															.divide(BigDecimal.valueOf(2),
																	Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE), 
															Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE));
		
		return new BigDecimal[] {rx, ry};
	}
}
