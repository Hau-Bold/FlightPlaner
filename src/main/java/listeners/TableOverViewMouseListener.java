package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import client.Routeplaner;
import contextmenu.OverViewContextMenu;
import contextmenu.TargetsContextMenu;
import overview.OverView;

public class TableOverViewMouseListener implements MouseListener {

	private OverViewContextMenu overViewContextMenu;
	private OverView overView;
	private Routeplaner routeplaner;

	public TableOverViewMouseListener(OverView overView, OverViewContextMenu overViewContextMenu, Routeplaner routeplaner) {
		this.overView = overView;
		this.overViewContextMenu = overViewContextMenu;
		this.routeplaner = routeplaner;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
//		if (overViewContextMenu != null) {
//			overViewContextMenu.dispose();
//		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent event) {
		
		TargetsContextMenu targetsContextMenu = routeplaner.getTargetContextMenu();
		if(targetsContextMenu != null)
		{
			targetsContextMenu.dispose();
			targetsContextMenu.dispose();
		}

		if ((event.getSource() == overView.getTable()) && (event.getSource() != routeplaner.getTableTargets()) && (event.getButton() == MouseEvent.BUTTON3)) {
			if (overViewContextMenu != null) {
				overViewContextMenu.dispose();
			}
			overViewContextMenu = new OverViewContextMenu(overView, event, routeplaner);
			overView.setOverViewContextMenu(overViewContextMenu);
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
