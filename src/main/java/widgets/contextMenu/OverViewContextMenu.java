package widgets.contextMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import routePlanning.Constants.Constants;
import widgets.IconMenuItem;

@SuppressWarnings("serial")
public class OverViewContextMenu extends widgets.contextMenu.CommonContextMenu implements ActionListener {

	private IconMenuItem removeFlight;
	private IconMenuItem renameFlight;
	private IconMenuItem selectFlight;

	public OverViewContextMenu(MouseEvent event) {
		super(event);

		initComponent();

		showMenu();
	}

	private void initComponent() {

		removeFlight = new IconMenuItem("deleteIcon.png", Constants.REMOVEFLIGHT);
		removeFlight.addActionListener(this);
		renameFlight = new IconMenuItem("rename.jpg", Constants.RENAME);
		renameFlight.addActionListener(this);
		selectFlight = new IconMenuItem("showIcon.png", Constants.SELECTFLIGHT);
		selectFlight.addActionListener(this);

		super.add(removeFlight, renameFlight, selectFlight);
		super.activate();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object o = event.getSource();

	}
}
