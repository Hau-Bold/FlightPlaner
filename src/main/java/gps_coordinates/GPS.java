package gps_coordinates;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * a class, that contains all methods to request the corresponding
 * gps-coordinates to given location
 * 
 *
 */
public class GPS {

	final static String GOOGLEAPISTART = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
	final static String GOOGLEAPIEND = "&sensor=false&oe=utf-8";
	final static String RESULT = "result";
	final static String GEOMETRY = "geometry";
	final static String LOCATION = "location";
	final static String LATITUDE = "lat";
	final static String LONGITUDE = "lng";
	private static String latitude;
	private static String longitude;

	/**
	 * to get the GpsCoordinate to a given location
	 * 
	 * @param location
	 *            - the location
	 * @return
	 * @throws MalformedURLException
	 *             - in case of technical error
	 * @throws JDOMException
	 *             - in case of technical error
	 */
	public static GpsCoordinate requestGPS(String receiving) throws MalformedURLException, JDOMException {

		URL url = new URL(GOOGLEAPISTART + receiving + GOOGLEAPIEND);
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
			Element result = root.getChild(RESULT);
			Element geometry = result.getChild(GEOMETRY);
			Element location = geometry.getChild(LOCATION);
			latitude = location.getChild(LATITUDE).getValue();
			longitude = location.getChild(LONGITUDE).getValue();
		}

		if ((latitude != null) && (longitude != null)) {
			return new GpsCoordinate(Double.valueOf(latitude), Double.valueOf(longitude));
		} else
			return null;
	}

}
