package algorithms;

import gps_coordinates.GpsCoordinate;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Routenplaner.Utils;

public class FindRandom implements AbstractOptimization {

	private ArrayList<GpsCoordinate> output;

	@Override
	public List<GpsCoordinate> computeRoute(GpsCoordinate startGps,List<GpsCoordinate> targets) {
		if (startGps != null) {
			if (targets.size() == 1) {
				output = new ArrayList<GpsCoordinate>(targets);
				output.add(0, startGps);
			} else {
				output = new ArrayList<GpsCoordinate>();
				int[] anzahlGpsKoordinate = new int[targets.size()];
				Utils.generate(anzahlGpsKoordinate, 0, new Random());
				for (int i = 0; i < anzahlGpsKoordinate.length; i++) {
					output.add(i, targets.get(anzahlGpsKoordinate[i]));
				}
				output.add(0, startGps);
			} 
		}
		return output;
	}

}
