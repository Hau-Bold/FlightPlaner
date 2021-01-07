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
	private static final String OpenStreetMapURL = "https://nominatim.openstreetmap.org/search?street=%s&city=%s&postalcode=%s&country=%s&format=json";

	private OpenStreetMapService() {
		jsonParser = new JSONParser();// TODO inject from spring
	}

	@Override
	public GPSCoordinate getCoordinates(String street, String city, String postalCode, String country)
			throws ParseException, IOException {

		GPSCoordinate gps = null;
		final URL url = new URL(String.format(OpenStreetMapURL, street, city, postalCode, country));
		String queryResult = getRequest(url);
		JSONArray jsonArray = (JSONArray) jsonParser.parse(queryResult);

		if (jsonArray.size() > 0) {

			try {
				JSONObject jsonObject = (JSONObject) jsonArray.get(1);
				String latitude = String.valueOf(jsonObject.get(Constants.LATITUDE));
				String longitude = String.valueOf(jsonObject.get(Constants.LONGITUDE));
				gps = new GPSCoordinate(street, city, postalCode, country, Double.valueOf(latitude),
						Double.valueOf(longitude));
			} catch (IndexOutOfBoundsException ex) {
				new InformationProvider(Constants.MalformedUrl, JOptionPane.ERROR_MESSAGE, url.toString()).run();
			}
		} else {
			throw new IOException("Search did not return any results from OpenStreetMap");
		}

		return gps;

	}

	private static String getRequest(URL urlObject) throws IOException {

		final HttpURLConnection httpUrlConnection = (HttpURLConnection) urlObject.openConnection();

		httpUrlConnection.setRequestMethod("GET");
		httpUrlConnection.setRequestProperty("accept", "application/json");

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
}
