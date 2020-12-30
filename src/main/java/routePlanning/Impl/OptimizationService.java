package routePlanning.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import routePlanning.Contract.IOptimizationService;

public class OptimizationService {

	public class FindFarest implements IOptimizationService {

		@Override
		public List<GPSCoordinate> compute(GPSCoordinate start, List<GPSCoordinate> targets) {

			List<GPSCoordinate> computedGPSCoordinates = new ArrayList<GPSCoordinate>();
			List<GPSCoordinate> copyOfTargets = new ArrayList<GPSCoordinate>(targets);
			GPSCoordinate copyOfStart = start;

			if (copyOfTargets.size() == 1) {

				computedGPSCoordinates.addAll(targets);
				computedGPSCoordinates.add(0, start);

			} else {
				List<Double> distanceList = new ArrayList<Double>();
				computedGPSCoordinates.add(0, start);
				while (copyOfTargets.size() > 1) {

					copyOfTargets
							.forEach(gps -> distanceList.add(RoutePlanningHelper.distanceBetween(copyOfStart, gps)));

					DistanceAndIndex distanceAndIndex = getMaximalDistanceAndIndex(distanceList);
					int index = distanceAndIndex.getIndex();
					computedGPSCoordinates.add(copyOfTargets.get(index));
					start = copyOfTargets.get(index);
					copyOfTargets.remove(index);
					distanceList.clear();
				}
				// to add the remaining
				copyOfTargets.forEach(gps -> computedGPSCoordinates.add(gps));
			}

			return computedGPSCoordinates;
		}
	}

	public class FindFarestThenNearest implements IOptimizationService {

		@Override
		public List<GPSCoordinate> compute(GPSCoordinate start, List<GPSCoordinate> targets) {

			ArrayList<GPSCoordinate> computedGPSCoordinates = new ArrayList<GPSCoordinate>();
			List<GPSCoordinate> copyOfTargets = new ArrayList<GPSCoordinate>(targets);
			GPSCoordinate copyOfStart = start;

			if (copyOfTargets.size() == 1) {

				computedGPSCoordinates.addAll(copyOfTargets);
				computedGPSCoordinates.add(0, start);
			} else {
				computedGPSCoordinates.add(0, start);

				List<Double> distanceList = new ArrayList<Double>();

				while (copyOfTargets.size() > 1) {

					copyOfTargets
							.forEach(gps -> distanceList.add(RoutePlanningHelper.distanceBetween(copyOfStart, gps)));

					DistanceAndIndex point = getMaximalDistanceAndIndex(distanceList);

					int index = point.getIndex();
					computedGPSCoordinates.add(copyOfTargets.get(index));
					start = copyOfTargets.get(index);
					copyOfTargets.remove(index);
					distanceList.clear();

					copyOfTargets
							.forEach(gps -> distanceList.add(RoutePlanningHelper.distanceBetween(copyOfStart, gps)));

					point = getMinimalDistanceAndIndex(distanceList);

					index = point.getIndex();
					computedGPSCoordinates.add(copyOfTargets.get(index));
					start = copyOfTargets.get(index);
					copyOfTargets.remove(index);
					distanceList.clear();

				}

				copyOfTargets.forEach(gps -> computedGPSCoordinates.add(gps));
			}

			return computedGPSCoordinates;
		}
	}

	public class FindNearestThenFarest implements IOptimizationService {

		@Override
		public List<GPSCoordinate> compute(GPSCoordinate start, List<GPSCoordinate> targets) {

			ArrayList<GPSCoordinate> computedGPSCoordinates = new ArrayList<GPSCoordinate>();

			List<GPSCoordinate> copyOfTargets = new ArrayList<GPSCoordinate>(targets);
			GPSCoordinate copyOfStart = start;

			if (copyOfTargets.size() == 1) {

				computedGPSCoordinates.addAll(copyOfTargets);
				computedGPSCoordinates.add(0, start);

			} else {

				List<Double> distances = new ArrayList<Double>();
				computedGPSCoordinates.add(0, start);

				while (copyOfTargets.size() > 1) {

					copyOfTargets.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(copyOfStart, gps)));
					DistanceAndIndex point = getMinimalDistanceAndIndex(distances);

					int index = point.getIndex();
					computedGPSCoordinates.add(copyOfTargets.get(index));
					start = copyOfTargets.get(index);
					copyOfTargets.remove(index);
					distances.clear();

					copyOfTargets.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(copyOfStart, gps)));
					point = getMaximalDistanceAndIndex(distances);

