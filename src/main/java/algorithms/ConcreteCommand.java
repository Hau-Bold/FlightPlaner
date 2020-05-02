package algorithms;

import java.util.ArrayList;
import java.util.List;

import client.Routeplaner;
import gps_coordinates.GpsCoordinate;
import widgets.progression.InfiniteProgress;

public class ConcreteCommand implements ICommand {

	private IOptimization abstractOptimization;

	private GpsCoordinate startGps;
	private List<GpsCoordinate> targets;
	private int locX;
	private int locY;

	private Routeplaner routeplaner;

	/**
	 * 
	 * @param routeplaner
	 * @param abstractOptimization
	 *            the optimization
	 * @param start
	 *            - the start
	 * @param targets
	 *            - the targets
	 * @param locY
	 * @param locX
	 */
	public ConcreteCommand(Routeplaner routeplaner, IOptimization abstractOptimization, GpsCoordinate startGps,
			ArrayList<GpsCoordinate> targets, int locX, int locY) {
		this.routeplaner = routeplaner;
		this.abstractOptimization = abstractOptimization;
		this.startGps = startGps;
		this.targets = targets;
		this.locX = locX;
		this.locY = locY;
	}

	@Override
	public void execute() {
		new InfiniteProgress(routeplaner, abstractOptimization, startGps, targets, locX, locY).execute();
	}

}
