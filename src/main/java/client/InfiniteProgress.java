package client;

import java.util.List;

import javax.swing.SwingWorker;

import algorithms.IOptimization;
import gps_coordinates.GpsCoordinate;

public class InfiniteProgress extends SwingWorker<Void, Void> {
	private int locX;
	private int locY;
	private GpsCoordinate startGps;
	private List<GpsCoordinate> targets;
	private IOptimization abstractOptimization;
	private Routeplaner routeplaner;

	public InfiniteProgress(Routeplaner routeplaner, IOptimization abstractOptimization, GpsCoordinate startGps, List<GpsCoordinate> targets, int locX, int locY) {
		this.routeplaner=routeplaner;
		this.abstractOptimization=abstractOptimization;
		this.startGps = startGps;
		this.targets = targets;
		this.locX = locX;
		this.locY = locY;
	}

	@Override
	protected Void doInBackground() throws Exception {
		ProgressBar Progress = new ProgressBar(locX, locY);
		Progress.getProgressBar().setIndeterminate(true);
		routeplaner.check(abstractOptimization.compute(startGps, targets));
		Progress.dispose();
		
		return null;
	}

	@Override
	protected void done() {
		// TODO Auto-generated method stub
		super.done();
	}

}
