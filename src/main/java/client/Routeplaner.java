package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import com.jgoodies.common.internal.Messages;

import QueryHelper.QueryHelper.QueryHelper;
import Routenplaner.AddressDialog;
import Routenplaner.AddressVector;
import Routenplaner.Constants;
import Routenplaner.Fonts;
import Routenplaner.FrameMap;
import Routenplaner.IconButton;
import Routenplaner.Utils;
import algorithms.ConcreteCommand;
import algorithms.FindFarest;
import algorithms.FindFarestThenNearest;
import algorithms.FindNearestThenFarest;
import algorithms.FindNext;
import algorithms.FindOptimized;
import algorithms.FindRandom;
import contextmenu.TargetsContextMenu;
import database.DatabaseLogic;
import gps_coordinates.GPS;
import gps_coordinates.GpsCoordinate;
import listeners.FlightBoxDisabledListener;
import listeners.FlightBoxEnabledListener;
import listeners.ListenerForEmptyFields;
import listeners.RoutePlanerMouseListener;
import listeners.RoutePlanerWindowListener;
import listeners.TableTargetsMouseListener;
import overview.Flight;
import overview.OverView;
import overview.OverViewLogic;
import render.CityRenderer;
import render.TargetRenderer;
import tablemodel.CommonModel;
import toolbar.ConfirmingAddress;
import toolbar.ConfirmingAdressLocationListener;
import toolbar.RoutePlanerLocationListener;

public class Routeplaner extends JFrame implements ActionListener, DocumentListener {

	// TODO privide setter for optimization and remove Interface from command....
	private final int X = 10;
	private final int Y = 10;
	private final int WIDTH = 500;
	private final int HEIGHT = 500;

	private static Routeplaner routeplaner;
	private JTabbedPane tabbedPane;

	private JPanel panelAdresse, panelTargets;
	private JComboBox<String> databaseBox, flightBox;
	private JLabel lblFlight, lblstreet, lblcity, lblcountry;
	private JTextField txtNewFlight, txtStreet, txtCity, txtCountry;

	private static DatabaseLogic database;
	private JLabel statusBar;

	/**
	 * to check the connection
	 */
	private boolean isConnected = false;
	private boolean iscreated = false;
	private boolean isdropped = false;

	/*
	 * to save the name of the table in the database
	 */
	private JTextField txtdatabase;
	private JLabel lblTABLE;
	private JButton btnCreateTable, btnDropTable;

	/**
	 * to save the requested GpsCoordinates
	 */
	private ArrayList<GpsCoordinate> master = new ArrayList<GpsCoordinate>();

	/**
	 * panel,model and table for addresses
	 */
	private CommonModel modelTargets;
	private CommonModel modelRoute;
	private JTable tableTargets, tableRoute;
	private JPanel panelTargetsNorth;

	// /**
	// * to count addresses
	// */
	// private int counter = 0;
	/**
	 * toolbar to save table actions
	 */
	private JToolBar toolBarForTableActions;

	/**
	 * buttons for table actions
	 */
	private JButton btnUpdate, btnDeleteRow;

	/**
	 * toolbar, die Buttons f�r die Optimierungsalgorithmen entgegennimmt
	 */
	private JToolBar toolBarAlgorithms;
	/**
	 * the compute actions
	 */

	private JButton btnShowMap, btnMyLocation;
	private JPanel panelComputedRoute, panelComputedRouteNorth;
	private JToolBar toolbarMap;

	/**
	 * Liste, in der die berechnete Route gespeichert wird
	 */
	private List<GpsCoordinate> computedRoute;

	private IconButton btnSubmitFlightNumber, btnSubmitFlightToSelect, btnSave, btnAccessData, btnSubmitFlightToDrop;
	public static String flightNumber;

	private JTextField txtDropFlight, txtSelectFlight;

