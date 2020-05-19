package routePlanningService.Contract;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import routePlanningService.Impl.GPS;

public interface IOpenStreetMapService {

	GPS getCoordinates(String location) throws ParseException, IOException;
}
