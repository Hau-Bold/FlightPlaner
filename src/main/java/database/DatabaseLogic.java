package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import QueryHelper.QueryHelper.QueryHelper;
import Routenplaner.Constants;
import gps_coordinates.GpsCoordinate;

/**
 * A Class,that contains the used standard queries like INSERT, UPDATE,
 * DROP,CREATE
 *
 */
public class DatabaseLogic {
	private static DBConnection connection;
	private final String WORKING_DIRECTORY = System.getProperty("user.home.workspace");
	private static String DbName = "WWF";
	
	public static String getDbName() {
		return DbName;
	}

	public void setDbNAME(String dbName) {
		DbName = dbName;
	}

	public DBConnection getConnection() {
		return connection;
	}

	/**
	 * to connect
	 */
	public void connect() {
		connection = new DBConnection(WORKING_DIRECTORY + DbName);
	}

	/**
	 * to disconnect
	 */
	public void disconnect() {
		try {
			if (connection != null) {
				connection.getConnection().close();
			}
		} catch (SQLException e) {
			System.err.println("DATABASE: not able to disconnect");
			e.printStackTrace();
		}

	}

	/**
	 * to create a new flight
	 * 
	 * @param flightNumber
	 *            - the flightNumber
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public void createFlight(String flightNumber) throws SQLException {

		HashMap<String,String> map = new HashMap<String,String>();
		StringBuilder columnConstraint = new StringBuilder();
		
		columnConstraint.append(QueryHelper.INTEGER + " ");
		columnConstraint.append(QueryHelper.PRIMARYKEY + " ");
		columnConstraint.append(QueryHelper.AUTOINCREMENT);
		
		map.put(Constants.ID, columnConstraint.toString());
		columnConstraint.setLength(0);
		
		columnConstraint.append(QueryHelper.STRINGDEFAULTEMPTYSTRING + " ");
		
		map.put(Constants.STREET, columnConstraint.toString());
		map.put(Constants.CITY, columnConstraint.toString());
		map.put(Constants.COUNTRY, columnConstraint.toString());
		columnConstraint.setLength(0);
		
		columnConstraint.append(QueryHelper.DOUBLEDEFAULTNULL + " ");
		
		map.put(Constants.LONGITUDE, columnConstraint.toString());
		map.put(Constants.LATITUDE, columnConstraint.toString());
		
		QueryHelper.createTable(flightNumber, map, true, connection.getConnection());
	}

	public void deleteTarget(String name_Table, int row) {
		PreparedStatement preparedstatement = null;

		try {
			preparedstatement = connection.getConnection()
					.prepareStatement("DELETE FROM " + name_Table + " WHERE ID = ?;");
			preparedstatement.setInt(1, row);
			preparedstatement.executeUpdate();
			preparedstatement.close();
		} catch (SQLException e) {
			System.out.println("DELETING IMPOSSIBLE");
			e.printStackTrace();
		}
	}

	
	/**
	 * yields a flight from the database as lis
	 * 
	 * @param flightToSelect
	 *            - the flight to select
	 * @return list of targets belonging to the specific flight
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public ArrayList<GpsCoordinate> getFlightAsList(String flightToSelect) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<GpsCoordinate> response = new ArrayList<GpsCoordinate>();
		String sql = "SELECT * FROM " + flightToSelect + ";";
		try {
			statement = connection.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println(String.format("not able to execute query: %s", sql));
			e.printStackTrace();
		}
		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			String street = resultSet.getString(2);
			String city = resultSet.getString(3);
			String country = resultSet.getString(4);
			double longitude = resultSet.getDouble(5);
			double latitude = resultSet.getDouble(6);

			response.add(new GpsCoordinate(id, street, city, country, longitude, latitude));
		}
		resultSet.close();
		statement.close();
		return response;

	}

	/**
	 * to insert a gps into a flight
	 * 
	 * @param flightNumber
	 *            - the number of the flight
	 * @param gps
	 *            - the gps
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public void insertIntoFlight(String flightNumber, GpsCoordinate gps) throws SQLException {
		PreparedStatement preparedStatement = null;
		String street = gps.getStreet();
		String city = gps.getCity();
		String country = gps.getCountry();
		Double longitude = gps.getLongitude();
		Double latitude = gps.getLatitude();
		try {
			preparedStatement = connection.getConnection().prepareStatement(" INSERT INTO  " + flightNumber
					+ "(ID, STREET, CITY, COUNTRY,LONGITUDE,LATITUDE)" + "values (?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(2, street);
			preparedStatement.setString(3, city);
			preparedStatement.setString(4, country);
			preparedStatement.setDouble(5, longitude);
			preparedStatement.setDouble(6, latitude);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			preparedStatement.close();
			e.printStackTrace();
		}
	}

}
