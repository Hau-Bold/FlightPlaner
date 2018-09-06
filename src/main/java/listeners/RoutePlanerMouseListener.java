package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import overview.OverView;

public class RoutePlanerMouseListener implements MouseListener {

	private OverView overView;

	public RoutePlanerMouseListener(OverView overView) {
		this.overView = overView;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
            if((event.getSource()!=overView.getTable()) && (overView.getOverViewContextMenu()!=null))
             {
            	 overView.getOverViewContextMenu().dispose();
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
