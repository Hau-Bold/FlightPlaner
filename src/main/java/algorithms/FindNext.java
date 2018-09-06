package algorithms;

import gps_coordinates.GPS;
import gps_coordinates.GpsCoordinate;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import Routenplaner.Utils;
import Routenplaner.SpecialPoint;

public class FindNext implements AbstractOptimization {

	private List<GpsCoordinate> output;

	@Override
	public List<GpsCoordinate> computeRoute(GpsCoordinate startGps, List<GpsCoordinate> receiving) {
		GpsCoordinate gpstmps = startGps;
		List<GpsCoordinate> tmpReceiving = new ArrayList<GpsCoordinate>(receiving);
		if (startGps != null) {
			if (tmpReceiving.size() == 1) {
				output = new ArrayList<GpsCoordinate>(tmpReceiving);
				output.add(0, startGps);
			} else {
				output = new ArrayList<GpsCoordinate>();
				List<Double> distances = new ArrayList<Double>();
				output.add(0, startGps);
				while (tmpReceiving.size() > 1) {
					tmpReceiving.forEach(gps -> distances.add(Utils.distanceBetween(gpstmps, gps)));
					SpecialPoint point = Utils.getMinimumAndIndexOfMinimum(distances);
					int index = point.getIndex();
					output.add(tmpReceiving.get(index));
					startGps = tmpReceiving.get(index);
					tmpReceiving.remove(index);
					distances.clear();
				}
				tmpReceiving.forEach(gps -> output.add(gps));
			}
		}
		return output;
	}

}
