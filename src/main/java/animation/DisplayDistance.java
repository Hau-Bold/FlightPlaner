package animation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Routenplaner.Utils;
import gps_coordinates.GpsCoordinate;

public class DisplayDistance extends JFrame {

	private final int X = 10;
	private final int Y = 10;
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private JLabel statusBar;

	private JPanel panelDistances;

	public DisplayDistance() {
		initComponent();
	}

	private void initComponent() {
		this.setTitle("Distances");
		this.setLocation(X, Y);
		this.setSize(500, 500);

		this.setLayout(new BorderLayout());

		panelDistances = new JPanel();
		panelDistances.setLayout(new BorderLayout());

		statusBar = new JLabel();
		panelDistances.add(statusBar, BorderLayout.SOUTH);

		this.getContentPane().add(panelDistances);
	}

	public void showDisplayDistance() {
		this.setVisible(true);
	}

	public void drawPoint(int l, int distance) {
		Graphics2D g = (Graphics2D) panelDistances.getGraphics();
		// g.drawRect(l, distance, 5, 5);

		// Stroke s = new BasicStroke(2.4f, // Width
		// BasicStroke.CAP_ROUND, // End cap
		// BasicStroke.JOIN_MITER, // Join style
		// 1.0f, // Miter limit
		// new float[] { 4.0f, 8.0f }, // Dash pattern
		// 10.0f); // Dash phase
		// g.setStroke(s);

		// g.drawLine(0, 0, l/100, distance/100);

		g.setColor(Color.RED);
		g.drawRect(l / 100, distance / 100, 5, 5);

		// panelDistances.repaint();
	}

	public void drawDistance(double distance) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		this.setTitle(String.valueOf(numberFormat.format(distance)) + " km");
		// Graphics2D g = (Graphics2D)statusBar.getGraphics();
		// g.drawString(String.valueOf(distance), 300, 400);
		// statusBar.repaint();
	}

	public void rewrite() {
		panelDistances.repaint();

	}

	public void drawDistanceAndSpeed(double distance, Point oldLocation, Point newLocation, long elapsedTime) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);

		GpsCoordinate from = Utils.millerToGps(oldLocation);
		GpsCoordinate to = Utils.millerToGps(newLocation);

		double elapsedDistance = Utils.distanceBetween(from, to);

		double speed = elapsedDistance / elapsedTime;

		this.setTitle(String.valueOf(numberFormat.format(distance)) + " km" + " : " + String.valueOf(speed) + "km / s");

	}

}
