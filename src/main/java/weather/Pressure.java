package weather;

public class Pressure
{
	@Override
	public String toString() {
		return "Pressure [value=" + value + ", unit=" + unit + "]";
	}
	public Pressure(String value, String unit) {
		this.value=value;
		this.unit=unit;
	}
	private String value;
	private String unit;
}

