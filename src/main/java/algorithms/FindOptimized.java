package algorithms;

import java.util.ArrayList;
import java.util.List;

import Routenplaner.SpecialPoint;
import Routenplaner.Utils;
import gps_coordinates.GpsCoordinate;

public class FindOptimized implements IOptimization {
	private ArrayList<GpsCoordinate> response;
	GpsCoordinate startGps = null;

	@Override
	public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> targets) {
		if (startGps != null) {
			if (targets.size() == 1) {
				response = new ArrayList<GpsCoordinate>(targets);
				response.add(0, startGps);
			} else {

				List<ArrayList<GpsCoordinate>> input = Utils.getPermutationOf(targets);
				List<Double> distances = new ArrayList<Double>();

				input.forEach(list -> {
					list.add(0, startGps);
					distances.add(Utils.getTotalDistance(list));
				});

				SpecialPoint point = Utils.getMinimumAndIndexOfMinimum(distances);
				response = new ArrayList<GpsCoordinate>(input.get(point.getIndex()));
			}
		}
		return response;
	}
}
