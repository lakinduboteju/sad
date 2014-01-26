package sad.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class will connect to a MYSQL database in the localhost and provides
 * java.sql.Connection to use in Gateway classes. This class helps gateway
 * classes to establish connections with database.
 * 
 * @author lakindu
 * 
 */
public class DatabaseConnector {
	// JDBC driver name and database URL
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/";

	// Database credentials
	private final String USER = "root";
	private final String PASS = "";

	private Connection conn = null; // holds connection to the database

	/**
	 * Constructs connection to the database
	 * 
	 * @param dbName
	 *            name of the database
	 */
	public DatabaseConnector(String dbName) {
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			// Open a connection
			conn = DriverManager.getConnection(DB_URL + dbName, USER, PASS);
		} catch (SQLException se) {
			System.out
					.println("Error: SQL exception when trying to connect to database and receive a connection.");
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out
					.println("Error: Cannot find driver (mysql-connector jar missing).");
			e.printStackTrace();
		}
	}

	/**
	 * Gets constructed connection
	 * 
	 * @return connection to the database
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Closes the database connection
	 */
	public void closeConnection() {
		try {
			conn.close(); // terminate connection
		} catch (SQLException e) {
			System.out.println("Error: Cannot terminate database connection.");
			e.printStackTrace();
		}
	}
}
