package routeSaving.database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import routePlanning.Constants.Constants;
import routePlanning.Impl.GPS;
import routePlanning.overview.Flight;

public class DatabaseLogic {
	private static DataBaseConnection connection;
	private String myDirectory;

	// ctor
	public DatabaseLogic(String directory) {
		myDirectory = directory;
	}

	public DataBaseConnection getConnection() {
		return connection;
	}

	public void connect() throws SQLException {

		if (connection != null) {
			connection.getConnection().close();
			connection = null;
		}
		connection = new DataBaseConnection(
				myDirectory + File.separator + Constants.BIN + File.separator + Constants.DataBaseName);
		connection.getConnection().setAutoCommit(Boolean.FALSE);
	}

	public static void disconnect() {

		try {
			if (connection != null) {
				connection.getConnection().close();
			}
		} catch (SQLException e) {
			System.out.println("Database: Status: Not able to disconnect");
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

		columnConstraint.append(Constants.INTEGER + " ");
		columnConstraint.append(Constants.PRIMARYKEY + " ");
		columnConstraint.append(Constants.AUTOINCREMENT);

		map.put(Constants.ID, columnConstraint.toString());
		columnConstraint.setLength(0);

		columnConstraint.append(Constants.STRINGDEFAULTEMPTYSTRING + " ");

		map.put(Constants.STREET, columnConstraint.toString());
		map.put(Constants.CITY, columnConstraint.toString());
		map.put(Constants.COUNTRY, columnConstraint.toString());
		columnConstraint.setLength(0);

		columnConstraint.append(Constants.DOUBLEDEFAULTNULL + " ");

		map.put(Constants.LONGITUDE, columnConstraint.toString());
		map.put(Constants.LATITUDE, columnConstraint.toString());

		createTable(flightNumber, map, true, connection.getConnection());
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

	public static void createTableFlights(DataBaseConnection connection) throws SQLException {

		Map<String, String> queryMap = new HashMap<String, String>();

		StringBuilder columnConstraints = new StringBuilder();
		columnConstraints.append(Constants.INTEGER + " ");
		columnConstraints.append(Constants.PRIMARYKEY + " ");
		columnConstraints.append(Constants.AUTOINCREMENT);

		queryMap.put(Constants.ID, columnConstraints.toString());

		columnConstraints.setLength(0);
		columnConstraints.append(Constants.STRINGDEFAULTEMPTYSTRING);

		queryMap.put(Constants.FLIGHTNUMBER, columnConstraints.toString());
		queryMap.put(Constants.START, columnConstraints.toString());
		queryMap.put(Constants.TARGET, columnConstraints.toString());

		columnConstraints.setLength(0);
		columnConstraints.append(Constants.DOUBLEDEFAULTNULL);

		queryMap.put(Constants.LATITUDE, columnConstraints.toString());
		queryMap.put(Constants.LONGITUDE, columnConstraints.toString());

		createTable(Constants.FLIGHTNUMBER, queryMap, true, connection.getConnection());

	}

	/**
	 * to insert a flightnumber into FLIGHTNUMBERS.
	 * 
	 * @param flightNumber
	 *            - the flightnumber
	 * @param connection
	 *            - the Connection
	 */
	public static void insertFlightNumber(String flightNumber, DataBaseConnection connection) {
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
	public static void insertStartLocation(String flightNumber, GPS startGps, DataBaseConnection connection)
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
	public static void insertTargetLocation(String flightNumber, String targetLocation, DataBaseConnection connection)
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
	public static List<Flight> getTableAsList(DataBaseConnection connection) throws SQLException {

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
	public static GPS getStartGps(String flightNumber, DataBaseConnection connection) throws SQLException {
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

	/**
	 * to create a new table
	 * 
	 * @param flightNumber
	 *            - the flightNumber
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static void createTable(String nameOfTable, Map<String, String> queryMap, boolean ifNotExists,
			Connection connection) throws SQLException {

		StringBuilder sqlBuilder = new StringBuilder();

		sqlBuilder.append(Constants.CREATE);
		sqlBuilder.append(" ");
		sqlBuilder.append(Constants.TABLE);
		sqlBuilder.append(" ");
		if (ifNotExists) {
			sqlBuilder.append(Constants.IFNOTEXISTS);
			sqlBuilder.append(" ");
		}
		sqlBuilder.append(nameOfTable);
		sqlBuilder.append(" ");
		sqlBuilder.append("(");

		Iterator<String> iter = queryMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			sqlBuilder.append(key + " ");
			sqlBuilder.append(queryMap.get(key));
			boolean isLastEntry = queryMap.keySet().size() == 1;
			if (!isLastEntry) {
				sqlBuilder.append(",");
			}
			iter.remove();
		}
		sqlBuilder.append(");");

		Statement statement = null;

		try {
			statement = connection.createStatement();
			statement.executeUpdate(sqlBuilder.toString());
			System.err.println(String.format("Table %s was created with query %s", nameOfTable, sqlBuilder.toString()));
			statement.close();

		} catch (Exception ex) {
			System.err.println(
					String.format("Was not able to create Table %s with query %s", nameOfTable, sqlBuilder.toString()));
			statement.close();
			ex.printStackTrace();
		}

	}

	/**
	 * to drop a table
	 * 
	 * @param nameOfTable
	 *            - the name of the table
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static void dropTable(String nameOfTable, Connection connection) throws SQLException {
		String sql = "DROP TABLE IF EXISTS " + nameOfTable;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(String.format("dropping table %s was impossible", nameOfTable));
			e.printStackTrace();
			statement.close();
		} finally {

		}

	}

	/**
	 * to check if a table exists
	 * 
	 * @param nameOfTable
	 *            - the name of the table
	 * @param connection
	 *            - the connection
	 * @return true or false
	 * @throws SQLException
	 *             - in case of technical error
	 */
	public static boolean checkIfTableExists(String nameOfTable, Connection connection) throws SQLException {
		String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name= '" + nameOfTable + "';";
		Statement statement = null;
		ResultSet resultset = null;
		boolean check = false;
		try {
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);
			check = resultset.next();
			resultset.close();
			statement.close();
		} catch (SQLException e) {
			System.err.println(String.format("not able to execute query: %s", sql));
			e.printStackTrace();
			resultset.close();
			statement.close();
		} finally {
			resultset.close();
			statement.close();
		}
		return check;
	}

}
