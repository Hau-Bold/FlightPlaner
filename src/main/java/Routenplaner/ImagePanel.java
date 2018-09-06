package Routenplaner;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage bufferedimage;

	public BufferedImage getBufferedimage() {
		return bufferedimage;
	}

	public void setBufferedimage(BufferedImage bufferedimage) {
		this.bufferedimage = bufferedimage;
	}

	public ImagePanel(BufferedImage bufferedimage) {
		this.bufferedimage = bufferedimage;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(bufferedimage.getWidth(), bufferedimage.getHeight()));
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(bufferedimage, 0, 0, FrameMap.scrollmap);

	}

}
