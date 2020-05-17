package widgets;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import client.FlightPlaner;
import routePlanningService.Impl.RoutePlanningHelper;
import widgets.control.MenuItemMouseListener;

@SuppressWarnings("serial")
public class IconMenuItem extends JMenuItem {

	private FlightPlaner myFlightPlaner;

	public IconMenuItem(String pathOfIcon, String text) {

		myFlightPlaner = FlightPlaner.getInstance();

		if (!RoutePlanningHelper.nullOrEmpty(pathOfIcon)) {

			String ressource = myFlightPlaner.getPathToImageFolder() + File.separator + pathOfIcon;

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
