package routePlanning.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import routePlanning.Constants.Constants;
import routePlanning.Contract.IOpenStreetMapService;
import view.Impl.InformationProvider;

public class OpenStreetMapService implements IOpenStreetMapService {

	private JSONParser jsonParser;
	private static final String OpenStreetMapURLPrePart = "https://nominatim.openstreetmap.org/search?q=";
	private static final String OpenStreetMapURLPostPart = "&format=json&amp;addressdetails=1";

	private OpenStreetMapService() {
		jsonParser = new JSONParser();// TODO inject from spring
	}

	private static String getRequest(String url) throws IOException {

		final URL urlObject = new URL(url);
		final HttpURLConnection httpUrlConnection = (HttpURLConnection) urlObject.openConnection();

		httpUrlConnection.setRequestMethod("GET");

		int responseCode = httpUrlConnection.getResponseCode();

		if (httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {

			new InformationProvider(Constants.MalformedUrl, JOptionPane.ERROR_MESSAGE, String.valueOf(responseCode))
					.run();

			return null;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	@Override
	public GPSCoordinate getCoordinates(String street, String city, String country) throws ParseException, IOException {

		GPSCoordinate gps = null;
		String url = buildOpenStreetMapUrlFrom(street, city, country);
		String queryResult = getRequest(url);
		JSONArray jsonArray = (JSONArray) jsonParser.parse(queryResult);

		try {
			JSONObject jsonObject = (JSONObject) jsonArray.get(1);
			String latitude = String.valueOf(jsonObject.get(Constants.LATITUDE));
			String longitude = String.valueOf(jsonObject.get(Constants.LONGITUDE));
			gps = new GPSCoordinate(street, city, country, Double.valueOf(latitude), Double.valueOf(longitude));
		} catch (IndexOutOfBoundsException ex) {
			new InformationProvider(Constants.MalformedUrl, JOptionPane.ERROR_MESSAGE, url).run();
		}

		return gps;
	}

	private static String buildOpenStreetMapUrlFrom(String street, String city, String country) {

		StringBuilder builder = new StringBuilder();
		if (!country.isEmpty()) {
			builder.append(country);
			builder.append("&");
		}

		builder.append(city);

		if (!street.isEmpty()) {
			String streetToAppend = adaptStreet(street);
			builder.append("&");
			builder.append(streetToAppend);
		}

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(OpenStreetMapURLPrePart);

		String location = builder.toString();
		location = RoutePlanningHelper.replaceUnusableChars(location);
		String[] locationSeparatedBySpace = location.split(" ");

		if (locationSeparatedBySpace.length == 0) {
			return null;
		}

		for (int i = 0; i < locationSeparatedBySpace.length; i++) {
			stringBuffer.append(locationSeparatedBySpace[i]);
			if (i < (locationSeparatedBySpace.length - 1)) {
				stringBuffer.append("+");
			}
		}

		stringBuffer.append(OpenStreetMapURLPostPart);

		return stringBuffer.toString();
	}

	private static String adaptStreet(String street) {
		return street.replaceAll(" ", "");
	}
}
