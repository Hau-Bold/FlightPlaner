package weather;

import org.jdom.Attribute;

public class Weather {


	private String lastUpdate;
	private Temperature temperature;
	private Humidity humidity;
	private Pressure pressure;
	private Wind wind;
	private Cloud cloud;
	private Visibility visibility;

	public Weather(String lastUpdate, Temperature temperature, Humidity humidity, Pressure pressure, Wind wind,
			Cloud cloud, Visibility visibility) {
		super();
		this.lastUpdate = lastUpdate;
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		this.wind = wind;
		this.cloud = cloud;
		this.visibility = visibility;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public Humidity getHumidity() {
		return humidity;
	}

	public Pressure getPressure() {
		return pressure;
	}

	public Wind getWind() {
		return wind;
	}

	public Cloud getCloud() {
		return cloud;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public Temperature getTemperature() {
		return temperature;
	}
	
	
	@Override
	public String toString() {
		return "Weather [lastUpdate=" + lastUpdate + ", temperature=" + temperature.toString() + ", humidity=" + humidity.toString()
				+ ", pressure=" + pressure.toString() + ", wind=" + wind.toString() + ", cloud=" + cloud.toString() + ", visibility=" + visibility.toString() + "]";
	}
	
}
