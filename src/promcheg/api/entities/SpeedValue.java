package promcheg.api.entities;

import java.math.BigDecimal;

public class SpeedValue extends Value<BigDecimal> {

	@Override
	public BigDecimal convertToUnit(UnitType resultUnit) {
		switch(resultUnit) {
			
		case KILOMETER_PER_HOUR:
			break;
		case KILOMETER_PER_SECOND:
			break;
		case METER_PER_SECOND:
			break;
			
		case LIGHT_HOUR:
		case LIGHT_MINUTE:
		case LIGHT_SECOND:
		case LIGHT_YEAR:
		case AU:
			throw new NumberFormatException("");
		default:
			break;		
		}
		
		return getValue();
	}
}
