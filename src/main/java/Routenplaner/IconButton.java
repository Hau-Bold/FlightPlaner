package Routenplaner;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class IconButton extends JButton {
	
	private ImageIcon icon;

	/**
	 * Constructor
	 * @param path - the path to the icon
	 * @param xPos - position in x direction
	 * @param yPos - position in y direction
	 */
	public IconButton(String path, int xPos, int yPos) {

		icon = new ImageIcon(getClass().getResource("../" + path));
		icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		this.setIcon(icon);
		this.setBounds(xPos, yPos, 20, 20);
		setBorder(BorderFactory.createRaisedBevelBorder());
		this.setVisible(false);
	}

}
