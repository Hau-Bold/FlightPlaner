package routePlanningService.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import Routenplaner.SpecialPoint;
import gps_coordinates.GpsCoordinate;
import routePlanningService.Contract.IOptimizationService;

@Component
@Service("OptimizationService")
public class OptimizationService {

	private ArrayList<GpsCoordinate> response;
	private ArrayList<Double> distanceList;

	public class FindFarest implements IOptimizationService {

		@Override
		public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> receiving) {

			List<GpsCoordinate> tmpReceiving = new ArrayList<GpsCoordinate>(receiving);
			GpsCoordinate startGpsTmp = startGps;
			if (startGps != null) {
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GpsCoordinate>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GpsCoordinate>();
					distanceList = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {

						tmpReceiving.forEach(
								gps -> distanceList.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));

						SpecialPoint pt = RoutePlanningHelper.getMaximumAndIndexOfMaximum(distanceList);
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

	public class FindFarestThenNearest implements IOptimizationService {

		@Override
		public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> receiving) {

			List<GpsCoordinate> tmpReceiving = new ArrayList<GpsCoordinate>(receiving);
			GpsCoordinate startGpsTmp = startGps;

			if (startGps != null) {
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GpsCoordinate>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GpsCoordinate>();
					distanceList = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {

						tmpReceiving.forEach(
								gps -> distanceList.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));

						SpecialPoint point = RoutePlanningHelper.getMaximumAndIndexOfMaximum(distanceList);

						int index = point.getIndex();
						response.add(tmpReceiving.get(index));
						startGps = tmpReceiving.get(index);
						tmpReceiving.remove(index);
						distanceList.clear();

						tmpReceiving.forEach(
								gps -> distanceList.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));

						point = RoutePlanningHelper.getMinimumAndIndexOfMinimum(distanceList);

						index = point.getIndex();
						response.add(tmpReceiving.get(index));
						startGps = tmpReceiving.get(index);
						tmpReceiving.remove(index);
						distanceList.clear();

					}

					tmpReceiving.forEach(gps -> response.add(gps));
				}
			}
			return response;
		}

	}

	public class FindNearestThenFarest implements IOptimizationService {

		@Override
		public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> receiving) {

			List<GpsCoordinate> tmpReceiving = new ArrayList<GpsCoordinate>(receiving);
			GpsCoordinate startGpsTmp = startGps;

			if (startGps != null) {
				response = new ArrayList<GpsCoordinate>();
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GpsCoordinate>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GpsCoordinate>();
					List<Double> distances = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {
						tmpReceiving
								.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));
						SpecialPoint point = RoutePlanningHelper.getMinimumAndIndexOfMinimum(distances);

						int index = point.getIndex();
						response.add(tmpReceiving.get(index));
						startGps = tmpReceiving.get(index);
						tmpReceiving.remove(index);
						distances.clear();

						tmpReceiving
								.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));
						point = RoutePlanningHelper.getMaximumAndIndexOfMaximum(distances);

						index = point.getIndex();
						response.add(tmpReceiving.get(index));
						startGps = tmpReceiving.get(index);
						tmpReceiving.remove(index);
						distances.clear();

					}
					tmpReceiving.forEach(gps -> response.add(gps));
				}
			}
			return response;
		}

	}

	public class FindNext implements IOptimizationService {

		@Override
		public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> receiving) {
			GpsCoordinate gpstmps = startGps;
			List<GpsCoordinate> tmpReceiving = new ArrayList<GpsCoordinate>(receiving);
			if (startGps != null) {
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GpsCoordinate>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GpsCoordinate>();
					List<Double> distances = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {
						tmpReceiving.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(gpstmps, gps)));
						SpecialPoint point = RoutePlanningHelper.getMinimumAndIndexOfMinimum(distances);
						int index = point.getIndex();
						response.add(tmpReceiving.get(index));
						startGps = tmpReceiving.get(index);
						tmpReceiving.remove(index);
						distances.clear();
					}
					tmpReceiving.forEach(gps -> response.add(gps));
				}
			}
			return response;
		}
	}

	public class FindOptimized implements IOptimizationService {

		@Override
		public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> targets) {
			if (startGps != null) {
				if (targets.size() == 1) {
					response = new ArrayList<GpsCoordinate>(targets);
					response.add(0, startGps);
				} else {

					List<ArrayList<GpsCoordinate>> input = RoutePlanningHelper.getPermutations(targets);
					List<Double> distances = new ArrayList<Double>();

					input.forEach(list -> {
						list.add(0, startGps);
						distances.add(RoutePlanningHelper.getTotalDistance(list));
					});

					SpecialPoint point = RoutePlanningHelper.getMinimumAndIndexOfMinimum(distances);
					response = new ArrayList<GpsCoordinate>(input.get(point.getIndex()));
				}
			}
			return response;
		}
	}

	public class FindRandom implements IOptimizationService {

		@Override
		public List<GpsCoordinate> compute(GpsCoordinate startGps, List<GpsCoordinate> targets) {
			if (startGps != null) {
				if (targets.size() == 1) {
					response = new ArrayList<GpsCoordinate>(targets);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GpsCoordinate>();
					int[] anzahlGpsKoordinate = new int[targets.size()];
					RoutePlanningHelper.generate(anzahlGpsKoordinate, 0, new Random());
					for (int i = 0; i < anzahlGpsKoordinate.length; i++) {
						response.add(i, targets.get(anzahlGpsKoordinate[i]));
					}
					response.add(0, startGps);
				}
			}
			return response;
		}
	}
}
