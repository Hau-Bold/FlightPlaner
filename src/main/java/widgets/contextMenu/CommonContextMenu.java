package widgets.contextMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import widgets.IconMenuItem;

@SuppressWarnings("serial")
public class CommonContextMenu extends JDialog {

	private final static int SIZEOFITEM = 25;
	private final static int WIDTH = 110;
	private List<IconMenuItem> myIconMenuItems = new ArrayList<IconMenuItem>();
	private JPanel panel;

	/** ctor */
	protected CommonContextMenu(MouseEvent event) {
		initComponent(event);
	}

	private void initComponent(MouseEvent event) {
		this.setUndecorated(true);
		this.setLocation(event.getX(), event.getY());
		this.setLayout(new BorderLayout());
	}

	protected void showMenu() {
		this.setVisible(true);
	}

	protected void activate() {
		int countOfItem = myIconMenuItems.size();
		this.setSize(WIDTH, countOfItem * SIZEOFITEM);
		panel = new JPanel(new GridLayout(countOfItem, 1));
		panel.setSize(WIDTH, countOfItem * SIZEOFITEM);

		this.add(panel, BorderLayout.CENTER);

		myIconMenuItems.forEach(item -> panel.add(createPanelWithIconMenuItem(item)));
	}

	private static JPanel createPanelWithIconMenuItem(IconMenuItem item) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setSize(WIDTH, SIZEOFITEM);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(item, BorderLayout.CENTER);
		return panel;
	}

	protected void add(IconMenuItem... varargs) {
		for (IconMenuItem item : varargs) {
			myIconMenuItems.add(item);
		}
	}

}
