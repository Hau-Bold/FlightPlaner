package gps_coordinates;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;

import Routenplaner.OpenStreetMapUtils;

/**
 * a class, that contains all methods to request the corresponding
 * gps-coordinates to given location
 * 
 *
 */
public class GPS {

	// final static String GOOGLEAPISTART =
	// "http://maps.googleapis.com/maps/api/geocode/xml?address=";
	final static String GOOGLEAPISTART = "https://nominatim.openstreetmap.org/search.php?q=";
	// final static String GOOGLEAPIEND = "&sensor=false&oe=utf-8";
	final static String GOOGLEAPIEND = "&&polygon_geojson=1&viewbox=";
	final static String RESULT = "result";
	final static String GEOMETRY = "geometry";
	final static String LOCATION = "location";
	final static String LATITUDE = "lat";
	final static String LONGITUDE = "lng";
	private static String latitude;
	private static String longitude;

	//
	public static GpsCoordinate requestGPS(String receiving)
			throws MalformedURLException, JSONException, org.json.simple.parser.ParseException {

		URL url = new URL(GOOGLEAPISTART + receiving + GOOGLEAPIEND);
		// Document doc = null;

		Map<String, Double> coordinates = OpenStreetMapUtils.getInstance().getCoordinates(receiving);

		System.out.println("fuck u");

		// try {
		// SAXBuilder builder = new SAXBuilder();
		// doc = builder.build(url);
		// } catch (IOException e) {
		// System.err.println(String.format("not able to open stream to < %s >", url));
		// e.printStackTrace();
		// }

		return new GpsCoordinate(0, 0);
		//
		// if (doc != null) {
		// Element root = doc.getRootElement();
		// Element result = root.getChild(RESULT);
		// Element geometry = result.getChild(GEOMETRY);
		// Element location = geometry.getChild(LOCATION);
		// latitude = location.getChild(LATITUDE).getValue();
		// longitude = location.getChild(LONGITUDE).getValue();
		// }
		//
		// if ((latitude != null) && (longitude != null)) {
		// return new GpsCoordinate(Double.valueOf(latitude),
		// Double.valueOf(longitude));
		// } else
		// return null;
	}

}
