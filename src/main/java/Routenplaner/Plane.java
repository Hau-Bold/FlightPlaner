package Routenplaner;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.RoutePlanningService;

@SuppressWarnings("serial")
public class Plane extends JLabel {
	private BufferedImage bufferedimage = null;
	private Image imageplane;
	final int height = 50;// Todo Move
	final int width = 50;

	public Image getImageplane() {
		return imageplane;
	}

	public Plane(Point point) {
		this.setSize(width, height);
		try {
			bufferedimage = ImageIO
					.read(new File(RoutePlanningService.getInstance().pathToImageFolder + File.separator + "flieger.png"));// Constants
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
}
