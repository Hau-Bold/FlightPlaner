package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import routePlanningService.Constants.Constants;
import routePlanningService.Impl.GPS;
import routePlanningService.overview.Flight;

public class DatabaseLogic {
	private static DBConnection connection;
	private final String WORKING_DIRECTORY = System.getProperty("user.home.workspace");
	private static String DbName = "WWF";

	public static String getDbName() {
		return DbName;
	}

	public DBConnection getConnection() {
		return connection;
	}

	public void connect() {

		// TODO move to bin folder.....
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

		HashMap<String, String> map = new HashMap<String, String>();
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
	public ArrayList<GPS> getFlightAsList(String flightToSelect) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<GPS> response = new ArrayList<GPS>();
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

			response.add(new GPS(id, street, city, country, longitude, latitude));
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
	public void insertIntoFlight(String flightNumber, GPS gps) throws SQLException {
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

	public static void createTableFlights(DBConnection connection) throws SQLException {

		Map<String, String> queryMap = new HashMap<String, String>();

		StringBuilder columnConstraints = new StringBuilder();
		columnConstraints.append(QueryHelper.INTEGER + " ");
		columnConstraints.append(QueryHelper.PRIMARYKEY + " ");
		columnConstraints.append(QueryHelper.AUTOINCREMENT);

		queryMap.put(Constants.ID, columnConstraints.toString());

		columnConstraints.setLength(0);
		columnConstraints.append(QueryHelper.STRINGDEFAULTEMPTYSTRING);

		queryMap.put(Constants.FLIGHTNUMBER, columnConstraints.toString());
		queryMap.put(Constants.START, columnConstraints.toString());
		queryMap.put(Constants.TARGET, columnConstraints.toString());

		columnConstraints.setLength(0);
		columnConstraints.append(QueryHelper.DOUBLEDEFAULTNULL);

		queryMap.put(Constants.LATITUDE, columnConstraints.toString());
		queryMap.put(Constants.LONGITUDE, columnConstraints.toString());

		QueryHelper.createTable(Constants.FLIGHTNUMBER, queryMap, true, connection.getConnection());

	}

	/**
	 * to insert a flightnumber into FLIGHTNUMBERS.
	 * 
	 * @param flightNumber
	 *            - the flightnumber
	 * @param connection
	 *            - the Connection
	 */
	public static void insertFlightNumber(String flightNumber, DBConnection connection) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.getConnection()

					.prepareStatement(" INSERT INTO  " + Constants.FLIGHTS + "(ID, FLIGHTNUMBER,START,TARGET)"
							+ "values (?, ?, ?, ?)");
			preparedStatement.setString(2, flightNumber);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * to insert the START into FLIGHTNUMBERS.
	 * 
	 * @param flightNumber
	 *            - the flightnumber
	 * @param connection
	 *            - the Connection
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static void insertStartLocation(String flightNumber, GPS startGps, DBConnection connection)
			throws SQLException {
		String sql = "UPDATE " + Constants.FLIGHTS + " SET START=" + "'" + startGps.getCity() + "',"
				+ Constants.LONGITUDE + "=" + "'" + startGps.getLongitude() + "'," + Constants.LATITUDE + "=" + "'"
				+ startGps.getLatitude() + "'" + " WHERE FLIGHTNUMBER='" + flightNumber + "';";
		Statement statement = null;
		try {
			statement = connection.getConnection().createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			System.err.println(String.format("not able to execute query %s", sql));
			e.printStackTrace();
		} finally {
			statement.close();
		}

	}

	/**
	 * to insert the TARGET into FLIGHTNUMBERS.
	 * 
	 * @param flightNumber
	 *            - the flightnumber
	 * @param connection
	 *            - the Connection
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static void insertTargetLocation(String flightNumber, String targetLocation, DBConnection connection)
			throws SQLException {
		String sql = "UPDATE " + Constants.FLIGHTS + " SET TARGET= '" + targetLocation + "' WHERE FLIGHTNUMBER='"
				+ flightNumber + "';";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			System.err.println(String.format("not able to execute query %s", sql));
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}

	}

	/**
	 * yields a list of stored flights.
	 * 
	 * @param connection
	 *            the Connection
	 * @return
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static List<Flight> getTableAsList(DBConnection connection) throws SQLException {

		Statement statement = null;
		ResultSet resultSet = null;
		List<Flight> response = new ArrayList<Flight>();
		String query = "SELECT * FROM " + Constants.FLIGHTS + ";";
		try {
			statement = connection.getConnection().createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String flightNumber = resultSet.getString(2);
				String start = resultSet.getString(3);
				double longitude = resultSet.getDouble(4);
				Double latitude = resultSet.getDouble(5);
				String target = resultSet.getString(6);
				response.add(new Flight(flightNumber, start, longitude, latitude, target));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.err.println(String.format("not able to execute query: %s", query));
			e.printStackTrace();
			resultSet.close();
			statement.close();
		} finally {
			resultSet.close();
			statement.close();
		}
		return response;
	}

	/**
	 * to remove a concrete flight from the overview table
	 * 
	 * @param flightNumber
	 *            - the name of the flight
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static void removeFlight(String flightNumber, DatabaseLogic databaseLogic) throws SQLException {
		PreparedStatement preparedstatement = null;
		String query = "DELETE FROM " + Constants.FLIGHTS + " WHERE FLIGHTNUMBER = ?;";
		try {
			preparedstatement = databaseLogic.getConnection().getConnection().prepareStatement(query);
			preparedstatement.setString(1, flightNumber);
			preparedstatement.executeUpdate();
			preparedstatement.close();
		} catch (SQLException e) {
			System.err.println(String.format("not able to execute query: %s", query));
			e.printStackTrace();
		} finally {
			preparedstatement.close();
		}

	}

	/**
	 * yields the gps of the start
	 * 
	 * @param flightNumber
	 *            - the flightnumber
	 * @param connection
	 *            - the connection
	 * @return
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static GPS getStartGps(String flightNumber, DBConnection connection) throws SQLException {
		String query = "SELECT * FROM " + Constants.FLIGHTS + " WHERE FLIGHTNUMBER = '" + flightNumber + "';";
		Statement statement = null;
		ResultSet resultSet = null;
		GPS response = null;

		try {
			statement = connection.getConnection().createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String start = resultSet.getString(3);
				if (start != null) {
					String[] startSplitted = start.split(",");
					if (startSplitted.length == 3) {
						response = new GPS(0, startSplitted[0], startSplitted[1], startSplitted[2],
								resultSet.getDouble(4), resultSet.getDouble(5));
					} else if (startSplitted.length == 1) {
						response = new GPS(0, "", startSplitted[0], "", resultSet.getDouble(4), resultSet.getDouble(5));
					}
				}
			}
		} catch (SQLException e) {
			resultSet.close();
			statement.close();
			e.printStackTrace();
		} finally {
			resultSet.close();
			statement.close();
		}
		return response;
	}

}
