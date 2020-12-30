package routePlanning.Contract;

import java.util.List;

import routePlanning.Impl.GPSCoordinate;

public interface IOptimizationService {
	List<GPSCoordinate> compute(GPSCoordinate start, List<GPSCoordinate> targets);
}