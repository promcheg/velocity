package promcheg.api.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.eclipse.swt.graphics.Point;

public class GraphUtils {
	public static  Point toDisplayCoordinates(BigDecimal rx, BigDecimal ry, int w, int h) {
		BigDecimal px = rx.add(BigDecimal.valueOf(1)).multiply(BigDecimal.valueOf(w)).divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
		
		BigDecimal py = BigDecimal.valueOf(h).subtract(BigDecimal.valueOf(h).multiply(ry)).divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);	
		
		return new Point(px.intValue(), py.intValue());
	}
	
	public static  BigDecimal[] toRelativeCoordinates(int px, int py, int w, int h) {
		BigDecimal rx = BigDecimal.valueOf(px)
													.subtract(BigDecimal.valueOf(w)
													.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP))
													.divide(BigDecimal.valueOf(w)
													.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP), 10, RoundingMode.HALF_UP);
		BigDecimal ry = BigDecimal.valueOf(-1)
													.multiply(BigDecimal.valueOf(py)
													.subtract(BigDecimal.valueOf(h)
													.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP))
													.divide(BigDecimal.valueOf(h)
													.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP), 10, RoundingMode.HALF_UP));
		
		return new BigDecimal[] {rx, ry};
	}
}
