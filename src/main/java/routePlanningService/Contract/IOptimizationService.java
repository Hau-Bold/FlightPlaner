package routePlanningService.Contract;

import java.util.List;

import gps_coordinates.GpsCoordinate;

public interface IOptimizationService {
	List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> receiving);
}