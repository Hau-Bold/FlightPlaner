package Routenplaner;

import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class IconButton extends JButton {

	public IconButton(String pathToImageFolder, String image, int xPos, int yPos) {

		String ressource = pathToImageFolder + File.separator + image;

		ImageIcon icon = new ImageIcon(ressource);
		icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		setIcon(icon);
		setBounds(xPos, yPos, 20, 20);
		setBorder(BorderFactory.createRaisedBevelBorder());
		setVisible(false);
	}

}
