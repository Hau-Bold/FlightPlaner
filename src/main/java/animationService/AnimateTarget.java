package animationService;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import widgets.animation.Target;

public class AnimateTarget implements Runnable {

	private Target myTarget;

	public AnimateTarget(Target target) {
		myTarget = target;
	}

	@Override
	public void run() {

		Graphics2D g = (Graphics2D) myTarget.getGraphics();
		Point center = myTarget.getCenter();
		while (true) {
			for (int j = 0; j < 10; j++) {
				g.setColor(j % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
				g.drawOval((int) center.getX(), (int) center.getY(), 2 * j, 2 * j);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(400);
				myTarget.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
