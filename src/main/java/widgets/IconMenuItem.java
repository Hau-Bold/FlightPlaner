package widgets;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import Routenplaner.Utils;
import client.RoutePlanningService;
import widgets.control.MenuItemMouseListener;

@SuppressWarnings("serial")
public class IconMenuItem extends JMenuItem {

	public IconMenuItem(String pathOfIcon, String text) {

		if (!Utils.nullOrEmpty(pathOfIcon)) {

			String ressource = RoutePlanningService.getInstance().pathToImageFolder + File.separator + pathOfIcon;

			ImageIcon icon = new ImageIcon(ressource);
			icon.setImage(icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));

			setIcon(icon);
			setSize(20, 150);
			setText(text);
			setOpaque(true);
			setBackground(Color.WHITE);

			addMouseListener(new MenuItemMouseListener(this));
		}
	}

	public IconMenuItem(String text) {
		// this((String) null, text);
		this("Confirm.png", text);
	}
}
