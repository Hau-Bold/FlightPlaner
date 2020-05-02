package widgets.progression;

import java.util.List;

import javax.swing.SwingWorker;

import algorithms.IOptimization;
import client.Routeplaner;
import gps_coordinates.GpsCoordinate;

public class InfiniteProgress extends SwingWorker<Void, Void> {
	private int myCurrentXCoordinate;
	private int myCurrentYCoordinate;
	private GpsCoordinate startGps;
	private List<GpsCoordinate> targets;
	private IOptimization myOptimization;
	private Routeplaner routeplaner;

	public InfiniteProgress(Routeplaner routeplaner, IOptimization optimization, GpsCoordinate startGps,
			List<GpsCoordinate> targets, int currentXCoordinate, int currentYCoordinate) {
		this.routeplaner = routeplaner;
		myOptimization = optimization;
		this.startGps = startGps;
		this.targets = targets;
		myCurrentXCoordinate = currentXCoordinate;
		myCurrentYCoordinate = currentYCoordinate;
	}

	@Override
	protected Void doInBackground() throws Exception {
		ProgressBar Progress = new ProgressBar(myCurrentXCoordinate, myCurrentYCoordinate);
		Progress.getProgressBar().setIndeterminate(true);
		routeplaner.check(myOptimization.compute(startGps, targets));// TODO only routeplaner should know optimization?
		Progress.dispose();

		return null;
	}

	@Override
	protected void done() {
		super.done();
	}

}
