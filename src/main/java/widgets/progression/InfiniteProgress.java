package widgets.progression;

import javax.swing.SwingWorker;

import client.FlightPlaner;

public class InfiniteProgress extends SwingWorker<Void, Void> {
	private int myCurrentXCoordinate;
	private int myCurrentYCoordinate;
	private FlightPlaner flightPlaner;

	public InfiniteProgress(int currentXCoordinate, int currentYCoordinate) {
		myCurrentXCoordinate = currentXCoordinate;
		myCurrentYCoordinate = currentYCoordinate;
		flightPlaner = FlightPlaner.getInstance();
	}

	@Override
	protected Void doInBackground() throws Exception {
		ProgressBar Progress = new ProgressBar(myCurrentXCoordinate, myCurrentYCoordinate);
		Progress.getProgressBar().setIndeterminate(true);
		flightPlaner.check();
		Progress.dispose();

		return null;
	}

	@Override
	protected void done() {
		super.done();
	}

}
