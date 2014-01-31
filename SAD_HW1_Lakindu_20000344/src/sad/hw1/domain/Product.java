package sad.hw1.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sad.hw1.strategy.CompleteRecognitionStrategy;
import sad.hw1.strategy.RecognitionStrategy;
import sad.hw1.strategy.ThreeWayRecognitionStrategy;
import sad.util.ApplicationException;

public class Product extends ActiveRecord {
	private String name;
	private RecognitionStrategy recognitionStrategy;
	private String type;
	private long productId;

	public Product(String name, RecognitionStrategy recognitionStrategy) {
		this.name = name;
		this.recognitionStrategy = recognitionStrategy;
		productId = 0;
		createdObjects = new ArrayList<Object>();
		createdObjects.add(this);
	}

	public Product(String name, String type,
			RecognitionStrategy recognitionStrategy) {
		this.name = name;
		this.recognitionStrategy = recognitionStrategy;
		this.type = type;
		productId = 0;
		createdObjects = new ArrayList<Object>();
		createdObjects.add(this);
	}

	public void calculateRevenueRecognitions(Contract contract) {
		contract.setProduct(this); // set product of the contract
		recognitionStrategy.calculateRevenueRecognitions(contract);
	}

	/**
	 * Saves or Updates the product to the database
	 * 
	 * @return Product id of saved product
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public long saveOrUpdate() throws SQLException, ApplicationException {
		long retId = -1;
		if (productId == 0) // if id is 0, it is a new object. just insert into
							// database
		{
			String insertProductsStatement = "INSERT INTO products VALUES (0, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(
					insertProductsStatement, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, type);
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			stmt.closeOnCompletion(); // insert product into database
			if (resultSet.next()) {
				retId = resultSet.getLong(1);
				productId = retId; // get auto generated id and set it as the id
									// of the object
			} else {
				throw new ApplicationException(
						"Cannot get auto-generated product id");
			}
		} else if (productId > 0) // this product is already in database. so
									// just have to update the entry in database
		{
			String updateProductsStatement = "UPDATE products SET name = ?, type = ? WHERE productId = ?";
			PreparedStatement stmt = conn
					.prepareStatement(updateProductsStatement);
			stmt.setString(1, name);
			stmt.setString(2, type);
			stmt.setLong(3, productId);
			stmt.executeUpdate(); // update corresponding product entry
			retId = productId; // when updating id will not change in the
								// database
		}
		return retId; // return id after saving
	}

	/**
	 * Deletes the product from database
	 * 
	 * @return id of deleted product
	 * @throws SQLException
	 */
	public long delete() throws SQLException {
		long retId = -1;
		if (productId > 0) {
			String deleteProductsStatement = "DELETE FROM products WHERE productId = ?";
			PreparedStatement stmt = conn
					.prepareStatement(deleteProductsStatement);
			stmt.setLong(1, productId);
			stmt.executeUpdate();
			retId = productId;
			productId = 0;
		}
		return retId;
	}

	public static Product newWordProcessor(String name) {
		return new Product(name, new CompleteRecognitionStrategy());
	}

	public static Product newSpreadsheet(String name) {
		return new Product(name, new ThreeWayRecognitionStrategy(60, 90));
	}

	public static Product newDatabase(String name) {
		return new Product(name, new ThreeWayRecognitionStrategy(30, 60));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RecognitionStrategy getRecognitionStrategy() {
		return recognitionStrategy;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public static Product findProduct(long productID) throws SQLException {
		Product ret = null;
		for (Object o : createdObjects) {
			Product p = (Product) o;
			if (p.getProductId() == productID) {
				return p;
			}
		}

		final String findProductStatement = "SELECT * " + "FROM products "
				+ "WHERE productId = ?";
		PreparedStatement stmt = conn.prepareStatement(findProductStatement);
		stmt.setLong(1, productID);
		ResultSet r = stmt.executeQuery();
		r.next();
		String pName = r.getString("name");
		String pType = r.getString("type");
		long pId = r.getLong("productId");
		ret = new Product(pName, pType, null);
		ret.setProductId(pId);
		return ret;
	}
}
