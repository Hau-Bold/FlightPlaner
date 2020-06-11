package routePlanning.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import routePlanning.Constants.Constants;
import routePlanning.Contract.IOpenStreetMapService;

public class OpenStreetMapService implements IOpenStreetMapService {

	private JSONParser jsonParser;

	private OpenStreetMapService() {
		jsonParser = new JSONParser();// TODO inject from spring
	}

	private String getRequest(String url) throws IOException {

		final URL urlObject = new URL(url);
		final HttpURLConnection httpUrlConnection = (HttpURLConnection) urlObject.openConnection();

		httpUrlConnection.setRequestMethod("GET");

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

	@Override
	public GPS getCoordinates(String location) throws ParseException, IOException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(Constants.OpenStreetMapURLPrePart);

		String[] split = location.split(" ");
		String queryResult = null;

		if (split.length == 0) {
			return null;
		}

		for (int i = 0; i < split.length; i++) {
			stringBuffer.append(split[i]);
			if (i < (split.length - 1)) {
				stringBuffer.append("+");
			}
		}
		stringBuffer.append(Constants.OpenStreetMapURLPostPart);

		queryResult = getRequest(stringBuffer.toString());

		if (queryResult == null) {
			return null;
		}

		JSONArray jsonArray = (JSONArray) jsonParser.parse(queryResult);
		JSONObject jsonObject = (JSONObject) jsonArray.get(1);
		String latitude = String.valueOf(jsonObject.get(Constants.LATITUDE));
		String longitude = String.valueOf(jsonObject.get(Constants.LONGITUDE));

		return new GPS(Double.valueOf(latitude), Double.valueOf(longitude));
	}
}
