package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import client.FlightPlaner;
import widgets.contextMenu.OverViewContextMenu;
import widgets.contextMenu.TargetsContextMenu;
import widgets.flightsOverview.FlightsOverview;

public class TableOverViewMouseListener implements MouseListener {

	private OverViewContextMenu overViewContextMenu;
	private FlightsOverview overView;
	private FlightPlaner myFlightPlaner;

	public TableOverViewMouseListener(FlightsOverview overView, OverViewContextMenu overViewContextMenu) {
		myFlightPlaner = FlightPlaner.getInstance();

		this.overView = overView;
		this.overViewContextMenu = overViewContextMenu;
	}

	@Override
	public void mousePressed(MouseEvent event) {

		TargetsContextMenu targetsContextMenu = myFlightPlaner.getTargetContextMenu();
		if (targetsContextMenu != null) {
			targetsContextMenu.dispose();
			targetsContextMenu.dispose();
		}

		if ((event.getSource() == overView.getTable()) && (event.getSource() != myFlightPlaner.getTableTargets())
				&& (event.getButton() == MouseEvent.BUTTON3)) {
			if (overViewContextMenu != null) {
				overViewContextMenu.dispose();
			}
			overViewContextMenu = new OverViewContextMenu(event);
			overView.setMyOverViewContextMenu(overViewContextMenu);
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
