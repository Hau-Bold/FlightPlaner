package overview;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryHelper.QueryHelper.QueryHelper;
import Routenplaner.Constants;
import database.DBConnection;
import database.DatabaseLogic;
import gps_coordinates.GpsCoordinate;

public class OverViewLogic {

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
	public static void insertStartLocation(String flightNumber, GpsCoordinate startGps, DBConnection connection)
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
	public static GpsCoordinate getStartGps(String flightNumber, DBConnection connection) throws SQLException {
		String query = "SELECT * FROM " + Constants.FLIGHTS + " WHERE FLIGHTNUMBER = '" + flightNumber + "';";
		Statement statement = null;
		ResultSet resultSet = null;
		GpsCoordinate response = null;

		try {
			statement = connection.getConnection().createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String start = resultSet.getString(3);
				if (start != null) {
					String[] startSplitted = start.split(",");
					if (startSplitted.length == 3) {
						response = new GpsCoordinate(0, startSplitted[0], startSplitted[1], startSplitted[2],
								resultSet.getDouble(4), resultSet.getDouble(5));
					} else if (startSplitted.length == 1) {
						response = new GpsCoordinate(0, "", startSplitted[0], "", resultSet.getDouble(4),
								resultSet.getDouble(5));
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
