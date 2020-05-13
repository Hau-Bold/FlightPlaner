package widgets.animation;

import java.awt.Point;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Target extends JLabel {
	public Target(Point point) {
		setSize(40, 40);
		setLocation(point.x - getWidth() / 2, point.y - getHeight() / 2);
	}

	public Point getCenter() {
		return new Point(getWidth() / 2, getHeight() / 2);
	}

}
