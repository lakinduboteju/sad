package sad.hw1.domain;

import java.sql.Connection;
import java.util.List;

import sad.util.DatabaseConnector;

public class ActiveRecord {
	protected static Connection conn;
	protected static List<Object> createdObjects;
	private final String DBNAME = "sad_transaction";
	
	public ActiveRecord() {
		DatabaseConnector dbConn = new DatabaseConnector(DBNAME);
		conn = dbConn.getConnection();
	}
}
