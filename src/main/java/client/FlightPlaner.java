package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Routenplaner.Fonts;
import Routenplaner.IconButton;
import listeners.ListenerForEmptyFields;
import listeners.RoutePlanerMouseListener;
import render.CityRenderer;
import routePlanning.Constants.Constants;
import routePlanning.Contract.IOpenStreetMapService;
import routePlanning.Contract.IOptimizationService;
import routePlanning.Impl.GPSCoordinate;
import routePlanning.Impl.RoutePlanningHelper;
import routePlanning.overview.Flight;
import routeSaving.database.RoutePlanningDataStorageService;
import spring.DomainLayerSpringContext;
import tablemodel.CommonModel;
import view.Impl.HtmlExecutor;
import widgets.animation.FrameMap;
import widgets.contextMenu.TargetsContextMenu;
import widgets.flightsOverview.FlightsOverview;
import widgets.progression.InfiniteProgress;

@SuppressWarnings("serial")
public class FlightPlaner extends JDialog implements ActionListener, DocumentListener {

	private static String myDirectory;
	private static FlightPlaner myInstance = null;

	private IOptimizationService myOptimizationService;
	private IOpenStreetMapService myOpenStreetMapService;
	private RoutePlanningDataStorageService routePlanningDataStorageService;

	private static WebDriver webdriver;

	private final int WIDTH = 600;// TODO remove
	private final int HEIGHT = 300;// TODO remove

	private JTabbedPane tabbedPane;

	private JPanel panelAdresse;
	private JComboBox<String> databaseBox;
	// TOO continue
	private JLabel lblFlight, lblstreet, lblcity, lblcountry;
	private JTextField txtNewFlight, txtStreet, txtCity, txtCountry;

	private JLabel statusBar;

	/**
	 * to check the connection
	 */
	private boolean isConnected = false;
	private boolean iscreated = false;
	private boolean isdropped = false;

	/*
	 * to save the name of the table in the routePlanningDataStorageService
	 */
	private JTextField txtdatabase;
	private JLabel lblTABLE;
	private JButton btnCreateTable, btnDropTable;

	private ArrayList<GPSCoordinate> gpsCoordinates = new ArrayList<GPSCoordinate>();

	/**
	 * panel,model and table for addresses
	 */
	private CommonModel modelRoute;
	private JTable tableTargets, tableRoute;

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
	private List<GPSCoordinate> computedRoute;

	private IconButton btnSubmitFlightNumber, btnSubmitFlightToSelect, btnSave, btnAccessData, btnSubmitFlightToDrop,
			myButtonToRequestGPS;
	public static String flightNumber;

	private JTextField txtDropFlight, txtSelectFlight;

	// to set the view
	private String currentView;

	private GPSCoordinate startGps = null;
	private CityRenderer cityRender;
	private TargetsContextMenu targetContextMenu = null;

	public FlightsOverview myFlightsOverview = null;
	private String myPathToImageFolder;
	private String myStreet;
	private String myCity;
	private String myCountry;
	private HtmlExecutor myHtmlExecutor;

	public static FlightPlaner getInstance(String directory) {
		if (myInstance == null) {
			myDirectory = directory;
			myInstance = new FlightPlaner();
		}

		return myInstance;
	}

	public static FlightPlaner getInstance() {

		if (myInstance == null) {
			throw new IllegalAccessError();
		}

		return myInstance;
	}

	// ctor
	private FlightPlaner() {

		initSpringAndServices();

		initWebdriver();
		initLookAndFeel();
		initComponents();
	}

	private void initSpringAndServices() {
		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext(myDirectory);
		myFlightsOverview = springContext.GetFlightsOverview();
		myOpenStreetMapService = springContext.GetOpenStreetMapService();
		routePlanningDataStorageService = springContext.GetRoutePlanningDataStorageService();
	}

	private static void initLookAndFeel() {
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
	}

	private static void initWebdriver() {

		System.setProperty("webdriver.gecko.driver",
				myDirectory + File.separator + Constants.BIN + File.separator + Constants.GECKODRIVER);

		webdriver = new FirefoxDriver();

		String pathOfHtmlPage = myDirectory + File.separator + Constants.ASSETS + File.separator
				+ Constants.FlightPlanerHtml;

		webdriver.get("file:///" + pathOfHtmlPage);
		// webdriver.manage().window().maximize();
		webdriver.manage().window().fullscreen();
	}

	private void initComponents() {

		myPathToImageFolder = myDirectory + File.separator + Constants.ASSETS + File.separator + Constants.IMAGE;

		if (!new File(myPathToImageFolder).exists()) {
			throw new IllegalArgumentException(String.format("path %s does not exists", myPathToImageFolder));
		}

		setTitle(Constants.FLIGHTPLANER);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dimension.width / 2, dimension.height / 2 - HEIGHT, WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// adding tabbedPane
		tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane);

