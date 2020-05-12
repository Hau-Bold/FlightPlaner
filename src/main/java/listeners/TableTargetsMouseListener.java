package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import client.FlightPlaner;
import overview.OverView;
import widgets.contextMenu.OverViewContextMenu;
import widgets.contextMenu.TargetsContextMenu;

public class TableTargetsMouseListener implements MouseListener {

	private TargetsContextMenu targetsContextMenu = null;

	// ctor.
	public TableTargetsMouseListener(TargetsContextMenu targetsContextMenu) {
		this.targetsContextMenu = targetsContextMenu;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		FlightPlaner routeplaner = FlightPlaner.getInstance();

		OverView overView = routeplaner.getOverView();
		OverViewContextMenu overViewContextMenu;
		if (overView != null) {
			overViewContextMenu = overView.getOverViewContextMenu();
			overView.dispose();
			if (overViewContextMenu != null) {
				overViewContextMenu.dispose();
			}
		}

		if (event.getButton() == MouseEvent.BUTTON3) {
			if (targetsContextMenu != null) {
				targetsContextMenu.dispose();
			}
			targetsContextMenu = new TargetsContextMenu(event);
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
