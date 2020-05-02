package contextmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import Routenplaner.Constants;
import client.Routeplaner;
import widgets.IconMenuItem;

public class TargetsContextMenu extends CommonContextMenu implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Routeplaner routeplaner;

	private IconMenuItem findOptimized;
	private IconMenuItem findNext;
	private IconMenuItem findFarest;
	private IconMenuItem findNearestThenFarest;
	private IconMenuItem findFarestThenNearest;
	private IconMenuItem findRandom;

	private MouseEvent event;

	/**
	 * Constructor.
	 * 
	 * @param event
	 *            - the event
	 * @param routeplaner
	 *            - the routeplaner
	 */
	public TargetsContextMenu(MouseEvent event, Routeplaner routeplaner) {
		super(event, routeplaner.getX(), routeplaner.getY());
		this.routeplaner = routeplaner;
		this.event = event;
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

		super.addIconMenuItem(findOptimized, findNext, findFarest, findNearestThenFarest, findFarestThenNearest,
				findRandom);
		super.activate();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object o = event.getSource();
		int x = this.event.getX() + routeplaner.getX();
		int y = this.event.getY() + routeplaner.getY();
		this.setVisible(false);
		if (o.equals(findOptimized)) {
			routeplaner.reactOnOptimized(x, y);
		}

		else if (o.equals(findNext)) {
			routeplaner.reactOnNext(x, y);
		} else if (o.equals(findFarest)) {
			routeplaner.reactOnFarest(x, y);
		} else if (o.equals(findNearestThenFarest)) {
			routeplaner.reactOnNearestThenFarest(x, y);
		} else if (o.equals(findFarestThenNearest)) {
			routeplaner.reactOnFarestThenNearest(x, y);
		} else if (o.equals(findRandom)) {
			routeplaner.reactOnRandom(x, y);
		}
		routeplaner.getTabbedPane().setSelectedIndex(2);
		this.dispose();
	}

}
