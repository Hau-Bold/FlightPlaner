package weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class RequestWeather {
	private static final String TEMPERATURE = "temperature";
	private static final String HUMIDITY = "humidity";
	private static final String PRESSURE = "pressure";
	private static final String WIND = "wind";
	private static final String VALUE = "value";
	private static final String MAX = "max";
	private static final String MIN = "min";
	private static final String UNIT = "unit";
	private static final String SPEED = "speed";
	private static final String DIRECTION = "direction";
	private static final String NAME = "name";
	private static final String CLOUDS = "clouds";
	private static final String VISIBILITY = "visibility";
	private static final String LASTUPDATE = "lastupdate";
	final String WEATHERURLSTART = "http://samples.openweathermap.org/data/2.5/weather?q=";
	final String WEATHERURLEND = "&mode=xml&appid=b1b15e88fa797225412429c1c50c122a1";
	private String city;

	public RequestWeather(String city) {
		this.city = city;

	}

	public void getWeather() throws JDOMException, MalformedURLException {
		URL url = new URL(WEATHERURLSTART + city + WEATHERURLEND);
		Document doc = null;

		try {
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(url);
		} catch (IOException e) {
			System.err.println(String.format("not able to open stream to < %s >", url));
			e.printStackTrace();
		}

		if (doc != null) {
			Element root = doc.getRootElement();
			Element tempElement = root.getChild(TEMPERATURE);
			Temperature temperature = new Temperature(tempElement.getAttribute(VALUE).toString(),
					tempElement.getAttribute(MIN).toString(), tempElement.getAttribute(MAX).toString(),
					tempElement.getAttribute(UNIT).toString());
			Element humidityElement = root.getChild(HUMIDITY);
			Humidity humidity = new Humidity(humidityElement.getAttribute(VALUE).toString(),
					humidityElement.getAttribute(UNIT).toString());
			Element pressureElement = root.getChild(PRESSURE);
			Pressure pressure = new Pressure(pressureElement.getAttribute(VALUE).toString(),
					pressureElement.getAttribute(UNIT).toString());
			Element windElement = root.getChild(WIND);
			Element speed = windElement.getChild(SPEED);
			Element direction = windElement.getChild(DIRECTION);
			Wind wind = new Wind(speed.getAttribute(VALUE).toString(), speed.getAttribute(NAME).toString(),
					direction.getAttribute(VALUE).toString(), direction.getAttribute(NAME).toString());

			Element clouds = root.getChild(CLOUDS);
			Cloud cloud = new Cloud(clouds.getAttribute(VALUE).toString(), clouds.getAttribute(NAME).toString());

			Element visible = root.getChild(VISIBILITY);
			Visibility visibility = new Visibility(visible.getAttribute(VALUE).toString());

			Element lastUpdate = root.getChild(LASTUPDATE);

			Weather weather = new Weather(lastUpdate.getAttribute(VALUE).toString(), temperature, humidity, pressure, wind, cloud,
					visibility);
			System.out.println(weather.toString());

		 Element pressur = root.getChild(PRESSURE);
			// Element wind = root.getChild(WIND);
		}

		// if ((latitude != null) && (longitude != null)) {
		// return new GpsCoordinate(Double.valueOf(latitude),
		// Double.valueOf(longitude));
		// } else
		// return null;
	}

}
