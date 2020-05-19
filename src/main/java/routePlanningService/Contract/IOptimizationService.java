package routePlanningService.Contract;

import java.util.List;

import routePlanningService.Impl.GPS;

public interface IOptimizationService {
	List<GPS> compute(GPS startGps, List<GPS> receiving);
}