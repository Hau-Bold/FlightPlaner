
package routeSaving.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class DataBaseConnection {

	private Connection myConnection;

	public Connection getConnection() {
		return myConnection;
	}

	public DataBaseConnection(String databaseConnectionPath) {
		/** loading driver */
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("error while loading jdbc-driver");
			e.printStackTrace();
		}

		initDatabaseConnection(databaseConnectionPath);
	}

	/**
	 * establishes a connection
	 * 
	 * @param databaseConnectionPath
	 */
	private void initDatabaseConnection(String databaseConnectionPath) {
		try {

			SQLiteConfig config = new SQLiteConfig();

			/** to enable usage of foreign keys */
			// could be removed?
			config.enforceForeignKeys(true);

			myConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseConnectionPath, config.toProperties());
		} catch (SQLException e) {
			System.out.println("connection" + e.getMessage());
		}

		/** handling when program is terminated */
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					if (myConnection != null && !myConnection.isClosed()) {
						myConnection.close();
						if (myConnection.isClosed())
							System.out.println("Status(database): disconnected");
					}
				} catch (Exception ex) {
					System.out.println("Error! " + ex.getMessage());
				}
			}
		});
	}
}
