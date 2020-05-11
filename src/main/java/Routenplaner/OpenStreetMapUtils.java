package Routenplaner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OpenStreetMapUtils {

	private static OpenStreetMapUtils instance = null;
	private JSONParser jsonParser;

	public OpenStreetMapUtils() {
		jsonParser = new JSONParser();
	}

	public static OpenStreetMapUtils getInstance() {
		if (instance == null) {
			instance = new OpenStreetMapUtils();
		}
		return instance;
	}

	private String getRequest(String url) throws Exception {

		final URL urlObject = new URL(url);
		final HttpURLConnection httpUrlConnection = (HttpURLConnection) urlObject.openConnection();

		httpUrlConnection.setRequestMethod("GET");

		int httpURLConnectionResponseCode = httpUrlConnection.getResponseCode();

		if (httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
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

	public Map<String, Double> getCoordinates(String address) throws JSONException, ParseException {
		Map<String, Double> res;
		StringBuffer query;
		String[] split = address.split(" ");
		String queryResult = null;

		query = new StringBuffer();
		res = new HashMap<String, Double>();

		query.append("https://nominatim.openstreetmap.org/search?q=");

		if (split.length == 0) {
			return null;
		}

		for (int i = 0; i < split.length; i++) {
			query.append(split[i]);
			if (i < (split.length - 1)) {
				query.append("+");
			}
		}
		query.append("&format=json&amp;addressdetails=1");

		try {
			queryResult = getRequest(query.toString());
		} catch (Exception e) {
		}

		if (queryResult == null) {
			return null;
		}

		JSONParser parser = new JSONParser();

		JSONArray array = (JSONArray) parser.parse(queryResult);

		// if (obj instanceof JSONArray) {
		// JSONArray array = (JSONArray) obj;
		if (((Map<String, Double>) array).size() > 0) {
			JSONObject jsonObject = (JSONObject) array.get(0);
			//
			String lon = (String) jsonObject.get("lon");
			String lat = (String) jsonObject.get("lat");
			res.put("lon", Double.parseDouble(lon));
			res.put("lat", Double.parseDouble(lat));
			// }
		}
		return res;
	}
}