	// to set the view
	private String currentView;
	private GpsCoordinate startGps = null;
	private CityRenderer cityRender;
	private TargetsContextMenu targetContextMenu = null;
	private ConfirmingAddress confirmingAdress;
	private RoutePlanerLocationListener routePlanerLocationListener;
	private RoutePlanerWindowListener routePlanerWindowListener;

	public boolean isGlued = false;
	public OverView overView = null;
	private DefaultListSelectionModel listSelectionModel;

	/**
	 * Constructor.
	 */
	private Routeplaner() {

		// Setting the Layout
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel((UIManager.getSystemLookAndFeelClassName()));
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
		initComponent();
	}

	private void initComponent() {

		this.setTitle(Constants.ROUTEPLANER);
		this.setBounds(X + 100, Y, WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		confirmingAdress = new ConfirmingAddress(this.getX(), this.getY() + this.getWidth(), this.getWidth(), 60, this);

		routePlanerLocationListener = new RoutePlanerLocationListener(confirmingAdress, this);
		this.addComponentListener(routePlanerLocationListener);
		confirmingAdress.addComponentListener(new ConfirmingAdressLocationListener(confirmingAdress, this));
		routePlanerWindowListener = new RoutePlanerWindowListener(confirmingAdress);
		this.addWindowListener(routePlanerWindowListener);

		// adding tabbedPane
		tabbedPane = new JTabbedPane();
		this.getContentPane().add(tabbedPane);

		// panel for address and database
		panelAdresse = new JPanel();
		panelAdresse.setLayout(null);
		tabbedPane.addTab("Datenbank", panelAdresse);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_0);

		// the combobox for databasemenu:
		String flightOptions[] = { Constants.CREATEFLIGHT, Constants.INSERTTARGET, Constants.DROPFLIGHT,
				Constants.SELECTFLIGHT };
		flightBox = new JComboBox<String>(flightOptions);
		flightBox.setBounds(0, 0, 100, 20);
		flightBox.setBorder(BorderFactory.createRaisedBevelBorder());
		flightBox.setFont(Fonts.MainFont);
		listSelectionModel = new DefaultListSelectionModel();
		listSelectionModel.addSelectionInterval(0, 0);
		listSelectionModel.addSelectionInterval(2, 3);
		flightBox.setRenderer(new ComboBoxRenderer(listSelectionModel));
		flightBox.addActionListener(new FlightBoxDisabledListener(listSelectionModel, flightBox, this));

		// the combobox for the database:
		String dBOptions[] = { Constants.CONNECT, Constants.DISCONNECT };
		databaseBox = new JComboBox<String>(dBOptions);
		databaseBox.setBounds(100, 0, 80, 20);
		databaseBox.setBorder(BorderFactory.createRaisedBevelBorder());
		databaseBox.setFont(Fonts.MainFont);
		databaseBox.addActionListener(this);
		databaseBox.setEnabled(true);

		btnSave = new IconButton("Images/saveIcon.png", 180, 0);
		btnSave.addActionListener(this);

		btnAccessData = new IconButton("Images/accessDataIcon.jpg", 200, 0);
		btnAccessData.addActionListener(this);

		panelAdresse.add(flightBox);
		panelAdresse.add(databaseBox);
		panelAdresse.add(btnSave);
		panelAdresse.add(btnAccessData);

		// Anlegen der Flugnummer:
		lblFlight = new JLabel(Constants.FLIGHTNUMBER + ":");
		lblFlight.setOpaque(true);
		lblFlight.setFont(Fonts.MainFont);
		lblFlight.setBounds(20, 50, 80, 20);
		lblFlight.setVisible(false);
		panelAdresse.add(lblFlight);

		btnSubmitFlightNumber = new IconButton("Images/confirmIcon.png", 200, 50);
		btnSubmitFlightNumber.addActionListener(this);
		panelAdresse.add(btnSubmitFlightNumber);

		txtNewFlight = new JTextField();
		txtNewFlight.setBounds(110, 50, 90, 20);
		txtNewFlight.setFocusable(true);
		txtNewFlight.setBackground(Color.GREEN);
		txtNewFlight.setVisible(false);
		txtNewFlight.getDocument().addDocumentListener(new ListenerForEmptyFields(txtNewFlight, btnSubmitFlightNumber));
		panelAdresse.add(txtNewFlight);

		// Drop a concrete Flight:
		btnSubmitFlightToDrop = new IconButton("Images/deleteIcon.png", 200, 50);
		btnSubmitFlightToDrop.addActionListener(this);
		panelAdresse.add(btnSubmitFlightToDrop);

		txtDropFlight = new JTextField();
		txtDropFlight.setBounds(110, 50, 90, 20);
		txtDropFlight.setBackground(Color.GREEN);
		txtDropFlight.setVisible(false);
		txtDropFlight.getDocument()
				.addDocumentListener(new ListenerForEmptyFields(txtDropFlight, btnSubmitFlightToDrop));
		panelAdresse.add(txtDropFlight);

		// Select a concrete Flight:
		btnSubmitFlightToSelect = new IconButton("Images/confirmIcon.png", 200, 50);
		btnSubmitFlightToSelect.addActionListener(this);
		panelAdresse.add(btnSubmitFlightToSelect);

		txtSelectFlight = new JTextField();
		txtSelectFlight.setBounds(110, 50, 90, 20);
		txtSelectFlight.setBackground(Color.GREEN);
		txtSelectFlight.setVisible(false);
		txtSelectFlight.getDocument()
				.addDocumentListener(new ListenerForEmptyFields(txtSelectFlight, btnSubmitFlightToSelect));
		panelAdresse.add(txtSelectFlight);

		// adjust address
		// street
		lblstreet = new JLabel("Street:");
		lblstreet.setOpaque(true);
		lblstreet.setBounds(20, 50, 50, 20);
		lblstreet.setFont(Fonts.MainFont);
		lblstreet.setVisible(false);
		panelAdresse.add(lblstreet);

		txtStreet = new JTextField();
		txtStreet.setBounds(80, 50, 100, 20);
		txtStreet.setFocusable(true);
		txtStreet.setBackground(Color.GREEN);
		txtStreet.setVisible(false);
		panelAdresse.add(txtStreet);

		// city
		lblcity = new JLabel("City:");
		lblcity.setOpaque(true);
		lblcity.setBounds(20, 80, 50, 20);
		lblcity.setFont(Fonts.MainFont);
		lblcity.setVisible(false);
		panelAdresse.add(lblcity);

		txtCity = new JTextField();
		txtCity.setBounds(80, 80, 100, 20);
		txtCity.setFocusable(true);
		txtCity.setBackground(Color.GREEN);
		txtCity.getDocument().addDocumentListener(new ListenerForEmptyFields(txtCity, confirmingAdress));
		txtCity.setVisible(false);
		panelAdresse.add(txtCity);

		// country:
		lblcountry = new JLabel("Country:");
		lblcountry.setOpaque(true);
		lblcountry.setBounds(20, 110, 50, 20);
		lblcountry.setFont(Fonts.MainFont);
		lblcountry.setVisible(false);
		panelAdresse.add(lblcountry);

		txtCountry = new JTextField();
		txtCountry.setBounds(80, 110, 100, 20);
		txtCountry.setFocusable(true);
		txtCountry.setBackground(Color.GREEN);
		txtCountry.setVisible(false);
		panelAdresse.add(txtCountry);

		// adjust statusbar
		statusBar = new JLabel();
		statusBar.setBounds(290, 0, 200, 20);
		statusBar.setOpaque(true);
		statusBar.setBackground(Color.WHITE);
		statusBar.setForeground(Color.RED);
		statusBar.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		statusBar.setVisible(false);
		panelAdresse.add(statusBar, BorderLayout.NORTH);

		txtdatabase = new JTextField();
		txtdatabase.setBounds(320, 20, 100, 25);
		txtdatabase.setVisible(false);
		txtdatabase.getDocument().addDocumentListener(this);
		panelAdresse.add(txtdatabase);

		lblTABLE = new JLabel();
		lblTABLE.setBounds(220, 20, 100, 25);
		lblTABLE.setVisible(false);
		panelAdresse.add(lblTABLE);

		/*
		 * Initialisierung des Buttons f�r Erstellung der TABLE
		 */
		btnCreateTable = new JButton();
		btnCreateTable.setBounds(430, 20, 25, 25);
		btnCreateTable.setVisible(false);
		btnCreateTable.addActionListener(this);
		panelAdresse.add(btnCreateTable);

		btnDropTable = new JButton();
		btnDropTable.setBounds(430, 20, 25, 25);
		btnDropTable.setVisible(false);
		btnDropTable.addActionListener(this);
		panelAdresse.add(btnDropTable);

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		panelTargets = new JPanel(new BorderLayout());
		tabbedPane.add(Constants.TARGETS, panelTargets);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_1);

