package PrintPreviewDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Page extends JLabel {

	private static final long serialVersionUID = 1L;
	private final int pageNum;
	private final PageFormat pageFormat;
	private BufferedImage image = null;
	private Dimension size = null;
	private double scale;

	public Page(int pageNum, Dimension size, Pageable pageable, double scale) {
		setHorizontalAlignment(JLabel.CENTER);
		this.scale = scale;
		this.size = size;
		this.image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		this.pageNum = pageNum;
		this.pageFormat = pageable.getPageFormat(pageNum);

		Graphics g = image.getGraphics();
		Color c = g.getColor();
		g.setColor(Color.white);
		g.fillRect(0, 0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight());
		g.setColor(c);
		try {
			pageable.getPrintable(pageNum).print(g, pageFormat, pageNum);
			g.setColor(Color.black);
			g.drawRect(0, 0, (int) pageFormat.getWidth() - 1, (int) pageFormat.getHeight() - 1);
		} catch (Exception ex) {
		}
		this.setIcon(new ImageIcon(image));
	}

	public void refreshScale() {

		if (scale != 1.0) {
			int w = (int) (size.width * scale);
			int h = (int) (size.height * scale);
			setIcon(new ImageIcon(image.getScaledInstance(w, h, BufferedImage.SCALE_FAST)));
		} else
			this.setIcon(new ImageIcon(image));

		this.validate();
	}
}
