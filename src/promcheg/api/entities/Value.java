package promcheg.api.entities;

/**
 * 
 * @author promc
 *
 * @param <T>
 */
public abstract class Value<T> {
	private ValueType type;
	private T value;
	private UnitType unit;

	public ValueType getType() {
		return type;
	}

	public void setType(ValueType type) {
		this.type = type;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public UnitType getUnit() {
		return unit;
	}

	public void setUnit(UnitType unit) {
		this.unit = unit;
	}
	
	public abstract T convertToUnit(UnitType resultUnit) throws NumberFormatException;
}
