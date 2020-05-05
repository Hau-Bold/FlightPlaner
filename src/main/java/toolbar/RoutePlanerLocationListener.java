package toolbar;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import Routenplaner.Utils;
import client.Routeplaner;

public class RoutePlanerLocationListener implements ComponentListener {

	private ConfirmingAddress confirmingAdress;

	public RoutePlanerLocationListener(ConfirmingAddress confirmingAdress) {
		this.confirmingAdress = confirmingAdress;
	}

	@Override
	public void componentHidden(ComponentEvent event) {
	}

	@Override
	public void componentMoved(ComponentEvent event) {

		Routeplaner routeplaner = Routeplaner.getInstance();

		double q = Math.abs(
				routeplaner.getLocation().getY() + routeplaner.getHeight() - confirmingAdress.getLocation().getY());
		if (q < 100 && !routeplaner.isGlued) {
			Utils.glue(confirmingAdress);
		} else if (routeplaner.isGlued) {
			confirmingAdress.setLocation(new Point(routeplaner.getX(), routeplaner.getY() + routeplaner.getWidth()));
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}
}
