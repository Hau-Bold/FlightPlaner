package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import client.Routeplaner;
import overview.OverView;
import widgets.contextMenu.OverViewContextMenu;
import widgets.contextMenu.TargetsContextMenu;

public class TableTargetsMouseListener implements MouseListener {

	private TargetsContextMenu targetsContextMenu = null;
	private Routeplaner routeplaner;

	/**
	 * Constructor.
	 * 
	 * @param routeplaner
	 *            - the routeplaner
	 * @param tableRouteContextMenu
	 *            - the tableRouteContextMenu
	 */
	public TableTargetsMouseListener(Routeplaner routeplaner, TargetsContextMenu targetsContextMenu) {
		this.routeplaner = routeplaner;
		this.targetsContextMenu = targetsContextMenu;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		OverView overView = routeplaner.getOverView();
		OverViewContextMenu overViewContextMenu;
		if (overView != null) {
			overViewContextMenu = overView.getOverViewContextMenu();
			overView.dispose();
			if (overViewContextMenu != null) {
				overViewContextMenu.dispose();
			}

		}

		if (event.getSource() == routeplaner.getTableTargets() && event.getButton() == MouseEvent.BUTTON3) {
			if (targetsContextMenu != null) {
				targetsContextMenu.dispose();
			}
			targetsContextMenu = new TargetsContextMenu(event);
			routeplaner.getRoutePlanerWindowListener().setContextMenu(targetsContextMenu);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
