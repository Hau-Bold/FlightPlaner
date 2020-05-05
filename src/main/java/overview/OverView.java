package overview;

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
import Routenplaner.Constants;
import database.DatabaseLogic;
import listeners.TableOverViewMouseListener;
import render.TargetRenderer;
import tablemodel.CommonModel;
import widgets.contextMenu.OverViewContextMenu;

public class OverView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static OverView instance = null;
	private JTable table;
	private JPanel panelTableNorth;
	private Container panelTable;
	private List<Flight> overViewEntries = null;
	private CommonModel model;
	private static OverViewContextMenu overViewContextMenu = null;
	private final String[] nameOfColumns = { Constants.ID, Constants.FLIGHTNUMBER, Constants.START, Constants.LONGITUDE,
			Constants.LATITUDE, Constants.TARGET };

	// ctor
	public static OverView getInstance(List<Flight> overViewEntries) {
		if (instance == null) {
			instance = new OverView(overViewEntries);
		}
		return instance;
	}

	// ctor
	private OverView(List<Flight> overViewEntries) {
		this.overViewEntries = overViewEntries;
		initComponent();
	}

	private void initComponent() {
		// the main frame
		this.setBounds(50, 50, 500, 500);
		this.setTitle("FLIGHTS: " + " " + DatabaseLogic.getDbName());
		this.setResizable(true);
		this.getContentPane().setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panelTable = new JPanel(new GridLayout(1, 1));
		panelTable.setBackground(Colors.colorTab1);
		this.add(panelTable);

		panelTableNorth = new JPanel(new BorderLayout());
		panelTableNorth.setPreferredSize(new Dimension(500, 500));
		panelTable.add(panelTableNorth);

		model = new CommonModel(nameOfColumns);
		FlightUtils.fillTableModel(overViewEntries, model);
		table = new JTable(model);
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
		// sorter.setModel(model);
		// sorter.setComparator(0, new idComparator());
		model.fireTableDataChanged();
		panelTableNorth.add(new JScrollPane(table));

		table.addMouseListener(new TableOverViewMouseListener(this, overViewContextMenu));

	}

	public void showFrame() {
		this.setVisible(true);
	}

	public void setOverViewContextMenu(OverViewContextMenu overViewContextMenu) {
		this.overViewContextMenu = overViewContextMenu;
	}

	public OverViewContextMenu getOverViewContextMenu() {
		return this.overViewContextMenu;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public CommonModel getModel() {
		return model;
	}

	public void setModel(CommonModel model) {
		this.model = model;
	}

}
