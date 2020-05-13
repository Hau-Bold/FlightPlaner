package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import Routenplaner.Constants;
import client.FlightPlaner;
import spring.DomainLayerSpringContext;

public class FlightBoxEnabledListener implements ActionListener {

	private JComboBox<String> myOptionsToHandleFlights;
	String currentView = null;
	private FlightPlaner myFlightPlaner;

	public FlightBoxEnabledListener(JComboBox<String> optionsToHandleFlight) {
		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();
		myFlightPlaner = springContext.GetFlightPlaner();

		myOptionsToHandleFlights = optionsToHandleFlight;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object o = event.getSource();

		if (o.equals(myOptionsToHandleFlights)) {

			switch (myOptionsToHandleFlights.getSelectedIndex()) {
			// New Flight:
			case 0:
				currentView = Constants.CREATEFLIGHT;

				break;
			// Insert Target
			case 1:
				currentView = Constants.INSERTTARGET;
				break;
			// Drop Flight
			case 2:
				currentView = Constants.DROPFLIGHT;
				break;
			// Select Flight
			case 3:
				currentView = Constants.SELECTFLIGHT;
				break;
			default:
				// do nothing
				break;
			}
			myFlightPlaner.setCurrentView(currentView);
			myFlightPlaner.setView(currentView);
		}
	}
}
