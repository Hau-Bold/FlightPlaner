package Routenplaner;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScrollPaneMap extends JScrollPane {

	private BufferedImage bufferedimage;
	private static ImageIcon imageicon;
	private final String pathOfWorldMap = "../Images/zeitzonen.png";

	public ScrollPaneMap() {
		bufferedimage = null;
		// Lade Weltkarte ins bufferedimage:
		try {
			bufferedimage = ImageIO.read(getClass().getResource(pathOfWorldMap));
		} catch (IOException e) {
			System.err.println(String.format("not able to load image from < %s >", pathOfWorldMap));
			e.printStackTrace();
		}

		imageicon = new ImageIcon(bufferedimage);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	}

}
