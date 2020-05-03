package widgets.progression;

import java.util.List;

import javax.swing.SwingWorker;

import client.Routeplaner;
import gps_coordinates.GpsCoordinate;

public class InfiniteProgress extends SwingWorker<Void, Void> {
	private int myCurrentXCoordinate;
	private int myCurrentYCoordinate;
	private GpsCoordinate startGps;
	private List<GpsCoordinate> targets;

	public InfiniteProgress(GpsCoordinate startGps, List<GpsCoordinate> targets, int currentXCoordinate,
			int currentYCoordinate) {
		this.startGps = startGps;
		this.targets = targets;
		myCurrentXCoordinate = currentXCoordinate;
		myCurrentYCoordinate = currentYCoordinate;
	}

	@Override
	protected Void doInBackground() throws Exception {
		ProgressBar Progress = new ProgressBar(myCurrentXCoordinate, myCurrentYCoordinate);
		Progress.getProgressBar().setIndeterminate(true);
		Routeplaner.getInstance().check(startGps, targets);
		Progress.dispose();

		return null;
	}

	@Override
	protected void done() {
		super.done();
	}

}
