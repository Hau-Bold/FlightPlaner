package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.servlet.server.Encoding;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class GPSCoordinate {
    
    private int myId;
    private double myLatitude;
    private double myLongitude;
    private String myStreet;
    private String myCity;
    private String myCountry;
    private String myPostalCode;

    private static final String OSMUrl = "https://nominatim.openstreetmap.org/search?street=%s&city=%s&postalcode=%s&country=%s&format=json";
    public GPSCoordinate(double latitude, double longitude) {
        myLatitude = latitude;
        myLongitude = longitude;
    }

    public GPSCoordinate(String street, String city, String country) {
        myStreet = street;
        myCity = city;
        myCountry = country;
    }

    public GPSCoordinate(String street, String city, String postalCode, String country, double longitude, double latitude) {
        this(latitude, longitude);
        myStreet = street;
        myCity = city;
        myPostalCode = postalCode;
        myCountry = country;
    }

    public int getId() {
        return myId;
    }

    public void setId(int id) {
        myId = id;
    }

    public String getStreet() {
        return myStreet;
    }

    public void setStreet(String street) {
        myStreet = street;
    }

    public String getCity() {
        return myCity;
    }

    public void setCity(String city) {
        myCity = city;
    }

    public String getCountry() {
        return myCountry;
    }

    public void setCountry(String country) {
        myCountry = country;
    }

    public double getLatitude() {
        return myLatitude;
    }

    public double getLongitude() {
        return myLongitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GPSCoordinate) {
            // TODO check xml from openstreetmap and use Double.compare(d1, d2)
            if (((GPSCoordinate) obj).getLongitude() == myLongitude
                    && ((GPSCoordinate) obj).getLatitude() == myLatitude) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "GPSCoordinate{" +
                "myStreet='" + myStreet + '\'' +
                ", myCity='" + myCity + '\'' +
                ", myCountry='" + myCountry + '\'' +
                ", myLatitude='" + myLatitude + '\'' +
                ", myLongtitude='" + myLongitude + '\'' +
                '}';
    }

    public static GPSCoordinate getFromOSM(String street, String city, String postalCode, String country) throws IOException, ParseException {
        double lat = 0.0;
        double lon = 0.0;

        URL url = new URL(String.format(OSMUrl, street, city, postalCode, country));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("accept", "application/json");

        InputStream responseStream = connection.getInputStream();

        // convert to String
        // StringWriter writer = new StringWriter();
        // IOUtils.copy(responseStream, writer, StandardCharsets.UTF_8);
        // String osmJSON = writer.toString();
        // System.out.println(osmJSON);

        // convert to JSON
        JSONParser JSONparser = new JSONParser();
        JSONArray jarr = (JSONArray) JSONparser.parse(
                new InputStreamReader(responseStream, StandardCharsets.UTF_8)
        );

        JSONObject responseJSON;

        if(jarr.size() > 0) {
            responseJSON = (JSONObject) jarr.get(0);
        } else {
            throw new IOException("Search did not return any results from OpenStreetMap");
        };

        if(responseJSON.containsKey("lat")) {
            lat = Double.parseDouble(responseJSON.get("lat").toString());
        }

        if(responseJSON.containsKey("lon")) {
            lon = Double.parseDouble(responseJSON.get("lon").toString());
        }

        return new GPSCoordinate(street, city, postalCode, country, lat, lon);

    }
}
