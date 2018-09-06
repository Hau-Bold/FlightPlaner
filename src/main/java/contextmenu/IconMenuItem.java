package contextmenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Routenplaner.Utils;
import listeners.MenuItemMouseListener;


public class IconMenuItem extends JMenuItem{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon icon =null;

	IconMenuItem(String pathOfIcon, String text)
	{
		
	if (Utils.isStringValid(pathOfIcon)) {
		this.icon = new ImageIcon(getClass().getResource("../" + pathOfIcon));
		this.icon.setImage(icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		
	}
	
	this.setIcon(icon);
	this.setSize(20, 150);
	this.setText(text);
	this.setOpaque(true);
	this.setBackground(Color.WHITE);
	
	this.addMouseListener(new MenuItemMouseListener(this));
	}

	public IconMenuItem(String text) {
		this((String)null,text);
	}
}
