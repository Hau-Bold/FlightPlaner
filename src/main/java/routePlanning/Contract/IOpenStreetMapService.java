package routePlanning.Contract;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import routePlanning.Impl.GPSCoordinate;

public interface IOpenStreetMapService {

	GPSCoordinate getCoordinates(String street, String city, String postalCode, String country)
			throws ParseException, IOException;
}
