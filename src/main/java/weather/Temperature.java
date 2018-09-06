package weather;

public class Temperature
{
	@Override
	public String toString() {
		return "Temperature [min=" + min + ", max=" + max + ", unit=" + unit + "]";
	}
	private String min;
	private String max;
	private String unit;
	public Temperature(String min, String max, String unit, String string) {
		this.min = min;
		this.max = max;
		this.unit = unit;
	}
}
