
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private Connection dbConnection;

	public Connection getConnection() {
		return dbConnection;
	}

	public DBConnection(String databaseConnectionPath) {
		// Lade den Treiber
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("Fehler beim Laden des JDBC-Treibers");
			e.printStackTrace();
		}

		innitDatabaseConnection(databaseConnectionPath);
	}

	private void innitDatabaseConnection(String databaseConnectionPath) {
		// Treiber holt Verbindung
		try {
			dbConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseConnectionPath);
		} catch (SQLException e) {
			System.out.println("DB Connection" + e.getMessage());
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {

				try {
					if (dbConnection != null && !dbConnection.isClosed()) {
						dbConnection.close();
						if (dbConnection.isClosed())
							System.out.println("database: Status disconnected");
					}

				} catch (Exception ex) {
					System.out.println("Error! " + ex.getMessage());
				}
			}
		});

	}

}
