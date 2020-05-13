package widgets.contextMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;

import Routenplaner.AddressVector;
import Routenplaner.Constants;
import client.FlightPlaner;
import database.DatabaseLogic;
import database.QueryHelper;
import gps_coordinates.GpsCoordinate;
import routePlanningService.Impl.RoutePlanningHelper;
import spring.DomainLayerSpringContext;
import tablemodel.CommonModel;
import widgets.IconMenuItem;
import widgets.flightsOverview.FlightsOverview;

@SuppressWarnings("serial")
public class OverViewContextMenu extends widgets.contextMenu.CommonContextMenu implements ActionListener {

	private IconMenuItem removeFlight;
	private IconMenuItem renameFlight;
	private IconMenuItem selectFlight;
	private FlightsOverview overView;
	private FlightPlaner myFlightPlaner;

	public OverViewContextMenu(FlightsOverview overView, MouseEvent event) {
		super(event);
		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();
		myFlightPlaner = springContext.GetFlightPlaner();

		initComponent();
		this.overView = overView;
		showMenu();
	}

	private void initComponent() {

		removeFlight = new IconMenuItem("deleteIcon.png", Constants.REMOVEFLIGHT);
		removeFlight.addActionListener(this);
		renameFlight = new IconMenuItem("rename.jpg", Constants.RENAME);
		renameFlight.addActionListener(this);
		selectFlight = new IconMenuItem("showIcon.png", Constants.SELECTFLIGHT);
		selectFlight.addActionListener(this);

		super.add(removeFlight, renameFlight, selectFlight);
		super.activate();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object o = event.getSource();

		if (o.equals(selectFlight)) {
			JTable table = overView.getTable();
			int row = table.getSelectedRow();
			if (row != -1) {
				String flightNumber = (String) table.getValueAt(row, 1);
				ArrayList<GpsCoordinate> flight = null;
				try {
					flight = myFlightPlaner.getDatabase().getFlightAsList(flightNumber);
					/** setting start */
					myFlightPlaner.setStartGps(
							DatabaseLogic.getStartGps(flightNumber, myFlightPlaner.getDatabase().getConnection()));
					myFlightPlaner.setMaster(flight);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (flight != null) {
					CommonModel modelTargets = myFlightPlaner.getModelTargets();
					if (!modelTargets.isEmpty()) {
						modelTargets.clear();
					}
					flight.forEach(gps -> modelTargets.addDataRow(new AddressVector(String.valueOf(gps.getId()),
							gps.getStreet(), gps.getCity(), gps.getCountry(), String.valueOf(gps.getLongitude()),
							String.valueOf(gps.getLatitude()))));
					modelTargets.revalidate();
					JLabel statusBar = myFlightPlaner.getStatusBar();
					statusBar.setText(DatabaseLogic.getDbName() + File.separator + flightNumber);
					myFlightPlaner.setStatusBar(statusBar);
					myFlightPlaner.flightNumber = flightNumber;
					overView.dispose();
				}
			}
			myFlightPlaner.getTabbedPane().setSelectedIndex(1);
			this.dispose();
		}

		else if (o.equals(removeFlight)) {
			JTable table = overView.getTable();
			CommonModel model = overView.getMyModel();
			DatabaseLogic databaseLogic = myFlightPlaner.getDatabase();
			String nameOfFlight = null;

			int[] arrayOfSelectedRows = table.getSelectedRows();

			if (!RoutePlanningHelper.isEmpty(arrayOfSelectedRows)) {
				for (int row = 0; row < arrayOfSelectedRows.length; row++) {
					nameOfFlight = (String) table.getValueAt(arrayOfSelectedRows[row], 1);
					try {
						DatabaseLogic.removeFlight(nameOfFlight, databaseLogic);
						QueryHelper.dropTable(nameOfFlight,
								myFlightPlaner.getDatabase().getConnection().getConnection());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				model.deleteRow(arrayOfSelectedRows);
				model.revalidate();
				if (nameOfFlight.equals(myFlightPlaner.getFlightNumber())) {
					myFlightPlaner.getModelTargets().clear();
				}
			}
			this.dispose();
		}
	}
}
