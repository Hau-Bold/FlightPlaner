package toolbar;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import Routenplaner.Utils;
import client.Routeplaner;

public class ConfirmingAdressLocationListener implements ComponentListener {

	private ConfirmingAddress confirmingAdress;

	public ConfirmingAdressLocationListener(ConfirmingAddress confirmingAdress) {
		this.confirmingAdress = confirmingAdress;
	}

	@Override
	public void componentMoved(ComponentEvent event) {

		Routeplaner routenplaner = Routeplaner.getInstance();

		routenplaner.isGlued = false;
		double absY = Math.abs(
				routenplaner.getLocation().getY() + routenplaner.getHeight() - confirmingAdress.getLocation().getY());
		if (absY < 100) {
			Utils.glue(confirmingAdress);
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}

}
