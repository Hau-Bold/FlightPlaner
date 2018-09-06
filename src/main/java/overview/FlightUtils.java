package overview;

import java.util.List;
import java.util.Vector;

import Routenplaner.Constants;
import tablemodel.CommonModel;

public class FlightUtils {

	public static void fillTableModel(List<Flight> receiving, CommonModel model) {
		Vector<String> datarow;
		int counter = 1;
		for (Flight entry : receiving)
		{
			datarow = new Vector<String>();
			datarow.add(String.valueOf(counter));
			datarow.add(entry.getFlightnumber());
			datarow.add(entry.getStart());
			datarow.add(String.valueOf(entry.getLatitude()));
			datarow.add(String.valueOf(entry.getLongitude()));
			datarow.add(entry.getTarget());
			model.addDataRow(datarow);
			counter++;
		}
	}

}
