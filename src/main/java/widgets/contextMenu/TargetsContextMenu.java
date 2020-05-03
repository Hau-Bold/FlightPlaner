package widgets.contextMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import Routenplaner.Constants;
import algorithms.FindFarest;
import algorithms.FindFarestThenNearest;
import algorithms.FindNearestThenFarest;
import algorithms.FindNext;
import algorithms.FindOptimized;
import algorithms.FindRandom;
import algorithms.IOptimization;
import client.Routeplaner;
import widgets.IconMenuItem;

@SuppressWarnings("serial")
public class TargetsContextMenu extends CommonContextMenu implements ActionListener {
	private IconMenuItem findOptimized;
	private IconMenuItem findNext;
	private IconMenuItem findFarest;
	private IconMenuItem findNearestThenFarest;
	private IconMenuItem findFarestThenNearest;
	private IconMenuItem findRandom;

	/**
	 * Constructor.
	 * 
	 * @param event
	 *            - the event
	 * @param routeplaner
	 *            - the routeplaner
	 */
	public TargetsContextMenu(MouseEvent event) {
		super(event);
		initComponent();
		showMenu();
	}

	private void initComponent() {
		findOptimized = new IconMenuItem(Constants.OPTIMIZE);
		findOptimized.addActionListener(this);

		findNext = new IconMenuItem(Constants.NEXT);
		findNext.addActionListener(this);

		findFarest = new IconMenuItem(Constants.FAREST);
		findFarest.addActionListener(this);

		findNearestThenFarest = new IconMenuItem(Constants.NextFar);
		findNearestThenFarest.addActionListener(this);

		findFarestThenNearest = new IconMenuItem(Constants.FarNext);
		findFarestThenNearest.addActionListener(this);

		findRandom = new IconMenuItem(Constants.RANDOM);
		findRandom.addActionListener(this);

		super.add(findOptimized, findNext, findFarest, findNearestThenFarest, findFarestThenNearest, findRandom);
		super.activate();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object o = event.getSource();

		Routeplaner routeplaner = Routeplaner.getInstance();

		int x = getLocation().x;
		int y = getLocation().y;
		this.setVisible(false);

		IOptimization optimization = null;

		if (o.equals(findOptimized)) {
			optimization = new FindOptimized();
		}

		else if (o.equals(findNext)) {
			optimization = new FindNext();
		} else if (o.equals(findFarest)) {
			optimization = new FindFarest();
		} else if (o.equals(findNearestThenFarest)) {
			optimization = new FindNearestThenFarest();
		} else if (o.equals(findFarestThenNearest)) {
			optimization = new FindFarestThenNearest();
		} else if (o.equals(findRandom)) {
			optimization = new FindRandom();
		}

		routeplaner.setOptimization(optimization);
		routeplaner.executeOptimization(x, y);
		routeplaner.getTabbedPane().setSelectedIndex(2);

		this.dispose();
	}

}
