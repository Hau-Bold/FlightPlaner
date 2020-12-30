package routePlanning.Impl;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;

import Routenplaner.ImagePanel;
import routePlanning.Constants.Constants;
import routePlanning.overview.Flight;
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

	public static Point gpsToMiller(GPSCoordinate gps) {
		double xMiller = (gps.getLongitude() + 168.2) / Constants.CORRECTION;
		final double H = 2136.0;
		double yMiller = Math.toRadians(gps.getLatitude());
		yMiller = 1.25 * Math.log(Math.tan((0.25 * Math.PI) + (0.4 * yMiller)));
		yMiller = (H / 2) - (H / 4.1) * yMiller;
		return new Point((int) xMiller, (int) yMiller);
	}

	public static GPSCoordinate millerToGps(Point point) {

		double longitude = Constants.CORRECTION * point.getX() - 168.2;

		final double H = 2136.0;

		double exponent = 0.8 * ((H / 2 - point.getY()) / (H / 4.1));

		double latitude = (Math.exp(exponent) - Math.tan(Math.PI / 4)) * (180.0) / (0.4 * Math.PI);
		return new GPSCoordinate(latitude, longitude);
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

	public static double getTotalDistance(List<GPSCoordinate> receiving) {
		double response = .0;
		for (int i = 0; i < receiving.size() - 1; i++) {
			response += distanceBetween(receiving.get(i), receiving.get(i + 1));
		}
		return response;
	}

	public static double distanceBetween(GPSCoordinate from, GPSCoordinate to) {
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

	public static void fillModel(List<GPSCoordinate> receiving, CommonModel model, boolean isComputedModel) {
		Vector<String> datarow;
		if (!isComputedModel) {
			for (GPSCoordinate entry : receiving) {
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
				GPSCoordinate gps = null;
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
