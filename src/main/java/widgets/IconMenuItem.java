package widgets;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.inject.Inject;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import client.FlightPlaner;
import routePlanningService.Impl.RoutePlanningHelper;
import spring.DomainLayerSpringContext;
import widgets.control.MenuItemMouseListener;

@SuppressWarnings("serial")
public class IconMenuItem extends JMenuItem {

	@Inject
	private FlightPlaner myRoutePlanningService;

	public IconMenuItem(String pathOfIcon, String text) {

		DomainLayerSpringContext context = DomainLayerSpringContext.GetContext();
		myRoutePlanningService = context.GetFlightPlaner();

		if (!RoutePlanningHelper.nullOrEmpty(pathOfIcon)) {

			String ressource = myRoutePlanningService.getPathToImageFolder() + File.separator + pathOfIcon;

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
