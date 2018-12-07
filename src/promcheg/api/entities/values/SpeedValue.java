package promcheg.api.entities.values;

import java.math.BigDecimal;

import promcheg.api.Constants;
import promcheg.api.entities.UnitType;
import promcheg.api.entities.Value;

public class SpeedValue extends Value<BigDecimal> {

	@Override
	public BigDecimal convertToUnit(UnitType resultUnit) {
		BigDecimal result = getValue();
		if(isAlowedUnitType(resultUnit) && getUnit() != resultUnit) {
			switch(resultUnit) {	
			case KILOMETER_PER_HOUR:
				if(getUnit() == UnitType.KILOMETER_PER_SECOND) {
					result = getValue()
							.multiply(BigDecimal.valueOf(3600));
				} else if(getUnit() == UnitType.METER_PER_SECOND) {
					result = getValue()
							.multiply(BigDecimal.valueOf(1000))
							.divide(BigDecimal.valueOf( 3600), Constants.MATH_PRECISION, Constants.MATH_ROUNDING_MODE);							
				}
				break;
			case KILOMETER_PER_SECOND:
				if(getUnit() == UnitType.KILOMETER_PER_HOUR) {
					
				} else if(getUnit() == UnitType.METER_PER_SECOND) {
					
				}				
				break;
			case METER_PER_SECOND:
				if(getUnit() == UnitType.KILOMETER_PER_SECOND) {
					
				} else if(getUnit() == UnitType.KILOMETER_PER_HOUR) {
					
				}
				break;
			default:
				break;		
			}
		}
		
		return result;
	}

	@Override
	public UnitType[] getAlowedUnitList() {
		return new UnitType[] {
					UnitType.KILOMETER_PER_SECOND,
					UnitType.KILOMETER_PER_HOUR,
					UnitType.METER_PER_SECOND
					};
	}

	@Override
	public void parseValue(String stringValue) {
		this.setValue(new BigDecimal(stringValue));
	}
}
