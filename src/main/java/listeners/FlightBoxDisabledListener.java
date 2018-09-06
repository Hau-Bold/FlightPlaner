package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;

import Routenplaner.Constants;
import client.Routeplaner;

public class FlightBoxDisabledListener implements ActionListener {

	private ListSelectionModel dmode;
	private JComboBox<String> flightBox;
	private Routeplaner routeplaner;
	String currentView=null;

	public FlightBoxDisabledListener(ListSelectionModel dmode, JComboBox<String> flightBox, Routeplaner routeplaner) {
		this.dmode = dmode;
		this.flightBox=flightBox;
		this.routeplaner=routeplaner;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object o = event.getSource();

		if (o.equals(flightBox)) {
			int index=flightBox.getSelectedIndex();
			if(dmode.isSelectedIndex(index))
			{
				return;
			}
			else
			{
			
			switch (index) {
			case 1:
				currentView = Constants.INSERTTARGET;
				break;
			}
			routeplaner.setCurrentView(currentView);
			routeplaner.setView(currentView);
		}
		}
	}
}



