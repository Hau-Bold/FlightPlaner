package contextmenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.jgoodies.forms.layout.FormLayout;

public class CommonContextMenu extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2796536710016330518L;
	private final int SIZEOFITEM = 25;
	private final int WIDTH = 110;
	private List<IconMenuItem> iconMenuItems = new ArrayList<IconMenuItem>();
	private JPanel panel;

	/**
	 * Constructor.
	 * 
	 * @param event
	 *            - the event
	 * @param routeplaner
	 *            - the routeplaner
	 * @param iconMenuItems
	 *            - list of IconMenuItems
	 */
	CommonContextMenu(MouseEvent event, int x, int y) {
		initComponent(x, y, event);
	}

	private void initComponent(int x, int y, MouseEvent event) {

		this.setUndecorated(true);
		this.setLocation(event.getX() + x, event.getY() + y);
		this.setLayout(new BorderLayout());
		
	}

	protected void showMenu() {
		this.setVisible(true);
	}

	protected void activate() {
		int countOfItem = iconMenuItems.size();
		this.setSize(WIDTH,countOfItem *SIZEOFITEM);
		panel=new JPanel(new GridLayout(countOfItem, 1));
		panel.setSize(WIDTH,countOfItem *SIZEOFITEM);
		
		this.add(panel,BorderLayout.CENTER);
		
		
		iconMenuItems.forEach(item-> panel.add(getPanelWithItem(item)));
	}


	private JPanel getPanelWithItem(IconMenuItem item) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setSize(WIDTH, SIZEOFITEM);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(item,BorderLayout.CENTER);
		return panel;
	}

	protected void addIconMenuItem(IconMenuItem... varargs) {
		for (IconMenuItem item : varargs) {
			iconMenuItems.add(item);
		}
	}

}
