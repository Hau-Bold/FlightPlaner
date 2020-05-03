package widgets.control;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import widgets.IconMenuItem;

public class AbstractMenuItemMouseListener implements MouseListener {

	private IconMenuItem myIconMenuItem;

	public AbstractMenuItemMouseListener(IconMenuItem iconMenuItem) {
		myIconMenuItem = iconMenuItem;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		myIconMenuItem.setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		myIconMenuItem.setBackground(Color.WHITE);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
