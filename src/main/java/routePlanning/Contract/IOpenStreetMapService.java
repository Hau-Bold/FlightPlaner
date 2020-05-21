package routePlanning.Contract;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import routePlanning.Impl.GPS;

public interface IOpenStreetMapService {

	GPS getCoordinates(String location) throws ParseException, IOException;
}
