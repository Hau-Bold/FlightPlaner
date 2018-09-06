package weather;

public class Humidity
{
	private String value;
	private String unit;
	
	@Override
	public String toString() {
		return "Humidity [value=" + value + ", unit=" + unit + "]";
	}

	public Humidity(String value, String unit) {
		this.value=value;
		this.unit=unit;
	}
}

