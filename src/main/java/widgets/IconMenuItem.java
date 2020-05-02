package widgets;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import Routenplaner.Utils;

public class IconMenuItem extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon icon = null;

	public IconMenuItem(String pathOfIcon, String text) {

		if (Utils.isStringValid(pathOfIcon)) {
			this.icon = new ImageIcon(getClass().getResource("../" + pathOfIcon));
			this.icon.setImage(icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));

		}

		this.setIcon(icon);
		this.setSize(20, 150);
		this.setText(text);
		this.setOpaque(true);
		this.setBackground(Color.WHITE);

		this.addMouseListener(new MenuItemMouseListener(this));
	}

	public IconMenuItem(String text) {
		this((String) null, text);
	}
}
