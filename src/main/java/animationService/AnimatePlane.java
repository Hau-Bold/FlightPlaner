package animationService;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import routePlanning.Impl.GPS;
import routePlanning.Impl.RoutePlanningHelper;
import widgets.animation.FrameMap;
import widgets.animation.Plane;

public class AnimatePlane implements Runnable {
	private ArrayList<Point> myTargets;
	private Plane myPlane;
	final int newWidth = 50;
	final int newHeight = 50;
	private int xplane, yplane;

	public AnimatePlane(Plane plane, ArrayList<Point> targets) {
		myPlane = plane;
		myTargets = targets;
		this.xplane = (newWidth - plane.getWidth()) / 2;
		this.yplane = (newHeight - plane.getHeight()) / 2;
	}

	@Override
	public void run() {

		for (int counter = 0; counter < myTargets.size() - 1; counter++) {

			long startTime = System.currentTimeMillis();

			// Koordinaten der zu fliegenden Strecke
			Point from = myTargets.get(counter);
			Point to = myTargets.get(counter + 1);
			int xCoorPlane = from.x;
			int yCoorPlane = from.y;
			int xCoorTo = to.x;
			int yCoorTo = to.y;

			while ((xCoorPlane != xCoorTo) || (yCoorPlane != yCoorTo)) {

				GPS gpsTo = RoutePlanningHelper.millerToGps(new Point(xCoorTo, yCoorTo));

				/** to save the old location */
				Point oldLocation = new Point(xCoorPlane, yCoorPlane);
				long timeAtOldLocation = System.currentTimeMillis();
				long elapsedTime = System.currentTimeMillis() - startTime;
				int time = RoutePlanningHelper.convertLongToInt(elapsedTime);

				if (xCoorTo > xCoorPlane) {
					xCoorPlane++;
				}
				if (xCoorTo < xCoorPlane) {
					xCoorPlane--;
				}
				if (yCoorTo > yCoorPlane) {
					yCoorPlane++;
				}
				if (yCoorTo < yCoorPlane) {
					yCoorPlane--;
				}

				Point newLocation = new Point(xCoorPlane, yCoorPlane);
				// Bestimme Winkel zwischen den Koordinaten:
				double argument = (newLocation.getY() - oldLocation.getY()) / (newLocation.getX() - oldLocation.getX());
				// Ziel rechts unten
				if ((xCoorPlane < xCoorTo) && (yCoorPlane < yCoorTo)) {
					argument = Math.toDegrees(Math.atan(argument)) + Math.toDegrees(1 / 2 * Math.PI);
				}
				// Ziel rechts oben
				else if ((oldLocation.getX() < newLocation.getX()) && (oldLocation.getY() > newLocation.getY())) {
					argument = Math.toDegrees(Math.atan(argument));
				}
				// Ziel links unten
				else if ((oldLocation.getX() > newLocation.getX()) && (oldLocation.getY() < newLocation.getY())) {
					argument = Math.toDegrees(Math.atan(argument)) + Math.toDegrees(Math.PI);
				}
				// Ziel links oben
				else if ((oldLocation.getX() > newLocation.getX()) && (oldLocation.getY() > newLocation.getY())) {
					argument = Math.toDegrees(Math.atan(argument)) + Math.toDegrees(Math.PI);
				}

				rotatePlane(argument, myPlane);

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				long timeAtNewLocation = System.currentTimeMillis();

				long elapsedTime2 = timeAtNewLocation - timeAtOldLocation;

				myPlane.setLocation(xCoorPlane, yCoorPlane);
				double distance = RoutePlanningHelper.distanceBetween(RoutePlanningHelper.millerToGps(oldLocation),
						RoutePlanningHelper.millerToGps(newLocation));

				/** time in seconds: */
				elapsedTime2 = (long) ((elapsedTime2) / 1000.0);

				// int f =RoutePlanningHelper.convertLongToInt(Math.round(distance));
			}
			myPlane.setLocation(xCoorPlane, yCoorPlane);
		}
	}

	// TODO move this method to plane
	private void rotatePlane(double argument, Plane plane) {
		double alpha = Math.toRadians(argument);
		BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotate.createGraphics();
		AffineTransform affinetransfrom = new AffineTransform();
		affinetransfrom.setToRotation(alpha, xplane + (myPlane.getWidth() / 2), yplane + (myPlane.getHeight() / 2));
		affinetransfrom.translate(xplane, yplane);
		g2d.setTransform(affinetransfrom);
		g2d.drawImage(myPlane.getImageplane(), 0, 0, FrameMap.getImagepanel());

		myPlane.setIcon(new ImageIcon(rotate));
	}

}
