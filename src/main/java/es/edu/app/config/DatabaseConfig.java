package es.edu.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

	private static Connection connection = null;

	private DatabaseConfig() {
		// nothing to do here
	}

	public static Connection getConnection() throws Exception {

		if (connection == null) {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		}
		return connection;

	}

	public static void closeConnection() throws SQLException {

		if (connection == null) {
			connection.close();
		}
	}
}
