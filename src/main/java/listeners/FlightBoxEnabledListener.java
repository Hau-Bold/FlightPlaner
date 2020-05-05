package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import Routenplaner.Constants;
import client.Routeplaner;

public class FlightBoxEnabledListener implements ActionListener {

	private JComboBox<String> flightBox;
	String currentView = null;

	public FlightBoxEnabledListener(JComboBox<String> flightBox) {
		this.flightBox = flightBox;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object o = event.getSource();

		if (o.equals(flightBox)) {

			switch (flightBox.getSelectedIndex()) {
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
			Routeplaner.getInstance().setCurrentView(currentView);
			Routeplaner.getInstance().setView(currentView);
		}
	}
}
