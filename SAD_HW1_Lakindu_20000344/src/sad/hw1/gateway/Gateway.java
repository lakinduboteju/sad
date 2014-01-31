package sad.hw1.gateway;

import java.sql.Connection;
import java.sql.SQLException;

import sad.util.DatabaseConnector;

public class Gateway {
	protected Connection db;
	private final String DBNAME = "sad_transaction";
	public Gateway() {
		DatabaseConnector dbConnector = new DatabaseConnector(DBNAME);
		db = dbConnector.getConnection();
	}
	
	public void close() throws SQLException {
		db.close();
	}
}
