package sad.hw1.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sad.util.DatabaseConnector;
import sad.util.MfDate;
import sad.util.Money;

/**
 * This class maps Revenue Recognitions to the data source (revenueRecognition
 * table). This class uses Table Data Gateway pattern for data mapping. This
 * class acts as database wrapper for RevenueRecognition class, which runs
 * transaction scripts.
 * 
 * @author Martin Fowler
 * 
 */
public class RevenueRecognitionGateway {
	private final String DBNAME = "sad_transaction";
	private Connection db;
	private static final String findRecognitionsStatement = "SELECT amount "
			+ "FROM revenueRecognitions "
			+ "WHERE contract = ? AND recognizedOn <= ?";
	private static final String findContractStatement = "SELECT * "
			+ "FROM contracts c, products p "
			+ "WHERE id = ? AND c.product = p.id";
	private static final String insertRecognitionStatement = "INSERT INTO revenueRecognitions VALUES (?, ?, ?)";

	public RevenueRecognitionGateway() {
		DatabaseConnector dbConnector = new DatabaseConnector(DBNAME);
		db = dbConnector.getConnection();
	}

	public ResultSet findRecognitionsFor(long contractID, MfDate asof)
			throws SQLException {
		PreparedStatement stmt = db.prepareStatement(findRecognitionsStatement);
		stmt.setLong(1, contractID);
		stmt.setDate(2, asof.toSqlDate());
		ResultSet result = stmt.executeQuery();
		return result;
	}

	public ResultSet findContract(long contractID) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(findContractStatement);
		stmt.setLong(1, contractID);
		ResultSet result = stmt.executeQuery();
		return result;
	}

	public void insertRecognition(long contractID, Money amount, MfDate asof)
			throws SQLException {
		PreparedStatement stmt = db
				.prepareStatement(insertRecognitionStatement);
		stmt.setLong(1, contractID);
		stmt.setBigDecimal(2, amount.amount());
		stmt.setDate(3, asof.toSqlDate());
		stmt.executeUpdate();
	}

	public void close() throws SQLException {
		db.close();
	}
}
