package algorithms;

import gps_coordinates.GPS;
import gps_coordinates.GpsCoordinate;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import Routenplaner.Utils;
import Routenplaner.SpecialPoint;

public class FindFarestThenNearest implements IOptimization {
	private ArrayList<GpsCoordinate> output;
	private List<Double> distances;

	@Override
	public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> receiving) {
		
		List<GpsCoordinate> tmpReceiving=new ArrayList<GpsCoordinate>(receiving);
		GpsCoordinate startGpsTmp = startGps;

		if (startGps != null) {
			if (tmpReceiving.size() == 1) {
				output = new ArrayList<GpsCoordinate>(tmpReceiving);
				output.add(0, startGps);
			} else {
				output = new ArrayList<GpsCoordinate>();
				distances = new ArrayList<Double>();
				output.add(0, startGps);
				while (tmpReceiving.size() > 1) {

					tmpReceiving.forEach(gps -> distances.add(Utils.distanceBetween(startGpsTmp, gps)));

					SpecialPoint point = Utils.getMaximumAndIndexOfMaximum(distances);

					int index = point.getIndex();
					output.add(tmpReceiving.get(index));
					startGps = tmpReceiving.get(index);
					tmpReceiving.remove(index);
					distances.clear();

					tmpReceiving.forEach(gps -> distances.add(Utils.distanceBetween(startGpsTmp, gps)));

					point = Utils.getMinimumAndIndexOfMinimum(distances);

					index = point.getIndex();
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
