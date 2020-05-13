package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import spring.DomainLayerSpringContext;
import widgets.flightsOverview.FlightsOverview;

public class RoutePlanerMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent event) {

		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();
		FlightsOverview flightsOverview = springContext.GetFlightsOverview();

		if ((event.getSource() != flightsOverview.getTable()) && (flightsOverview.getMyOverViewContextMenu() != null)) {
			flightsOverview.getMyOverViewContextMenu().dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
