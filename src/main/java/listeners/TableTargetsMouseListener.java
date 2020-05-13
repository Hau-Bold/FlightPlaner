package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import spring.DomainLayerSpringContext;
import widgets.contextMenu.OverViewContextMenu;
import widgets.contextMenu.TargetsContextMenu;
import widgets.flightsOverview.FlightsOverview;

public class TableTargetsMouseListener implements MouseListener {

	private TargetsContextMenu myTargetsContextMenu = null;

	// ctor.
	public TableTargetsMouseListener(TargetsContextMenu targetsContextMenu) {
		myTargetsContextMenu = targetsContextMenu;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();

		FlightsOverview flightsOverview = springContext.GetFlightsOverview();
		OverViewContextMenu overViewContextMenu;
		if (flightsOverview != null) {
			overViewContextMenu = flightsOverview.getMyOverViewContextMenu();
			flightsOverview.dispose();
			if (overViewContextMenu != null) {
				overViewContextMenu.dispose();
			}
		}

		if (event.getButton() == MouseEvent.BUTTON3) {
			if (myTargetsContextMenu != null) {
				myTargetsContextMenu.dispose();
			}
			myTargetsContextMenu = new TargetsContextMenu(event);
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
