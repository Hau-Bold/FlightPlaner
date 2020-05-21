package routePlanning.Contract;

import java.util.List;

import routePlanning.Impl.GPS;

public interface IOptimizationService {
	List<GPS> compute(GPS startGps, List<GPS> receiving);
}