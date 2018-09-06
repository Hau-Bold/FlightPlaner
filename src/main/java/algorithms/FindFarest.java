package algorithms;

import gps_coordinates.GPS;
import gps_coordinates.GpsCoordinate;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import Routenplaner.Utils;
import Routenplaner.SpecialPoint;

public class FindFarest implements AbstractOptimization {

	private ArrayList<GpsCoordinate> response;
	private ArrayList<Double> distanceList;

	@Override
	public List<GpsCoordinate> computeRoute(GpsCoordinate startGps, List<GpsCoordinate> receiving) {
		
		List<GpsCoordinate> tmpReceiving = new ArrayList<GpsCoordinate>(receiving);
		 GpsCoordinate startGpsTmp=startGps;
		if (startGps != null) {
			if (tmpReceiving.size() == 1) {
				response = new ArrayList<GpsCoordinate>(tmpReceiving);
				response.add(0, startGps);
			} else {
				response = new ArrayList<GpsCoordinate>();
				distanceList = new ArrayList<Double>();
				response.add(0, startGps);
				while (tmpReceiving.size() > 1) {

					tmpReceiving.forEach(gps -> distanceList.add(Utils.distanceBetween(startGpsTmp, gps)));

					SpecialPoint pt = Utils.getMaximumAndIndexOfMaximum(distanceList);
					int index = pt.getIndex();
					response.add(tmpReceiving.get(index));
					startGps = tmpReceiving.get(index);
					tmpReceiving.remove(index);
					distanceList.clear();
				}
				// to add the remaining
				tmpReceiving.forEach(gps -> response.add(gps));
			}
		}
		return response;
	}
}
