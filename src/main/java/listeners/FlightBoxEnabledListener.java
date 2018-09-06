package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;

import Routenplaner.Constants;
import client.Routeplaner;

public class FlightBoxEnabledListener implements ActionListener {

	private JComboBox<String> flightBox;
	private Routeplaner routeplaner;
	String currentView=null;

	public FlightBoxEnabledListener(JComboBox<String> flightBox, Routeplaner routeplaner) {
		this.flightBox=flightBox;
		this.routeplaner=routeplaner;
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
			routeplaner.setCurrentView(currentView);
			routeplaner.setView(currentView);
		}
	}
}



