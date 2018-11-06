package promcheg.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Constants {
	public final static BigDecimal C = BigDecimal.valueOf(299792458); // m/s
	public final static int MATH_PRECISION = 10;
	public final static RoundingMode MATH_ROUNDING_MODE = RoundingMode.HALF_UP; 
}