		panelTargetsNorth = new JPanel(new GridLayout(1, 1));
		panelTargetsNorth.setPreferredSize(new Dimension(200, 380));
		panelTargetsNorth.setBackground(Color.BLUE);
		panelTargets.add(panelTargetsNorth, BorderLayout.NORTH);

		String[] columns = { Constants.ID, Constants.STREET, Constants.CITY, Constants.COUNTRY, Constants.LONGITUDE,
				Constants.LATITUDE };
		modelTargets = new CommonModel(columns);
		tableTargets = new JTable(modelTargets);
		tableTargets.setPreferredScrollableViewportSize(new Dimension(200, 100));
		tableTargets.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableTargets.getTableHeader().setReorderingAllowed(false);
		tableTargets.setFillsViewportHeight(true);
		tableTargets.setFont(new Font("Helvetica", Font.BOLD, 11));
		tableTargets.getColumnModel().getColumn(5).setCellRenderer(new TargetRenderer());
		tableTargets.addMouseListener(new TableTargetsMouseListener(this, targetContextMenu));

		panelTargetsNorth.add(new JScrollPane(tableTargets));

		toolBarForTableActions = new JToolBar();
		toolBarForTableActions.setPreferredSize(new Dimension(100, 30));

		btnUpdate = new JButton();
		btnUpdate.setText(Constants.UPDATE);
		btnUpdate.setPreferredSize(new Dimension(60, 0));
		btnUpdate.addActionListener(this);

