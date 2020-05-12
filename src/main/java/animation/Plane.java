package animation;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.FlightPlaner;

@SuppressWarnings("serial")
public class Plane extends JLabel {
	private BufferedImage bufferedimage = null;
	private Image imageplane;
	final int height = 50;// Todo Move
	final int width = 50;

	@Inject
	private FlightPlaner routePlanningService;

	public Plane(Point point) {
		this.setSize(width, height);
		try {
			bufferedimage = ImageIO
					.read(new File(routePlanningService.getPathToImageFolder() + File.separator + "flieger.png"));// Constants
			// for
			// Names???
			imageplane = bufferedimage;
			imageplane = imageplane.getScaledInstance(height, width, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(imageplane));
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x - width / 2, y - height / 2);
	}

	public Image getImageplane() {
		return imageplane;
	}
}
