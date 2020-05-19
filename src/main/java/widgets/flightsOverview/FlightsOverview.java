package widgets.flightsOverview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Routenplaner.Colors;
import database.DatabaseLogic;
import listeners.TableOverViewMouseListener;
import render.TargetRenderer;
import routePlanningService.Constants.Constants;
import routePlanningService.Impl.RoutePlanningHelper;
import routePlanningService.overview.Flight;
import tablemodel.CommonModel;
import widgets.contextMenu.OverViewContextMenu;

@SuppressWarnings("serial")
public class FlightsOverview extends JFrame {

	private JTable table;
	private JPanel panelTableNorth;
	private Container panelTable;
	private CommonModel myModel;
	private static OverViewContextMenu myOverViewContextMenu = null;
	private final String[] myNameOfColumns = { Constants.ID, Constants.FLIGHTNUMBER, Constants.START,
			Constants.LONGITUDE, Constants.LATITUDE, Constants.TARGET };

	public void initComponent(List<Flight> flightEntries) {
		// the main frame
		setBounds(50, 50, 500, 500);
		setTitle("FLIGHTS: " + " " + DatabaseLogic.getDbName());
		setResizable(true);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panelTable = new JPanel(new GridLayout(1, 1));
		panelTable.setBackground(Colors.colorTab1);
		add(panelTable);

		panelTableNorth = new JPanel(new BorderLayout());
		panelTableNorth.setPreferredSize(new Dimension(500, 500));
		panelTable.add(panelTableNorth);

		myModel = RoutePlanningHelper.generateModelOfFlights(flightEntries, myNameOfColumns);
		table = new JTable(myModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 400));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFillsViewportHeight(true);
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setFont(new Font("Helvetica", Font.BOLD, 11));
		table.getColumnModel().getColumn(4).setCellRenderer(new TargetRenderer());
		// table.setDefaultRenderer(Object.class, new CustomCellRenderer());
		// MyRowSorter sorter = new MyRowSorter();
		// table.setRowSorter(sorter);
		// sorter.setModel(myModel);
		// sorter.setComparator(0, new idComparator());
		myModel.fireTableDataChanged();
		panelTableNorth.add(new JScrollPane(table));

		table.addMouseListener(new TableOverViewMouseListener(this, myOverViewContextMenu));

	}

	public void showFrame() {
		setVisible(true);
	}

	public void setMyOverViewContextMenu(OverViewContextMenu overViewContextMenu) {
		myOverViewContextMenu = overViewContextMenu;
	}

	public OverViewContextMenu getMyOverViewContextMenu() {
		return myOverViewContextMenu;
	}

	public JTable getTable() {
		return table;
	}

	public CommonModel getMyModel() {
		return myModel;
	}
}