		// panel for address and routePlanningDataStorageService
		panelAdresse = new JPanel();
		panelAdresse.setLayout(null);
		tabbedPane.addTab("Datenbank", panelAdresse);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_0);

		// the combobox for the routePlanningDataStorageService:
		String optionsForDataBase[] = { Constants.CONNECT, Constants.DISCONNECT };
		databaseBox = new JComboBox<String>(optionsForDataBase);
		databaseBox.setBounds(0, 0, 80, 20);
		databaseBox.setBorder(BorderFactory.createRaisedBevelBorder());
		databaseBox.setFont(Fonts.MainFont);
		databaseBox.addActionListener(this);
		databaseBox.setEnabled(true);

		btnSave = new IconButton(myPathToImageFolder, "saveIcon.png", 180, 0);
		btnSave.addActionListener(this);

		btnAccessData = new IconButton(myPathToImageFolder, "accessDataIcon.jpg", 200, 0);
		btnAccessData.addActionListener(this);

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

		btnSubmitFlightNumber = new IconButton(myPathToImageFolder, "confirmIcon.png", 200, 50);
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
		btnSubmitFlightToDrop = new IconButton(myPathToImageFolder, "deleteIcon.png", 200, 50);
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
		btnSubmitFlightToSelect = new IconButton(myPathToImageFolder, "Confirm.png", 200, 50);
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
		panelAdresse.add(lblstreet);

		txtStreet = new JTextField();
		txtStreet.setBounds(80, 50, 100, 20);
		txtStreet.setFocusable(true);
		txtStreet.setBackground(Color.GREEN);
		panelAdresse.add(txtStreet);

		// city
		lblcity = new JLabel("City:");
		lblcity.setOpaque(true);
		lblcity.setBounds(200, 50, 50, 20);
		lblcity.setFont(Fonts.MainFont);
		panelAdresse.add(lblcity);

		txtCity = new JTextField();
		txtCity.setBounds(250, 50, 100, 20);
		txtCity.setFocusable(true);
		txtCity.setBackground(Color.GREEN);
		txtCity.getDocument().addDocumentListener(new ListenerForEmptyFields(txtCity, myButtonToRequestGPS));
		panelAdresse.add(txtCity);

		// country:
		lblcountry = new JLabel("Country:");
		lblcountry.setOpaque(true);
		lblcountry.setBounds(370, 50, 50, 20);
		lblcountry.setFont(Fonts.MainFont);
		panelAdresse.add(lblcountry);

		txtCountry = new JTextField();
		txtCountry.setBounds(430, 50, 100, 20);
		txtCountry.setFocusable(true);
		txtCountry.setBackground(Color.GREEN);
		panelAdresse.add(txtCountry);

		myButtonToRequestGPS = new IconButton(myPathToImageFolder, "Confirm.png", 540, 50);
		myButtonToRequestGPS.setVisible(true);
		myButtonToRequestGPS.addActionListener(this);
		panelAdresse.add(myButtonToRequestGPS);

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
		 * Initialisierung des Buttons für TABLE
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

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		panelComputedRoute = new JPanel(new BorderLayout());

		tabbedPane.add(Constants.Route, panelComputedRoute);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

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

		myHtmlExecutor = new HtmlExecutor(myDirectory, Constants.FLIGHTPLANER, Constants.HEADER_OF_PAGE,
				Calendar.getInstance().getTime());
		try {
			myHtmlExecutor.write(gpsCoordinates);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// initWebdriver();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		/* routePlanningDataStorageService */
		if (o.equals(databaseBox)) {
			switch (databaseBox.getSelectedIndex()) {
			// Connect
			case 0:
				routePlanningDataStorageService.setDirectory(myDirectory);

				try {
					routePlanningDataStorageService.connect();
					RoutePlanningDataStorageService.createTableFlights(routePlanningDataStorageService.getConnection());
				} catch (SQLException e1) {
					System.err.println("was not able to create table " + Constants.OVERVIEW);
					e1.printStackTrace();
				}
				statusBar.setText(new StringBuilder("Connected: " + Constants.DataBaseName).toString());
				statusBar.setVisible(true);
				btnAccessData.setVisible(true);
				setConnected(true);
				if (currentView == Constants.CREATEFLIGHT) {
					btnSave.setVisible(true);
				}

				break;
			// disconnect
			case 1:
				if (routePlanningDataStorageService != null) {
					routePlanningDataStorageService.disconnect();
					routePlanningDataStorageService = null;
					statusBar.setText(null);
					statusBar.setVisible(false);
					setConnected(false);
					btnAccessData.setVisible(false);
					btnSave.setVisible(false);

				}
				break;
			default:
				throw new IllegalArgumentException();
			}
		}

		else if (o.equals(btnSave)) {
			if (!RoutePlanningHelper.nullOrEmpty(flightNumber)) {
				try {
					routePlanningDataStorageService.createNewFlight(flightNumber);
					RoutePlanningDataStorageService.insertFlightNumber(flightNumber,
							routePlanningDataStorageService.getConnection());
					btnSave.setVisible(false);
					statusBar.setText(Constants.DataBaseName + File.separator + flightNumber);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, String.format("%s", flightNumber));
			}
		}

		else if (o.equals(btnAccessData)) {
			List<Flight> overViewEntries = null;

			try {
				overViewEntries = RoutePlanningDataStorageService
						.getTableAsList(routePlanningDataStorageService.getConnection());
			} catch (SQLException e1) {
				System.err.println("was not able to get OVERVIEW as list");
				e1.printStackTrace();
			}

			myFlightsOverview.initComponent(overViewEntries);
			panelAdresse.addMouseListener(new RoutePlanerMouseListener());
			myFlightsOverview.showFrame();
		}

		else if (o.equals(myButtonToRequestGPS)) {
			myStreet = txtStreet.getText();
			myCity = txtCity.getText();
			myCountry = txtCountry.getText();
			requestGPS();
			writeHtmlPage();
			webdriver.navigate().refresh();
		}

		else if (o.equals(btnSubmitFlightToDrop)) {
			String flightToDrop = txtDropFlight.getText();
			if (flightToDrop != "") {
				try {
					if (RoutePlanningDataStorageService.checkIfTableExists(flightToDrop,
							routePlanningDataStorageService.getConnection().getConnection())) {

						String userChoice = JOptionPane.showInputDialog(myInstance, "Drop table " + flightToDrop + "?",
								JOptionPane.YES_NO_CANCEL_OPTION);
						if (userChoice != null) {
							try {
								RoutePlanningDataStorageService.dropTable(flightToDrop,
										routePlanningDataStorageService.getConnection().getConnection());
								if (flightToDrop == flightNumber) {
									flightNumber = null;
									statusBar.setText(Constants.DataBaseName);
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					} else {
						JOptionPane.showInputDialog(FlightPlaner.this, "Flight" + flightToDrop + " does not exist",
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
				if (RoutePlanningDataStorageService.checkIfTableExists(flightToSelect,
						routePlanningDataStorageService.getConnection().getConnection())) {
					flightNumber = flightToSelect;
					statusBar.setText(statusBar.getText() + File.separator + flightNumber);
					btnSubmitFlightToSelect.setVisible(false);
					List<GPSCoordinate> response = routePlanningDataStorageService.getFlightAsList(flightToSelect);
				}

				else {
					JOptionPane.showInputDialog(FlightPlaner.this, "Flight " + flightToSelect + " does not exist",
							JOptionPane.CLOSED_OPTION);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		else if (o.equals(btnSubmitFlightNumber))

		{
			flightNumber = txtNewFlight.getText();
			btnSubmitFlightNumber.setVisible(false);
		}

		else if (o.equals(btnMyLocation)) {

			// TODO should by your adress by default, nothing more

			// AddressDialog.getInstance(flightNumber, isConnected());
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

	private void requestGPS() {
		int counter = gpsCoordinates.size();
		counter++;

		if (myCity.isEmpty()) {
			return;
		}

		try {
			GPSCoordinate gps = null;

			try {
				gps = myOpenStreetMapService.getCoordinates(myStreet, myCity, myCountry);
				// TODO when is null reference recieved?
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			}

			if (gps != null && !gpsCoordinates.contains(gps)) {

				gps.setId(counter);

				gpsCoordinates.add(gps);

				if (isConnected() && (flightNumber != null)) {
					try {
						routePlanningDataStorageService.insertIntoFlight(flightNumber, gps);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			RoutePlanningHelper.clearTextFields(txtStreet, txtCity, txtCountry);
			txtCity.requestFocus();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeHtmlPage() {
		try {
			myHtmlExecutor.write(gpsCoordinates);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void executeOptimization(int locX, int locY) {
		if (startGps != null) {
			new InfiniteProgress(locX, locY).execute();
		} else {
			throw new IllegalArgumentException("start is not set up!");
		}
	}

	public void check() {

		modelRoute.clear();

		computedRoute = myOptimizationService.compute(startGps, gpsCoordinates);
		RoutePlanningHelper.fillModel(computedRoute, modelRoute, true);
		cityRender.setData(computedRoute);
		GPSCoordinate targetGps = computedRoute.get(computedRoute.size() - 1);

		if (isConnected) {
			try {
				RoutePlanningDataStorageService.insertTargetLocation(flightNumber, targetGps.getCity(),
						routePlanningDataStorageService.getConnection());
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

	public RoutePlanningDataStorageService getRoutePlanningDataStorageService() {
		return routePlanningDataStorageService;
	}

	public JLabel getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(JLabel statusBar) {
		this.statusBar = statusBar;
	}

	public ArrayList<GPSCoordinate> getGpsCoordinates() {
		return gpsCoordinates;
	}

	public void setGpsCoordinates(ArrayList<GPSCoordinate> master) {
		this.gpsCoordinates = master;
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

	public FlightsOverview getMyFlightsOverview() {
		return myFlightsOverview;
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

	public GPSCoordinate getStartGps() {
		return startGps;
	}

	public void setStartGps(GPSCoordinate startGps) {
		this.startGps = startGps;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public void setOptimization(IOptimizationService optimization) {
		myOptimizationService = optimization;
	}

	public String getPathToImageFolder() {
		return myPathToImageFolder;
	}
}
