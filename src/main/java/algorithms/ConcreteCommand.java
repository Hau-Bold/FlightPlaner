package algorithms;

import java.util.List;

import gps_coordinates.GpsCoordinate;
import widgets.progression.InfiniteProgress;

public final class ConcreteCommand implements ICommand {

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
	public ConcreteCommand(int locX, int locY) {
		this.locX = locX;
		this.locY = locY;
	}

	@Override
	public void execute() {
		new InfiniteProgress(locX, locY).execute();
	}

}
