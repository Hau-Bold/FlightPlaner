package Routenplaner;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import widgets.animation.FrameMap;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private BufferedImage myBufferedimage;

	public BufferedImage getMyBufferedimage() {
		return myBufferedimage;
	}

	public void setBufferedimage(BufferedImage bufferedimage) {
		myBufferedimage = bufferedimage;
	}

	public ImagePanel(BufferedImage bufferedimage) {
		myBufferedimage = bufferedimage;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(bufferedimage.getWidth(), bufferedimage.getHeight()));
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(myBufferedimage, 0, 0, FrameMap.scrollmap);

	}

}
