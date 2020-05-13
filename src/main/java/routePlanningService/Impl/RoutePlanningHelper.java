package routePlanningService.Impl;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import Routenplaner.ImagePanel;
import Routenplaner.SpecialPoint;
import gps_coordinates.GPS;
import gps_coordinates.GpsCoordinate;
import routePlanningService.Constants.Constants;
import routePlanningService.overview.Flight;
import tablemodel.CommonModel;

public class RoutePlanningHelper {

	public static String replaceUnusableChars(String receiving) {
		if (receiving != null) {
			receiving = receiving.replaceAll("ä", "ae");
			receiving = receiving.replaceAll("ü", "ue");
			receiving = receiving.replaceAll("ö", "oe");
			receiving = receiving.replaceAll("ß", "ss");
			receiving = receiving.replaceAll(" ", "");
		}
		return receiving;
	}

	public static List<ArrayList<GpsCoordinate>> getPermutations(List<GpsCoordinate> targets) {
		List<ArrayList<GpsCoordinate>> permutations = new ArrayList<ArrayList<GpsCoordinate>>();
		if (targets.size() == 2) {
			ArrayList<GpsCoordinate> values1 = new ArrayList<GpsCoordinate>();
			ArrayList<GpsCoordinate> values2 = new ArrayList<GpsCoordinate>();
			values1.add(targets.get(0));
			values1.add(targets.get(1));
			values2.add(targets.get(1));
			values2.add(targets.get(0));
			permutations.add(values1);
			permutations.add(values2);
		} else {
			for (GpsCoordinate item : targets) {
				ArrayList<GpsCoordinate> copy = new ArrayList<GpsCoordinate>(targets);
				copy.remove(item);
				List<ArrayList<GpsCoordinate>> perm = getPermutations(copy);
				for (ArrayList<GpsCoordinate> p : perm) {
					copy = new ArrayList<GpsCoordinate>();
					copy.add(item);
					copy.addAll(p);
					permutations.add(copy);
				}
			}
		}

		return permutations;
	}

