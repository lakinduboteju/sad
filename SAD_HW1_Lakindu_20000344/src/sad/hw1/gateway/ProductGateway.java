package sad.hw1.gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sad.util.ApplicationException;
import sad.util.DatabaseConnector;

public class ProductGateway {
	private final String DBNAME = "sad_transaction";
	private Connection db;

	private static final String insertProductsStatement = "INSERT INTO products VALUES (0, ?, ?)";
	private static final String deleteProductsStatement = "DELETE FROM products WHERE productId = ?";

	public ProductGateway() {
		DatabaseConnector dbConnector = new DatabaseConnector(DBNAME);
		db = dbConnector.getConnection();
	}

	public long insertProduct(String name, String type) throws SQLException,
			ApplicationException {
		PreparedStatement stmt = db.prepareStatement(insertProductsStatement, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		stmt.setString(2, type);
		stmt.executeUpdate();
		ResultSet resultSet = stmt.getGeneratedKeys();
		if (resultSet.next()) {
			return resultSet.getLong(1);
		} else {
			throw new ApplicationException("Cannot get auto-generated product id");
		}
	}

	public void deleteProduct(long productID) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(deleteProductsStatement);
		stmt.setLong(1, productID);
		stmt.executeUpdate();
	}

	public static long insertNewWordProcessor(String name) throws ApplicationException {
		long autoGeneratedID = -1;
		ProductGateway pg = new ProductGateway();
		try {
			autoGeneratedID = pg.insertProduct(name, "W");
			pg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autoGeneratedID;
	}

	public static long insertNewSpreadsheet(String name) throws ApplicationException {
		long autoGeneratedID = -1;
		ProductGateway pg = new ProductGateway();
		try {
			autoGeneratedID = pg.insertProduct(name, "S");
			pg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autoGeneratedID;
	}

	public static long insertNewDatabase(String name) throws ApplicationException {
		long autoGeneratedID = -1;
		ProductGateway pg = new ProductGateway();
		try {
			autoGeneratedID = pg.insertProduct(name, "D");
			pg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autoGeneratedID;
	}

	public void close() throws SQLException {
		db.close();
	}
}
