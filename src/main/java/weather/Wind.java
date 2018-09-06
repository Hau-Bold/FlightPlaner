package weather;

public class Wind
{
	@Override
	public String toString() {
		return "Wind [speedValue=" + speedValue + ", speedName=" + speedName + ", directionValue=" + directionValue
				+ ", directionName=" + directionName + "]";
	}

	private String speedValue;
	private String speedName;
	private String directionValue;
	private String directionName;
	
	public Wind(String speedValue, String speedName, String directionValue, String directionName) {
		this.speedValue=speedValue;
		this.speedName=speedName;
		this.directionValue=directionValue;
		this.directionName=directionName;
	}



}
