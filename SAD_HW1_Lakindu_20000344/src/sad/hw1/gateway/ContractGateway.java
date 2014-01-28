package sad.hw1.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sad.util.ApplicationException;
import sad.util.DatabaseConnector;
import sad.util.MfDate;
import sad.util.Money;

public class ContractGateway {
	private final String DBNAME = "sad_transaction";
	private Connection db;

	private static final String insertContracctStatement = "INSERT INTO contracts VALUES (0, ?, ?, ?)";
	private static final String deleteContractStatement = "DELETE FROM contracts WHERE contractId = ?";

	public ContractGateway() {
		DatabaseConnector dbConnector = new DatabaseConnector(DBNAME);
		db = dbConnector.getConnection();
	}

	public long insertContract(Money revenue, MfDate dateSigned, long productID)
			throws SQLException, ApplicationException {
		PreparedStatement stmt = db.prepareStatement(insertContracctStatement,
				Statement.RETURN_GENERATED_KEYS);
		stmt.setBigDecimal(1, revenue.amount());
		stmt.setDate(2, dateSigned.toSqlDate());
		stmt.setLong(3, productID);
		ResultSet resultSet = stmt.getGeneratedKeys();
		if (resultSet.next()) {
			return resultSet.getLong(1);
		} else {
			throw new ApplicationException("Cannot get auto-generated contract id");
		}
	}

	public void deleteContract(long productID) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(deleteContractStatement);
		stmt.setLong(1, productID);
	}

	public void close() throws SQLException {
		db.close();
	}
}
