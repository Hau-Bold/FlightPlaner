package toolbar;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import Routenplaner.Utils;
import client.Routeplaner;

public class RoutePlanerLocationListener implements ComponentListener {

	private ConfirmingAddress confirmingAdress;
	private Routeplaner routenplaner;

	public RoutePlanerLocationListener(ConfirmingAddress confirmingAdress, Routeplaner routenplaner) {
		this.confirmingAdress=confirmingAdress;
		this.routenplaner=routenplaner;
	}

	@Override
	public void componentHidden(ComponentEvent event) {
	}

	@Override
	public void componentMoved(ComponentEvent event) {
           double q = Math.abs(routenplaner.getLocation().getY() + routenplaner.getHeight() -confirmingAdress.getLocation().getY());
           if(q<100 && !routenplaner.isGlued)
           {
        	   Utils.glue(routenplaner,confirmingAdress);
           }
           else if(routenplaner.isGlued)
           {
        	   confirmingAdress.setLocation(new Point(routenplaner.getX(),routenplaner.getY()+routenplaner.getWidth()));
        	   
           }
   		}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
