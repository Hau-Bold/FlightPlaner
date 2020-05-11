package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import client.RoutePlanningService;
import overview.OverView;
import widgets.contextMenu.OverViewContextMenu;
import widgets.contextMenu.TargetsContextMenu;

public class TableOverViewMouseListener implements MouseListener {

	private OverViewContextMenu overViewContextMenu;
	private OverView overView;

	public TableOverViewMouseListener(OverView overView, OverViewContextMenu overViewContextMenu) {
		this.overView = overView;
		this.overViewContextMenu = overViewContextMenu;
	}

	@Override
	public void mousePressed(MouseEvent event) {

		TargetsContextMenu targetsContextMenu = RoutePlanningService.getInstance().getTargetContextMenu();
		if (targetsContextMenu != null) {
			targetsContextMenu.dispose();
			targetsContextMenu.dispose();
		}

		if ((event.getSource() == overView.getTable())
				&& (event.getSource() != RoutePlanningService.getInstance().getTableTargets())
				&& (event.getButton() == MouseEvent.BUTTON3)) {
			if (overViewContextMenu != null) {
				overViewContextMenu.dispose();
			}
			overViewContextMenu = new OverViewContextMenu(overView, event);
			overView.setOverViewContextMenu(overViewContextMenu);
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
