package Routenplaner;

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

import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import client.Routeplaner;
import gps_coordinates.GPS;
import gps_coordinates.GpsCoordinate;
import tablemodel.CommonModel;
import toolbar.ConfirmingAddress;

/**
 * for functions
 */
public class Utils {

	final static Double CORRECTION = 0.11029411764705882352941176470588;

	/**
	 * to remove not accepted chars
	 * 
	 * @param receiving
	 *            - the String to examine
	 * @return - the transformed String
	 */
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

	/**
	 * to get permutations
	 * 
	 * @param targets
	 *            - the targets
	 * @return - list of permutations
	 */
	public static List<ArrayList<GpsCoordinate>> getPermutationOf(List<GpsCoordinate> targets) {
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
				List<ArrayList<GpsCoordinate>> perm = getPermutationOf(copy);
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

	/**
	 * 
	 * @param doubleList
	 *            Input eine Liste mit double Eintr�gen
	 * @return Minimaler Wert der doubleList mit Index
	 */
	public static SpecialPoint getMinimumAndIndexOfMinimum(List<Double> doubleList) {

		SpecialPoint pt = new SpecialPoint();
		pt.setIndex(0);
		pt.setDistance(doubleList.get(0));

		for (int i = 1; i < doubleList.size(); i++) {
			if (doubleList.get(i) <= pt.getDistance()) {
				pt.setIndex(i);
				pt.setDistance(doubleList.get(i));
			}
		}
		return pt;
	}

	/**
	 * 
	 * @param receiving
	 *            Input eine Liste mit double Eintr�gen
	 * @return Maximaler Wert der doubleList mit Index
	 */
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

	/**
	 * 
	 * @param location
	 *            the location
	 * @param id
	 *            - the id
	 * @return GpsCoordinate for location
	 * @throws MalformedURLException
	 *             - in case of technical error
	 * @throws JDOMException
	 * @throws JSONException
	 * @throws ParseException
	 */
	public static GpsCoordinate getGpsCoordinateToLocation(String location, int id)
			throws MalformedURLException, JDOMException, JSONException, ParseException {
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

	/**
	 * transforms gps into pixel
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Point gpsToMiller(GpsCoordinate gps) {
		double xMiller = (gps.getLongitude() + 168.2) / CORRECTION;
		final double H = 2136.0;
		double yMiller = Math.toRadians(gps.getLatitude());
		yMiller = 1.25 * Math.log(Math.tan((0.25 * Math.PI) + (0.4 * yMiller)));
		yMiller = (H / 2) - (H / 4.1) * yMiller;
		return new Point((int) xMiller, (int) yMiller);
	}

	public static GpsCoordinate millerToGps(Point point) {

		double longitude = CORRECTION * point.getX() - 168.2;

		final double H = 2136.0;

		double exponent = 0.8 * ((H / 2 - point.getY()) / (H / 4.1));

		double latitude = (Math.exp(exponent) - Math.tan(Math.PI / 4)) * (180.0) / (0.4 * Math.PI);
		return new GpsCoordinate(latitude, longitude);
	}

	/**
	 * to clear the JTextFields
	 * 
	 * @param var
	 *            the JTextFields
	 */
	public static void clearTextFields(JTextField... var) {
		for (JTextField txtField : var) {
			txtField.setText("");
		}

	}

	/**
	 * to determine the visibility of several JLabels
	 * 
	 * @param isVisible
	 *            - a boolean the visibility depends on
	 * @param varargs
	 *            - the JLabels
	 */
	public static void setVisibilityOfLabels(boolean isVisible, JLabel... varargs) {
		for (JLabel label : varargs) {
			label.setVisible(isVisible);
		}

	}

	/**
	 * to determine the visibility of several JTextFields
	 * 
	 * @param isVisible
	 *            - a boolean the visibility depends on
	 * @param varargs
	 *            - the JTextFields
	 */
	public static void setVisibilityOfJTextFields(boolean isVisible, JTextField... varargs) {
		for (JTextField textfield : varargs) {
			textfield.setVisible(isVisible);
		}
	}

	/**
	 * to complete the total length of a given path
	 * 
	 * @param receiving
	 *            - the list of GpsCoordinates
	 * @return the length of the path
	 */
	public static double getTotalDistance(List<GpsCoordinate> receiving) {
		double response = .0;
		for (int i = 0; i < receiving.size() - 1; i++) {
			response += distanceBetween(receiving.get(i), receiving.get(i + 1));
		}
		return response;
	}

	/**
	 * to compute the distance between two GpsCoordinates
	 * 
	 * unit: km
	 * 
	 * @param -
	 *            from
	 * @param -
	 *            to
	 * @return - the distance
	 */
	public static double distanceBetween(GpsCoordinate from, GpsCoordinate to) {
		final double radius = 6371.0;
		double distance = Math.sin(Math.toRadians(from.getLatitude())) * Math.sin(Math.toRadians(to.getLatitude()));
		distance += Math.cos(Math.toRadians(from.getLatitude())) * Math.cos(Math.toRadians(to.getLatitude()))
				* Math.cos(Math.toRadians((to.getLongitude() - from.getLongitude())));
		distance = Math.acos(distance);
		distance *= radius;
		return distance;

	}

	public static boolean isStringValid(String string) {
		return (string != null) && (string != "");
	}

	public static void fillModel(List<GpsCoordinate> receiving, CommonModel model, boolean isComputedModel) {
		Vector<String> datarow;
		if (!isComputedModel) {
			for (GpsCoordinate entry : receiving) {
				datarow = new Vector<String>();
				datarow.add(String.valueOf(entry.getId()));
				datarow.add(entry.getMyStreet());
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
				datarow.add(gps.getMyStreet());
				datarow.add(gps.getCity());
				datarow.add(gps.getCountry());
				model.addDataRow(datarow);

				for (int i = 1; i < receiving.size(); i++) {
					gps = receiving.get(i);
					datarow = new Vector<String>();
					datarow.add(String.valueOf(gps.getId()));
					datarow.add(gps.getMyStreet());
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

	public static void glue(Routeplaner routenplaner, ConfirmingAddress confirmingAdress) {
		confirmingAdress.setLocation(new Point(routenplaner.getX(), routenplaner.getY() + routenplaner.getWidth()));
		routenplaner.isGlued = true;
	}

	public static boolean isEmpty(int[] arrayOfSelectedRows) {
		return arrayOfSelectedRows.length == 0;
	}

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
