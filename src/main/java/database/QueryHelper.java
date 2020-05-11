package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

public class QueryHelper {

	/** SQL **/
	public final static String CREATE = "CREATE";
	public final static String DROP = "DROP";
	public final static String INSERT = "INSERT";
	public final static String DELETE = "DELETE";
	public final static String UPDATE = "UPDATE";
	public final static String IFNOTEXISTS = "IF NOT EXISTS";

	final static String TABLE = "TABLE";

	/** DDL */
	public final static String DOUBLE = "DOUBLE";
	public final static String STRING = "STRING";
	public final static String INTEGER = "INTEGER";

	/** constrainst */
	public final static String DEFAULTNULL = "DEFAULT NULL";
	public final static String DEFAULTEMPTYSTRING = "DEFAULT ('')";
	public final static String PRIMARYKEY = "PRIMARY KEY";
	public final static String AUTOINCREMENT = "AUTOINCREMENT";

	/** Combined Constraints */
	public final static String STRINGDEFAULTEMPTYSTRING = "String DEFAULT (' ')";
	public final static String DOUBLEDEFAULTNULL = " DOUBLE DEFAULT NULL";

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

		sqlBuilder.append(CREATE);
		sqlBuilder.append(" ");
		sqlBuilder.append(TABLE);
		sqlBuilder.append(" ");
		if (ifNotExists) {
			sqlBuilder.append(IFNOTEXISTS);
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
