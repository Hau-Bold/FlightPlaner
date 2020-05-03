package widgets;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import Routenplaner.Utils;
import widgets.control.MenuItemMouseListener;

@SuppressWarnings("serial")
public class IconMenuItem extends JMenuItem {

	private ImageIcon myIcon = null;

	public IconMenuItem(String pathOfIcon, String text) {

		if (!Utils.nullOrEmpty(pathOfIcon)) {
			myIcon = new ImageIcon(getClass().getResource("../" + pathOfIcon));
			myIcon.setImage(myIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		}

		setIcon(myIcon);
		setSize(20, 150);
		setText(text);
		setOpaque(true);
		setBackground(Color.WHITE);

		addMouseListener(new MenuItemMouseListener(this));
	}

	public IconMenuItem(String text) {
		this((String) null, text);
	}
}