	public static SpecialPoint getMinimumAndIndexOfMinimum(List<Double> list) {

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

	public static SpecialPoint getMaximumAndIndexOfMaximum(List<Double> receiving) {
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

	public static GpsCoordinate getGpsCoordinateToLocation(String location, int id)
			throws MalformedURLException, JSONException, ParseException {
		String[] locationSplitted = location.split(",");
		GpsCoordinate gps = new GpsCoordinate(GPS.requestGPS(location).getLatitude(),
				(GPS.requestGPS(location)).getLongitude());

		gps.setId(id);

		// case:complete address
		if (locationSplitted.length == 3) {
			gps.setStreet(locationSplitted[0]);
			gps.setCity(locationSplitted[1]);
			gps.setCountry(locationSplitted[2]);
		}

		// case: only city
		if (locationSplitted.length == 1) {
			gps.setCity(locationSplitted[0]);
		}
		return gps;
	}

	public static void generate(int[] array, int x, Random random) {
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

	public static Point gpsToMiller(GpsCoordinate gps) {
		double xMiller = (gps.getLongitude() + 168.2) / Constants.CORRECTION;
		final double H = 2136.0;
		double yMiller = Math.toRadians(gps.getLatitude());
		yMiller = 1.25 * Math.log(Math.tan((0.25 * Math.PI) + (0.4 * yMiller)));
		yMiller = (H / 2) - (H / 4.1) * yMiller;
		return new Point((int) xMiller, (int) yMiller);
	}

	public static GpsCoordinate millerToGps(Point point) {

		double longitude = Constants.CORRECTION * point.getX() - 168.2;

		final double H = 2136.0;

		double exponent = 0.8 * ((H / 2 - point.getY()) / (H / 4.1));

		double latitude = (Math.exp(exponent) - Math.tan(Math.PI / 4)) * (180.0) / (0.4 * Math.PI);
		return new GpsCoordinate(latitude, longitude);
	}

	// TODO move to other Helper
	public static void clearTextFields(JTextField... var) {
		for (JTextField txtField : var) {
			txtField.setText("");
		}

	}

	// TODO move to other Helper
	public static void setVisibilityOfLabels(boolean isVisible, JLabel... varargs) {
		for (JLabel label : varargs) {
			label.setVisible(isVisible);
		}

	}

	// TODO move to other Helper
	public static void setVisibilityOfJTextFields(boolean isVisible, JTextField... varargs) {
		for (JTextField textfield : varargs) {
			textfield.setVisible(isVisible);
		}
	}

	public static double getTotalDistance(List<GpsCoordinate> receiving) {
		double response = .0;
		for (int i = 0; i < receiving.size() - 1; i++) {
			response += distanceBetween(receiving.get(i), receiving.get(i + 1));
		}
		return response;
	}

	public static double distanceBetween(GpsCoordinate from, GpsCoordinate to) {
		final double radius = 6371.0;
		double distance = Math.sin(Math.toRadians(from.getLatitude())) * Math.sin(Math.toRadians(to.getLatitude()));
		distance += Math.cos(Math.toRadians(from.getLatitude())) * Math.cos(Math.toRadians(to.getLatitude()))
				* Math.cos(Math.toRadians((to.getLongitude() - from.getLongitude())));
		distance = Math.acos(distance);
		distance *= radius;
		return distance;

	}

	public static boolean nullOrEmpty(String string) {
		return (string == null) || (string == "");
	}

	public static void fillModel(List<GpsCoordinate> receiving, CommonModel model, boolean isComputedModel) {
		Vector<String> datarow;
		if (!isComputedModel) {
			for (GpsCoordinate entry : receiving) {
				datarow = new Vector<String>();
				datarow.add(String.valueOf(entry.getId()));
				datarow.add(entry.getStreet());
				datarow.add(entry.getCity());
				datarow.add(entry.getCountry());
				datarow.add(String.valueOf(entry.getLongitude()));
				datarow.add(String.valueOf(entry.getLatitude()));
				model.addDataRow(datarow);
			}
		} else {
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMinimumFractionDigits(2);
			decimalFormat.setMaximumFractionDigits(2);

			model.clear();
			if (receiving.size() > 0) {
				GpsCoordinate gps = null;
				gps = receiving.get(0);
				datarow = new Vector<String>();
				datarow.add(String.valueOf(gps.getId()));
				datarow.add(gps.getStreet());
				datarow.add(gps.getCity());
				datarow.add(gps.getCountry());
				model.addDataRow(datarow);

				for (int i = 1; i < receiving.size(); i++) {
					gps = receiving.get(i);
					datarow = new Vector<String>();
					datarow.add(String.valueOf(gps.getId()));
					datarow.add(gps.getStreet());
					datarow.add(gps.getCity());
					datarow.add(gps.getCountry());
					datarow.add(String
							.valueOf(decimalFormat.format(distanceBetween(receiving.get(i), receiving.get(i - 1)))));
					model.addDataRow(datarow);
				}

				datarow = new Vector<String>();
				datarow.add("");
				datarow.add("");
				datarow.add("");
				datarow.add("total [km]:");
				datarow.add(String.valueOf(decimalFormat.format(getTotalDistance(receiving))));
				model.addDataRow(datarow);
			}
		}

	}

	public static CommonModel generateModelOfFlights(List<Flight> receiving, String[] myNameOfColumns) {

		CommonModel model = new CommonModel(myNameOfColumns);

		Vector<String> datarow;
		int counter = 1;
		for (Flight entry : receiving) {
			datarow = new Vector<String>();
			datarow.add(String.valueOf(counter));
			datarow.add(entry.getFlightnumber());
			datarow.add(entry.getStart());
			datarow.add(String.valueOf(entry.getLatitude()));
			datarow.add(String.valueOf(entry.getLongitude()));
			datarow.add(entry.getTarget());
			model.addDataRow(datarow);
			counter++;
		}

		return model;
	}

	public static boolean isEmpty(int[] arrayOfSelectedRows) {
		return arrayOfSelectedRows.length == 0;
	}

	// TODO move to other Helper
	public static void drawRoute(ArrayList<Point> targetList, ImagePanel imagepanel) {
		Graphics2D g = (Graphics2D) imagepanel.getGraphics();
		Stroke s = new BasicStroke(2.4f, // Width
				BasicStroke.CAP_ROUND, // End cap
				BasicStroke.JOIN_MITER, // Join style
				1.0f, // Miter limit
				new float[] { 4.0f, 8.0f }, // Dash pattern
				10.0f); // Dash phase
		g.setStroke(s);

		for (int i = 0; i < targetList.size() - 1; i++) {
			Point pt1 = targetList.get(i);
			Point pt2 = targetList.get(i + 1);
			g.drawLine(pt1.x, pt1.y, pt2.x, pt2.y);
		}
	}

	public static int convertLongToInt(long value) {
		return Integer.valueOf(String.valueOf(value));
	}
}
