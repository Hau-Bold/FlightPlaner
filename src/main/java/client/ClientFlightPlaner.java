package client;

import javax.swing.SwingUtilities;

public class ClientFlightPlaner {

	public static void main(String[] args) {

		// DomainLayerSpringContext springContext =
		// DomainLayerSpringContext.GetContext(args[0]);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				FlightPlaner flightPlaner = FlightPlaner.getInstance(args[0]);
				flightPlaner.initComponent();// TODO make later private
				flightPlaner.setVisible(true);
			}
		});

	}

}
