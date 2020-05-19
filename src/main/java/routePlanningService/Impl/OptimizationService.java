package routePlanningService.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Routenplaner.SpecialPoint;
import routePlanningService.Contract.IOptimizationService;

public class OptimizationService {

	private ArrayList<GPS> response;
	private ArrayList<Double> distanceList;

	public class FindFarest implements IOptimizationService {

		@Override
		public List<GPS> compute(GPS startGps, List<GPS> receiving) {

			List<GPS> tmpReceiving = new ArrayList<GPS>(receiving);
			GPS startGpsTmp = startGps;
			if (startGps != null) {
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GPS>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GPS>();
					distanceList = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {

						tmpReceiving.forEach(
								gps -> distanceList.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));

						SpecialPoint pt = getMaximumAndIndexOfMaximum(distanceList);
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
		public List<GPS> compute(GPS startGps, List<GPS> receiving) {

			List<GPS> tmpReceiving = new ArrayList<GPS>(receiving);
			GPS startGpsTmp = startGps;

			if (startGps != null) {
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GPS>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GPS>();
					distanceList = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {

						tmpReceiving.forEach(
								gps -> distanceList.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));

						SpecialPoint point = getMaximumAndIndexOfMaximum(distanceList);

						int index = point.getIndex();
						response.add(tmpReceiving.get(index));
						startGps = tmpReceiving.get(index);
						tmpReceiving.remove(index);
						distanceList.clear();

						tmpReceiving.forEach(
								gps -> distanceList.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));

						point = getMinimumAndIndexOfMinimum(distanceList);

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
		public List<GPS> compute(GPS startGps, List<GPS> receiving) {

			List<GPS> tmpReceiving = new ArrayList<GPS>(receiving);
			GPS startGpsTmp = startGps;

			if (startGps != null) {
				response = new ArrayList<GPS>();
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GPS>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GPS>();
					List<Double> distances = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {
						tmpReceiving
								.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));
						SpecialPoint point = getMinimumAndIndexOfMinimum(distances);

						int index = point.getIndex();
						response.add(tmpReceiving.get(index));
						startGps = tmpReceiving.get(index);
						tmpReceiving.remove(index);
						distances.clear();

						tmpReceiving
								.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(startGpsTmp, gps)));
						point = getMaximumAndIndexOfMaximum(distances);

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
		public List<GPS> compute(GPS startGps, List<GPS> receiving) {
			GPS gpstmps = startGps;
			List<GPS> tmpReceiving = new ArrayList<GPS>(receiving);
			if (startGps != null) {
				if (tmpReceiving.size() == 1) {
					response = new ArrayList<GPS>(tmpReceiving);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GPS>();
					List<Double> distances = new ArrayList<Double>();
					response.add(0, startGps);
					while (tmpReceiving.size() > 1) {
						tmpReceiving.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(gpstmps, gps)));
						SpecialPoint point = getMinimumAndIndexOfMinimum(distances);
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
		public List<GPS> compute(GPS startGps, List<GPS> targets) {
			if (startGps != null) {
				if (targets.size() == 1) {
					response = new ArrayList<GPS>(targets);
					response.add(0, startGps);
				} else {

					List<ArrayList<GPS>> input = getPermutations(targets);
					List<Double> distances = new ArrayList<Double>();

					input.forEach(list -> {
						list.add(0, startGps);
						distances.add(RoutePlanningHelper.getTotalDistance(list));
					});

					SpecialPoint point = getMinimumAndIndexOfMinimum(distances);
					response = new ArrayList<GPS>(input.get(point.getIndex()));
				}
			}
			return response;
		}
	}

	public class FindRandom implements IOptimizationService {

		@Override
		public List<GPS> compute(GPS startGps, List<GPS> targets) {
			if (startGps != null) {
				if (targets.size() == 1) {
					response = new ArrayList<GPS>(targets);
					response.add(0, startGps);
				} else {
					response = new ArrayList<GPS>();
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

	private SpecialPoint getMaximumAndIndexOfMaximum(List<Double> receiving) {
		SpecialPoint response = new SpecialPoint();
		response.setIndex(0);
		response.setDistance(receiving.get(0));

		for (int i = 1; i < receiving.size(); i++) {
			if (receiving.get(i) >= response.getDistance()) {
				response.setIndex(i);
				response.setDistance(receiving.get(i));
			}
		}
		return response;
	}

	private SpecialPoint getMinimumAndIndexOfMinimum(List<Double> list) {

		SpecialPoint pt = new SpecialPoint();
		pt.setIndex(0);
		pt.setDistance(list.get(0));

		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) <= pt.getDistance()) {
				pt.setIndex(i);
				pt.setDistance(list.get(i));
			}
		}
		return pt;
	}

	private List<ArrayList<GPS>> getPermutations(List<GPS> targets) {
		List<ArrayList<GPS>> permutations = new ArrayList<ArrayList<GPS>>();
		if (targets.size() == 2) {
			ArrayList<GPS> values1 = new ArrayList<GPS>();
			ArrayList<GPS> values2 = new ArrayList<GPS>();
			values1.add(targets.get(0));
			values1.add(targets.get(1));
			values2.add(targets.get(1));
			values2.add(targets.get(0));
			permutations.add(values1);
			permutations.add(values2);
		} else {
			for (GPS item : targets) {
				ArrayList<GPS> copy = new ArrayList<GPS>(targets);
				copy.remove(item);
				List<ArrayList<GPS>> perm = getPermutations(copy);
				for (ArrayList<GPS> p : perm) {
					copy = new ArrayList<GPS>();
					copy.add(item);
					copy.addAll(p);
					permutations.add(copy);
				}
			}
		}

		return permutations;
	}

}
