package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;

import Routenplaner.Constants;
import client.FlightPlaner;
import spring.DomainLayerSpringContext;

public class FlightBoxDisabledListener implements ActionListener {

	private ListSelectionModel mydmode;
	private JComboBox<String> myOptionsForFlight;
	private FlightPlaner myFlightPlaner;
	String currentView = null;

	public FlightBoxDisabledListener(ListSelectionModel dmode, JComboBox<String> optionsForFlight) {
		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();
		myFlightPlaner = springContext.GetFlightPlaner();

		mydmode = dmode;
		myOptionsForFlight = optionsForFlight;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object o = event.getSource();

		if (o.equals(myOptionsForFlight)) {
			int index = myOptionsForFlight.getSelectedIndex();
			if (mydmode.isSelectedIndex(index)) {
				return;
			} else {

				switch (index) {
				case 1:
					currentView = Constants.INSERTTARGET;
					break;
				}
				myFlightPlaner.setCurrentView(currentView);
				myFlightPlaner.setView(currentView);
			}
		}
	}
}
