package algorithms;

import gps_coordinates.GpsCoordinate;

import java.util.ArrayList;
import java.util.List;

public interface AbstractOptimization {
	List<GpsCoordinate> computeRoute(GpsCoordinate startGps, List<GpsCoordinate> receiving);
}