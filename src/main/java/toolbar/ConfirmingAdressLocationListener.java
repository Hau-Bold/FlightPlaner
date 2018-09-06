package toolbar;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import Routenplaner.Utils;
import client.Routeplaner;

public class ConfirmingAdressLocationListener implements ComponentListener {

	private ConfirmingAddress confirmingAdress;
	private Routeplaner routenplaner;

	public ConfirmingAdressLocationListener(ConfirmingAddress confirmingAdress, Routeplaner routenplaner) {
		this.confirmingAdress=confirmingAdress;
		this.routenplaner=routenplaner;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent event) {
		routenplaner.isGlued=false;
		double  absY=Math.abs(routenplaner.getLocation().getY() + routenplaner.getHeight() -confirmingAdress.getLocation().getY());
		if(absY < 100)
		{
			Utils.glue(routenplaner,confirmingAdress);
		}

	}

	

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

}
