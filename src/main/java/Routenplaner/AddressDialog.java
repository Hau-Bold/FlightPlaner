package Routenplaner;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import client.FlightPlaner;
import listeners.ListenerForEmptyFields;
import routePlanning.Constants.Constants;
import routePlanning.Impl.GPS;
import routePlanning.Impl.RoutePlanningHelper;
import routeSaving.database.DatabaseLogic;
import spring.DomainLayerSpringContext;

/**
 * 
 * the class AddressDialog
 */
public class AddressDialog extends JFrame implements DocumentListener, ActionListener {

	private static final long serialVersionUID = 1L;
	static AddressDialog instance = null;
	private JLabel lblClientStreet, lblClientCity, lblClientCountry;
	private JTextField txtClientStreet, txtClientCity, txtClientCountry;
	private JButton btnConfirm;
	public String start;
	public String myFlightNumber;
	private boolean isConnected;
	private FlightPlaner myFlightPlaner;
	private RoutePlanningHelper myRoutePlanningHelper;

	private AddressDialog(String flightNumber, boolean isConnected) {

		DomainLayerSpringContext springContext = DomainLayerSpringContext.GetContext();
		myFlightPlaner = FlightPlaner.getInstance();

		myRoutePlanningHelper = new RoutePlanningHelper(springContext.GetOpenStreetMapService());

		myFlightNumber = flightNumber;
		this.isConnected = isConnected;
		initComponent();
		showFrame();
	}

	private void initComponent() {

		// Lege Daten des neuen Frame fest
		this.setTitle("YOUR ADRESS");
		this.setBounds(10, 10, 300, 250);
		this.setResizable(false);
		this.setLayout(null);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		lblClientStreet = new JLabel(Constants.STREET + ":");
		lblClientStreet.setBounds(20, 10, 60, 20);
		lblClientStreet.setOpaque(true);
		this.add(lblClientStreet);

		txtClientStreet = new JTextField();
		txtClientStreet.setBounds(90, 10, 100, 20);
		txtClientStreet.setBackground(Color.GREEN);
		this.add(txtClientStreet);

		lblClientCity = new JLabel(Constants.CITY + ":");
		lblClientCity.setBounds(20, 35, 40, 20);
		lblClientCity.setOpaque(true);
		this.add(lblClientCity);

		btnConfirm = new IconButton(myFlightPlaner.getPathToImageFolder(), "Confirm_icon.png", 190, 35);
		btnConfirm.setMnemonic(KeyEvent.VK_Q);
		btnConfirm.addActionListener(this);

		txtClientCity = new JTextField();
		txtClientCity.setBounds(90, 35, 100, 20);
		txtClientCity.setBackground(Color.GREEN);
		txtClientCity.getDocument().addDocumentListener(new ListenerForEmptyFields(txtClientCity, btnConfirm));
		this.add(txtClientCity);

		lblClientCountry = new JLabel(Constants.COUNTRY + ":");
		lblClientCountry.setBounds(20, 60, 70, 20);
		lblClientCountry.setOpaque(true);
		this.add(lblClientCountry);

		txtClientCountry = new JTextField();
		txtClientCountry.setBounds(90, 60, 100, 20);
		txtClientCountry.setBackground(Color.GREEN);
		this.add(txtClientCountry);

		this.add(btnConfirm);
	}

	public static AddressDialog getInstance(String flightNumber, boolean b) {
		if (instance == null) {
			return new AddressDialog(flightNumber, b);
		}
		return null;
	}

	public void showFrame() {
		this.setVisible(true);
	}

	@Override
	public void insertUpdate(DocumentEvent de) {
		if (!txtClientCity.getText().equals("")) {
			btnConfirm.setVisible(true);
		}

	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		if (txtClientCity.getText().equals("")) {
			btnConfirm.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnConfirm)) {

			StringBuilder builder = new StringBuilder();
			builder.append(txtClientStreet.getText());
			builder.append(txtClientCity.getText());
			builder.append(txtClientCountry.getText());

			start = myRoutePlanningHelper.replaceUnusableChars(builder.toString());
			GPS gpsOfStart = null;
			try {
				gpsOfStart = myRoutePlanningHelper.getGpsCoordinateToLocation(start, 0);
				myFlightPlaner.setStartGps(gpsOfStart);
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			if (isConnected) {
				/** the flightnumber is valid */
				try {
					DatabaseLogic.insertStartLocation(myFlightNumber, gpsOfStart,
							myFlightPlaner.getDatabase().getConnection());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			this.dispose();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
	}

	public String getStart() {
		return start;
	}

}
