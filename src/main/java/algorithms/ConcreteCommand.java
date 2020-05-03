package algorithms;

import java.util.ArrayList;
import java.util.List;

import gps_coordinates.GpsCoordinate;
import widgets.progression.InfiniteProgress;

public final class ConcreteCommand implements ICommand {

	private GpsCoordinate startGps;
	private List<GpsCoordinate> targets;
	private int locX;
	private int locY;

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
	public ConcreteCommand(GpsCoordinate startGps, ArrayList<GpsCoordinate> targets, int locX, int locY) {
		this.startGps = startGps;
		this.targets = targets;
		this.locX = locX;
		this.locY = locY;
	}

	@Override
	public void execute() {
		new InfiniteProgress(startGps, targets, locX, locY).execute();
	}

}
