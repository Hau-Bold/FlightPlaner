package contextmenu;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AbstractMenuItemMouseListener implements MouseListener {

	private IconMenuItem iconMenuItem;

	public AbstractMenuItemMouseListener(IconMenuItem iconMenuItem) {
		this.iconMenuItem=iconMenuItem;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		iconMenuItem.setBackground(Color.LIGHT_GRAY);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		iconMenuItem.setBackground(Color.WHITE);
		
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
