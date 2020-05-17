package widgets.contextMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import Routenplaner.Constants;
import client.FlightPlaner;
import routePlanningService.Contract.IOptimizationService;
import routePlanningService.Impl.OptimizationService;
import spring.DomainLayerSpringContext;
import widgets.IconMenuItem;

@SuppressWarnings("serial")
public class TargetsContextMenu extends CommonContextMenu implements ActionListener {
	private IconMenuItem findOptimized;
	private IconMenuItem findNext;
	private IconMenuItem findFarest;
	private IconMenuItem findNearestThenFarest;
	private IconMenuItem findFarestThenNearest;
	private IconMenuItem findRandom;
	private FlightPlaner myRouteplanningService;
	private OptimizationService myOptimizationService;

	// ctor
	public TargetsContextMenu(MouseEvent event) {
		super(event);
		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();
		myOptimizationService = (OptimizationService) springContext.GetOptimizationService();
		myRouteplanningService = FlightPlaner.getInstance();
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

		IOptimizationService optimization = null;

		int x = getLocation().x;
		int y = getLocation().y;
		this.setVisible(false);

		if (o.equals(findOptimized)) {
			optimization = myOptimizationService.new FindOptimized();
		}

		else if (o.equals(findNext)) {
			optimization = myOptimizationService.new FindNext();
		} else if (o.equals(findFarest)) {
			optimization = myOptimizationService.new FindFarest();
		} else if (o.equals(findNearestThenFarest)) {
			optimization = myOptimizationService.new FindNearestThenFarest();
		} else if (o.equals(findFarestThenNearest)) {
			optimization = myOptimizationService.new FindFarestThenNearest();
		} else if (o.equals(findRandom)) {
			optimization = myOptimizationService.new FindRandom();
		}

		myRouteplanningService.setOptimization(optimization);
		myRouteplanningService.executeOptimization(x, y);
		myRouteplanningService.getTabbedPane().setSelectedIndex(2);

		this.dispose();
	}

}
