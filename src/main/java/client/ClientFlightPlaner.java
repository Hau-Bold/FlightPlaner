package client;

import javax.swing.SwingUtilities;

import spring.DomainLayerSpringContext;

public class ClientFlightPlaner {

	public static void main(String[] args) {

		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext(args[0]);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				FlightPlaner routePlaner = springContext.GetFlightPlaner();
				routePlaner.setDirectory(args[0]);// Todo how to pass as argument?????
				routePlaner.initComponent();
				routePlaner.setVisible(true);
			}
		});

	}

}
