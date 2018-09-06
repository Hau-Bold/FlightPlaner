package Routenplaner;

/**
 * ein Label, auf dem eine Graphik des
 *  Fliegers gespeichert wird 
 */

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Plane extends JLabel
{
	private BufferedImage bufferedimage = null;
	private Image imageplane;
	final int height = 50;
	final int width = 50;
	final String PATH = "../Images/flieger.png";

	public Image getImageplane()
	{
		return imageplane;
	}
	public Plane(Point point)
	{
		this.setSize(width, height);
		try
		{
			bufferedimage = ImageIO.read(getClass().getResource(PATH));
			imageplane = (Image) bufferedimage;
			imageplane = imageplane.getScaledInstance(height, width, 0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(imageplane));
	}
	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x - width / 2, y - height / 2);
	}
}
