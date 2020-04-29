package algorithms;

import gps_coordinates.GpsCoordinate;

import java.util.ArrayList;
import java.util.List;

public interface IOptimization {
	List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> receiving);
}