package widgets.progression;

import javax.swing.SwingWorker;

import client.RoutePlanningService;

public class InfiniteProgress extends SwingWorker<Void, Void> {
	private int myCurrentXCoordinate;
	private int myCurrentYCoordinate;

	public InfiniteProgress(int currentXCoordinate, int currentYCoordinate) {
		myCurrentXCoordinate = currentXCoordinate;
		myCurrentYCoordinate = currentYCoordinate;
	}

	@Override
	protected Void doInBackground() throws Exception {
		ProgressBar Progress = new ProgressBar(myCurrentXCoordinate, myCurrentYCoordinate);
		Progress.getProgressBar().setIndeterminate(true);
		RoutePlanningService.getInstance().check();
		Progress.dispose();

		return null;
	}

	@Override
	protected void done() {
		super.done();
	}

}
