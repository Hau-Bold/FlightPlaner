package routePlanningService.Contract;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import gps_coordinates.GpsCoordinate;

public interface IOpenStreetMapService {

	GpsCoordinate getCoordinates(String location) throws ParseException, IOException;
}
