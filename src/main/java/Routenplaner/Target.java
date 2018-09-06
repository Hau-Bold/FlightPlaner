package Routenplaner;

import java.awt.Point;

import javax.swing.JLabel;

public class Target extends JLabel
{
	public Target(Point point)
	{
		this.setSize(40, 40);
		this.setLocation(point.x - this.getWidth() / 2, point.y - this.getHeight()
				/ 2);
	}
	
	public Point getCentre()
	{
		return new Point(this.getWidth()/2, this.getHeight()/2);
	}

}
