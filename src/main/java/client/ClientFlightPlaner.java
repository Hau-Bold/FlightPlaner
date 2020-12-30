package client;

import javax.swing.SwingUtilities;

public class ClientFlightPlaner {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				FlightPlaner flightPlaner = FlightPlaner.getInstance(args[0]);
				flightPlaner.setVisible(true);
			}
		});

	}
}