					index = point.getIndex();
					computedGPSCoordinates.add(copyOfTargets.get(index));
					start = copyOfTargets.get(index);
					copyOfTargets.remove(index);
					distances.clear();
				}

				copyOfTargets.forEach(gps -> computedGPSCoordinates.add(gps));
			}
			return computedGPSCoordinates;
		}
	}

	public class FindNext implements IOptimizationService {

		@Override
		public List<GPSCoordinate> compute(GPSCoordinate start, List<GPSCoordinate> targets) {
			ArrayList<GPSCoordinate> computedGPSCoordinates = new ArrayList<GPSCoordinate>();

			List<GPSCoordinate> copyOfTargets = new ArrayList<GPSCoordinate>(targets);
			GPSCoordinate copyOfStart = start;

			if (copyOfTargets.size() == 1) {
				computedGPSCoordinates.addAll(copyOfTargets);
				computedGPSCoordinates.add(0, start);
			} else {

				List<Double> distances = new ArrayList<Double>();
				computedGPSCoordinates.add(0, start);
				while (copyOfTargets.size() > 1) {
					copyOfTargets.forEach(gps -> distances.add(RoutePlanningHelper.distanceBetween(copyOfStart, gps)));
					DistanceAndIndex distanceAndIndex = getMinimalDistanceAndIndex(distances);
					int index = distanceAndIndex.getIndex();
					computedGPSCoordinates.add(copyOfTargets.get(index));
					start = copyOfTargets.get(index);
					copyOfTargets.remove(index);
					distances.clear();
				}
				copyOfTargets.forEach(gps -> computedGPSCoordinates.add(gps));
			}
			return computedGPSCoordinates;
		}
	}

	public class FindOptimized implements IOptimizationService {

		@Override
		public List<GPSCoordinate> compute(GPSCoordinate start, List<GPSCoordinate> targets) {

			List<GPSCoordinate> computedGPSCoordinates = new ArrayList<GPSCoordinate>();
			List<GPSCoordinate> copyOfTargets = new ArrayList<GPSCoordinate>(targets);

			if (targets.size() == 1) {

				computedGPSCoordinates.addAll(copyOfTargets);
				computedGPSCoordinates.add(0, start);
			} else {

				List<ArrayList<GPSCoordinate>> input = getPermutations(copyOfTargets);
				List<Double> distances = new ArrayList<Double>();

				input.forEach(list -> {
					list.add(0, start);
					distances.add(RoutePlanningHelper.getTotalDistance(list));
				});

				DistanceAndIndex point = getMinimalDistanceAndIndex(distances);
				computedGPSCoordinates = new ArrayList<GPSCoordinate>(input.get(point.getIndex()));
			}
			return computedGPSCoordinates;
		}
	}

	public class FindRandom implements IOptimizationService {

		@Override
		public List<GPSCoordinate> compute(GPSCoordinate start, List<GPSCoordinate> targets) {

			ArrayList<GPSCoordinate> computedGPSCoordinates = new ArrayList<GPSCoordinate>();
			List<GPSCoordinate> copyOfTargets = new ArrayList<GPSCoordinate>(targets);

			if (targets.size() == 1) {

				computedGPSCoordinates.addAll(copyOfTargets);
				computedGPSCoordinates.add(0, start);

			} else {

				int[] indicesOfGPSCoordinates = new int[targets.size()];
				generate(indicesOfGPSCoordinates, 0, new Random());
				for (int i = 0; i < indicesOfGPSCoordinates.length; i++) {
					computedGPSCoordinates.add(i, targets.get(indicesOfGPSCoordinates[i]));
				}
				computedGPSCoordinates.add(0, start);
			}
			return computedGPSCoordinates;
		}
	}

	private DistanceAndIndex getMaximalDistanceAndIndex(List<Double> distances) {

		DistanceAndIndex distanceAndCorrespondingIndex = new DistanceAndIndex(0, distances.get(0));

		for (int i = 1; i < distances.size(); i++) {
			if (distances.get(i) >= distanceAndCorrespondingIndex.getDistance()) {
				distanceAndCorrespondingIndex.setIndex(i);
				distanceAndCorrespondingIndex.setDistance(distances.get(i));
			}
		}
		return distanceAndCorrespondingIndex;
	}

	private DistanceAndIndex getMinimalDistanceAndIndex(List<Double> distances) {

		DistanceAndIndex distanceAndCorrespondingIndex = new DistanceAndIndex(0, distances.get(0));

		for (int i = 1; i < distances.size(); i++) {
			if (distances.get(i) <= distanceAndCorrespondingIndex.getDistance()) {
				distanceAndCorrespondingIndex.setIndex(i);
				distanceAndCorrespondingIndex.setDistance(distances.get(i));
			}
		}
		return distanceAndCorrespondingIndex;
	}

	private List<ArrayList<GPSCoordinate>> getPermutations(List<GPSCoordinate> targets) {
		List<ArrayList<GPSCoordinate>> permutations = new ArrayList<ArrayList<GPSCoordinate>>();
		if (targets.size() == 2) {
			ArrayList<GPSCoordinate> values1 = new ArrayList<GPSCoordinate>();
			ArrayList<GPSCoordinate> values2 = new ArrayList<GPSCoordinate>();
			values1.add(targets.get(0));
			values1.add(targets.get(1));
			values2.add(targets.get(1));
			values2.add(targets.get(0));
			permutations.add(values1);
			permutations.add(values2);
		} else {
			for (GPSCoordinate item : targets) {
				ArrayList<GPSCoordinate> copy = new ArrayList<GPSCoordinate>(targets);
				copy.remove(item);
				List<ArrayList<GPSCoordinate>> perm = getPermutations(copy);
				for (ArrayList<GPSCoordinate> p : perm) {
					copy = new ArrayList<GPSCoordinate>();
					copy.add(item);
					copy.addAll(p);
					permutations.add(copy);
				}
			}
		}

		return permutations;
	}

	private static void generate(int[] array, int x, Random random) {
		array[x] = random.nextInt(array.length);
		for (int i = 0; i < x; i++) {
			if (array[i] == array[x]) {
				generate(array, x, random);

				return;
			}
		}

		if (x < array.length - 1) {
			// generate next index
			generate(array, x + 1, random);
		}
	}

	private class DistanceAndIndex {
		private int myIndex;
		private double myDistance;

		public DistanceAndIndex(int index, double distance) {
			myIndex = index;
			myDistance = distance;
		}

		public void setIndex(int index) {
			myIndex = index;
		}

		public void setDistance(Double distance) {
			myDistance = distance;

		}

		public Double getDistance() {
			return myDistance;
		}

		public int getIndex() {
			return myIndex;
		}

	}

}
