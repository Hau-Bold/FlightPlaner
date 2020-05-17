package widgets.animation;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.FlightPlaner;

@SuppressWarnings("serial")
public class Plane extends JLabel {
	private BufferedImage myBufferedimage = null;
	private Image myImageplane;
	final int height = 50;// Todo Move
	final int width = 50;

	public Plane(Point point) {
		setSize(width, height);

		FlightPlaner flightPlaner = FlightPlaner.getInstance();

		try {
			myBufferedimage = ImageIO
					.read(new File(flightPlaner.getPathToImageFolder() + File.separator + "flieger.png"));// Constants
			// for
			// Names???
			myImageplane = myBufferedimage;
			myImageplane = myImageplane.getScaledInstance(height, width, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIcon(new ImageIcon(myImageplane));
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x - width / 2, y - height / 2);
	}

	public Image getImageplane() {
		return myImageplane;
	}
}