		btnDeleteRow = new JButton();
		btnDeleteRow.setText(Constants.DELETE);
		btnDeleteRow.setPreferredSize(new Dimension(60, 0));
		btnDeleteRow.addActionListener(this);

		toolBarForTableActions.add(btnUpdate);
		toolBarForTableActions.add(btnDeleteRow);

		panelTargets.add(toolBarForTableActions, BorderLayout.PAGE_END);

		toolBarAlgorithms = new JToolBar();
		toolBarAlgorithms.setBounds(0, 500, 100, 30);

		/* setting start location */
		btnMyLocation = new JButton(Constants.STARTPOINT);
		btnMyLocation.setPreferredSize(new Dimension(80, 0));
		btnMyLocation.setBackground(Color.ORANGE);
		btnMyLocation.setToolTipText("enter location");
		btnMyLocation.setMnemonic(KeyEvent.VK_M);
		btnMyLocation.addActionListener(this);

		toolBarAlgorithms.add(btnMyLocation);

		panelTargets.add(toolBarAlgorithms);

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		panelComputedRoute = new JPanel(new BorderLayout());

		tabbedPane.add(Constants.Route, panelComputedRoute);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_2);

		// Ein panel f�r die Tabelle
		panelComputedRouteNorth = new JPanel(new GridLayout(1, 1));
		panelComputedRouteNorth.setPreferredSize(new Dimension(200, 420));
		panelComputedRoute.add(panelComputedRouteNorth, BorderLayout.NORTH);

		// Anlegen der Tabelle
		final String[] nameofCOlumns = { Constants.ID, Constants.STREET, Constants.CITY, Constants.COUNTRY,
				Constants.DISTANCE };

		modelRoute = new CommonModel(nameofCOlumns);
		tableRoute = new JTable(modelRoute);
		tableRoute.setPreferredScrollableViewportSize(new Dimension(200, 150));
		tableRoute.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableRoute.getTableHeader().setReorderingAllowed(false);
		tableRoute.setFillsViewportHeight(true);
		tableRoute.setFont(new Font("Helvetica", Font.BOLD, 11));

		cityRender = new CityRenderer();
		tableRoute.getColumnModel().getColumn(2).setCellRenderer(cityRender);

		panelComputedRouteNorth.add(new JScrollPane(tableRoute));

		toolbarMap = new JToolBar();
		toolbarMap.setPreferredSize(new Dimension(200, 20));
		panelComputedRoute.add(toolbarMap, BorderLayout.PAGE_END);

		btnShowMap = new JButton();
		btnShowMap.setPreferredSize(new Dimension(80, 20));
		btnShowMap.setText(Constants.MAP);
		btnShowMap.addActionListener(this);
		toolbarMap.add(btnShowMap);

	}

	static Routeplaner getInstance() {
		if (Routeplaner.routeplaner == null) {
			routeplaner = new Routeplaner();
			return routeplaner;
		}
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		/* database */
		if (o.equals(databaseBox)) {
			switch (databaseBox.getSelectedIndex()) {
			// Connect
			case 0:
				if (database == null) {
					database = new DatabaseLogic();
					database.connect();
					try {
						OverViewLogic.createTableFlights(database.getConnection());
					} catch (SQLException e1) {
						System.err.println("was not able to create table " + Constants.OVERVIEW);
						e1.printStackTrace();
					}
					statusBar.setText(new StringBuilder("Connected: " + database.getDbName()).toString());
					statusBar.setVisible(true);
					btnAccessData.setVisible(true);
					setConnected(true);
					if (currentView == Constants.CREATEFLIGHT) {
						btnSave.setVisible(true);
					}

					flightBox.removeActionListener(flightBox.getActionListeners()[0]);
					flightBox.setRenderer(new DefaultListCellRenderer());
					flightBox.addActionListener(new FlightBoxEnabledListener(flightBox, routeplaner));

				}
				break;
			// disconnect
			case 1:
				if (database != null) {
					database.disconnect();
					database = null;
					statusBar.setText(null);
					statusBar.setVisible(false);
					setConnected(false);
					btnAccessData.setVisible(false);
					btnSave.setVisible(false);
					setView(Constants.STANDART);

					flightBox.removeActionListener(flightBox.getActionListeners()[0]);
					flightBox.setRenderer(new ComboBoxRenderer(listSelectionModel));
					flightBox.addActionListener(
							new FlightBoxDisabledListener(listSelectionModel, flightBox, routeplaner));
				}
				break;
			default:
				// do nothing
			}
		}

		else if (o.equals(btnSave)) {
			if (Utils.isStringValid(flightNumber)) {
				try {
					database.createFlight(flightNumber);
					OverViewLogic.insertFlightNumber(flightNumber, database.getConnection());
					btnSave.setVisible(false);
					statusBar.setText(database.getDbName() + File.separator + flightNumber);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, String.format(Messages.MUST_NOT_BE_NULL, flightNumber));
			}
		}

		else if (o.equals(btnAccessData)) {
			List<Flight> overViewEntries = null;

			try {
				overViewEntries = OverViewLogic.getTableAsList(database.getConnection());
			} catch (SQLException e1) {
				System.err.println("was not able to get OVERVIEW as list");
				e1.printStackTrace();
			}
			overView = OverView.getInstance(overViewEntries, this);
			panelAdresse.addMouseListener(new RoutePlanerMouseListener(overView));
			overView.showFrame();
		}

		else if (o.equals(btnSubmitFlightToDrop)) {
			String flightToDrop = txtDropFlight.getText();
			if (flightToDrop != "") {
				try {
					if (QueryHelper.checkIfTableExists(flightToDrop, database.getConnection().getConnection())) {

						String userChoice = JOptionPane.showInputDialog(Routeplaner.this,
								"Drop table " + flightToDrop + "?", JOptionPane.YES_NO_CANCEL_OPTION);
						if (userChoice != null) {
							try {
								QueryHelper.dropTable(flightToDrop, database.getConnection().getConnection());
								if (flightToDrop == flightNumber) {
									flightNumber = null;
									statusBar.setText(database.getDbName());
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					} else {
						JOptionPane.showInputDialog(Routeplaner.this, "Flight" + flightToDrop + " does not exist",
								JOptionPane.CLOSED_OPTION);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		else if (o.equals(btnSubmitFlightToSelect)) {
			String flightToSelect = txtSelectFlight.getText().trim();
			try {
				if (QueryHelper.checkIfTableExists(flightToSelect, database.getConnection().getConnection())) {
					flightNumber = flightToSelect;
					statusBar.setText(statusBar.getText() + File.separator + flightNumber);
					btnSubmitFlightToSelect.setVisible(false);
					List<GpsCoordinate> response = database.getFlightAsList(flightToSelect);
					if (!modelTargets.isEmpty()) {
						modelTargets.clear();
					}
					Utils.fillModel(response, modelTargets, false);

				} else {
					JOptionPane.showInputDialog(Routeplaner.this, "Flight " + flightToSelect + " does not exist",
							JOptionPane.CLOSED_OPTION);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		else if (o.equals(btnSubmitFlightNumber)) {
			flightNumber = txtNewFlight.getText();
			btnSubmitFlightNumber.setVisible(false);
		}

		else if (o.equals(btnDeleteRow)) {
			int[] arrayOfSelectedRows = tableTargets.getSelectedRows();

			/* remove from database */
			if (isConnected() == true) {
				for (int row = 0; row < arrayOfSelectedRows.length; row++) {
					Object object = modelTargets.getValueAt(arrayOfSelectedRows[row], 0);
					if (object != null) {
						int id = Integer.valueOf(String.valueOf(object).trim());
						database.deleteTarget(flightNumber, id);
					}
				}
			}

			/* remove from master */
			if (arrayOfSelectedRows[0] < master.size()) {
				for (int i = 0; i < arrayOfSelectedRows.length; i++) {
					master.remove(arrayOfSelectedRows[0]);
				}
			}

			/* remove from model */
			modelTargets.deleteRow(arrayOfSelectedRows);
			modelTargets.revalidate();
		}

		else if (o.equals(btnMyLocation)) {
			AddressDialog.getInstance(flightNumber, isConnected(), this);
		}

		else if (o.equals(btnShowMap)) {
			FrameMap.getInstance(computedRoute);
		}

	}

	@Override
	public void changedUpdate(DocumentEvent de) {
	}

	@Override
	public void insertUpdate(DocumentEvent de) {

		if (!txtdatabase.getText().equals("")) {
			if (iscreated == true) {
				btnCreateTable.setVisible(true);
			}

			if (isdropped == true) {
				btnDropTable.setVisible(true);
			}
		}

	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		if (txtdatabase.getText().equals("")) {
			if (iscreated == true) {
				btnCreateTable.setVisible(false);
			}
			if (isdropped == true) {
				btnDropTable.setVisible(false);
			}
		}

	}

	/**
	 * To set the view:
	 * 
	 * @param inserttarget
	 */
	public void setView(String view) {
		if (view != null) {
			switch (view) {
			case Constants.CREATEFLIGHT:
				/** management of view "CREATEFLIGHT:" */
				lblFlight.setVisible(true);
				txtNewFlight.setVisible(true);
				txtNewFlight.requestFocus();
				if (isConnected()) {
					btnSave.setVisible(true);
				}

				/** management of view "INSERTTARGET:" */
				Utils.setVisibilityOfLabels(false, lblstreet, lblcity, lblcountry);
				Utils.setVisibilityOfJTextFields(false, txtStreet, txtCity, txtCountry);

				/** management of view "DROPFLIGHT" */
				txtDropFlight.setVisible(false);
				btnSubmitFlightToDrop.setVisible(false);

				/** management of view "SELECTFLIGHT" */
				txtSelectFlight.setVisible(false);
				btnSubmitFlightToSelect.setVisible(false);
				break;
			case Constants.INSERTTARGET:
				/** management of view "CREATEFLIGHT:" */
				lblFlight.setVisible(false);
				txtNewFlight.setVisible(false);
				btnSubmitFlightNumber.setVisible(false);
				btnSave.setVisible(false);

				/** management of view "INSERTTARGET:" */
				Utils.setVisibilityOfLabels(true, lblstreet, lblcity, lblcountry);
				Utils.setVisibilityOfJTextFields(true, txtStreet, txtCity, txtCountry);
				txtCity.requestFocus();

				/** management of view "DROPFLIGHT" */
				txtDropFlight.setVisible(false);
				btnSubmitFlightToDrop.setVisible(false);

				/** management of view "SELECTFLIGHT" */
				txtSelectFlight.setVisible(false);
				btnSubmitFlightToSelect.setVisible(false);
				break;

			case Constants.DROPFLIGHT:
				if (isConnected()) {
					/** management of view "CREATEFLIGHT:" */
					txtNewFlight.setVisible(false);
					btnSubmitFlightNumber.setVisible(false);
					btnSave.setVisible(false);
					/** management of view "INSERTTARGET:" */
					Utils.setVisibilityOfLabels(false, lblstreet, lblcity, lblcountry);
					Utils.setVisibilityOfJTextFields(false, txtStreet, txtCity, txtCountry);
					/** management of view "DROPFLIGHT" */
					lblFlight.setVisible(true);
					txtDropFlight.setVisible(true);
					txtDropFlight.requestFocus();
					/** management of view "SELECTFLIGHT" */
					txtSelectFlight.setVisible(false);
					btnSubmitFlightToSelect.setVisible(false);
					break;
				}
			case Constants.SELECTFLIGHT:
				if (isConnected()) {
					/** management of view "CREATEFLIGHT:" */
					txtNewFlight.setVisible(false);
					btnSubmitFlightNumber.setVisible(false);
					btnSave.setVisible(false);

					/** management of view "INSERTTARGET:" */
					Utils.setVisibilityOfLabels(false, lblstreet, lblcity, lblcountry);
					Utils.setVisibilityOfJTextFields(false, txtStreet, txtCity, txtCountry);

					/** management of view "DROPFLIGHT" */
					txtDropFlight.setVisible(false);
					btnSubmitFlightToDrop.setVisible(false);

					/** management of view "SELECTFLIGHT" */
					lblFlight.setVisible(true);
					txtSelectFlight.setVisible(true);
					txtSelectFlight.requestFocus();
				}
				break;

			case Constants.STANDART:
				flightNumber = null;
				break;

			default:
				// do nothing;
			}
		}
	}

	public void reactOnConfirmAdress() throws JSONException {
		int counter = master.size();
		counter++;

		if (txtCity.getText() != "")
			try {
				{
					GpsCoordinate gps = null;

					StringBuilder builder = new StringBuilder();
					builder.append(txtStreet.getText());
					builder.append(txtCity.getText());
					builder.append(txtCountry.getText());

					/** to request the coordinates */
					try {
						gps = GPS.requestGPS(Utils.replaceUnusableChars(builder.toString()));
					} catch (MalformedURLException e2) {
						e2.printStackTrace();
					} catch (JDOMException e1) {
						e1.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (gps != null) {
						/** saving the address */
						gps.setId(counter);
						gps.setStreet(txtStreet.getText());
						gps.setCity(txtCity.getText());
						gps.setCountry(txtCountry.getText());

						if (!master.contains(gps)) {

							master.add(gps);
							modelTargets.addDataRow(new AddressVector(String.valueOf(counter), txtStreet.getText(),
									txtCity.getText(), txtCountry.getText(), String.valueOf(gps.getLongitude()),
									String.valueOf(gps.getLatitude())));
							modelTargets.revalidate();

							if (isConnected() && (flightNumber != null)) {
								try {
									database.insertIntoFlight(flightNumber, gps);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}

						}
					}
					Utils.clearTextFields(txtStreet, txtCity, txtCountry);
					txtCity.requestFocus();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	public void reactOnOptimized(int locX, int locY) {
		if (startGps != null) {
			new ConcreteCommand(this, new FindOptimized(), startGps, master, locX, locY).execute();

		}

	}

	public void reactOnNext(int locX, int locY) {
		if (startGps != null) {
			new ConcreteCommand(this, new FindNext(), startGps, master, locX, locY).execute();
		}
	}

	public void reactOnFarest(int locX, int locY) {
		if (startGps != null) {
			new ConcreteCommand(this, new FindFarest(), startGps, master, locX, locY).execute();
		}
	}

	public void reactOnNearestThenFarest(int locX, int locY) {
		if (startGps != null) {
			new ConcreteCommand(this, new FindNearestThenFarest(), startGps, master, locX, locY).execute();
		}

	}

	public void reactOnFarestThenNearest(int locX, int locY) {
		if (startGps != null) {
			new ConcreteCommand(this, new FindFarestThenNearest(), startGps, master, locX, locY).execute();
		}

	}

	public void reactOnRandom(int locX, int locY) {
		if (startGps != null) {
			new ConcreteCommand(this, new FindRandom(), startGps, master, locX, locY).execute();
		}

	}

	public void check(List<GpsCoordinate> receiving) {
		modelRoute.clear();
		Utils.fillModel(receiving, modelRoute, true);
		cityRender.setData(receiving);
		computedRoute = receiving;
		GpsCoordinate targetGps = computedRoute.get(computedRoute.size() - 1);
		if (isConnected) {
			try {
				OverViewLogic.insertTargetLocation(flightNumber, targetGps.getCity(), database.getConnection());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** getters&setters */
	public String getFlightNumber() {
		return flightNumber;
	}

	public DatabaseLogic getDatabase() {
		return database;
	}

	public JLabel getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(JLabel statusBar) {
		this.statusBar = statusBar;
	}

	public ArrayList<GpsCoordinate> getMaster() {
		return master;
	}

	public void setMaster(ArrayList<GpsCoordinate> master) {
		this.master = master;
	}

	public RoutePlanerLocationListener getRoutePlanerLocationListener() {
		return routePlanerLocationListener;
	}

	public void setRoutePlanerLocationListener(RoutePlanerLocationListener routePlanerLocationListener) {
		this.routePlanerLocationListener = routePlanerLocationListener;
	}

	public CommonModel getModelTargets() {
		return modelTargets;
	}

	public void setModelTargets(CommonModel modelTargets) {
		this.modelTargets = modelTargets;
	}

	public JTable getTableTargets() {
		return tableTargets;
	}

	public void setTableTargets(JTable tableTargets) {
		this.tableTargets = tableTargets;
	}

	public TargetsContextMenu getTargetContextMenu() {
		return targetContextMenu;
	}

	public void setTargetContextMenu(TargetsContextMenu targetContextMenu) {
		this.targetContextMenu = targetContextMenu;
	}

	public RoutePlanerWindowListener getRoutePlanerWindowListener() {
		return routePlanerWindowListener;
	}

	public void setRoutePlanerWindowListener(RoutePlanerWindowListener routePlanerWindowListener) {
		this.routePlanerWindowListener = routePlanerWindowListener;
	}

	public OverView getOverView() {
		return overView;
	}

	public void setOverView(OverView overView) {
		this.overView = overView;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public String getCurrentView() {
		return currentView;
	}

	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}

	public GpsCoordinate getStartGps() {
		return startGps;
	}

	public void setStartGps(GpsCoordinate startGps) {
		this.startGps = startGps;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

}
