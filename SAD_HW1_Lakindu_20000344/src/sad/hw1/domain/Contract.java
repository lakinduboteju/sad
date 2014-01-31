package sad.hw1.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

public class Contract extends ActiveRecord {
	private List<RevenueRecognition> revenueRecognitions = new ArrayList<RevenueRecognition>();
	private Product product;
	private Money revenue;
	private MfDate whenSigned;
	private long contractId;

	public Contract(Product product, Money revenue, MfDate whenSigned) {
		this.product = product;
		this.revenue = revenue;
		this.whenSigned = whenSigned;
		createdObjects = new ArrayList<Object>();
		createdObjects.add(this);
	}

	public Money recognizedRevenue(MfDate asOf) {
		Money result = Money.dollars(0);
		for (RevenueRecognition r : revenueRecognitions)
			if (r.isRecognizableBy(asOf))
				result = result.add(r.getAmount());
		return result;
	}

	public void calculateRecognitions() {
		product.calculateRevenueRecognitions(this);
	}

	public void addRevenueRecognition(RevenueRecognition recognition) {
		revenueRecognitions.add(recognition);
	}

	public Money getRevenue() {
		return revenue;
	}

	public MfDate getWhenSigned() {
		return whenSigned;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	/**
	 * Saves or Updates the contract to the database
	 * 
	 * @return id of saved contract
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public long saveOrUpdate() throws SQLException, ApplicationException {
		if (product.getProductId() == 0) // product has not been saved. so save
											// product first
		{
			product.saveOrUpdate();
		}
		long retId = -1;
		if (contractId == 0) // a new object. just insert to database
		{
			String insertContractStatement = "INSERT INTO contracts VALUES (0, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(
					insertContractStatement, Statement.RETURN_GENERATED_KEYS);
			stmt.setBigDecimal(1, revenue.amount());
			stmt.setDate(2, whenSigned.toSqlDate());
			stmt.setLong(3, product.getProductId());
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			stmt.closeOnCompletion();
			if (resultSet.next()) {
				retId = resultSet.getLong(1);
				contractId = retId;
			} else {
				throw new ApplicationException(
						"Cannot get auto-generated contract id");
			}
		} else if (contractId > 0) // already in database. just update
		{
			String updateContractStatement = "UPDATE contracts SET revenue = ?, dateSigned = ?, product = ? WHERE contractId = ?";
			PreparedStatement stmt = conn
					.prepareStatement(updateContractStatement);
			stmt.setBigDecimal(1, revenue.amount());
			stmt.setDate(2, whenSigned.toSqlDate());
			stmt.setLong(3, product.getProductId());
			stmt.setLong(4, contractId);
			stmt.executeUpdate();
			retId = contractId;
		}
		return retId;
	}

	/**
	 * Deletes contract from database
	 * 
	 * @return id of deleted contract
	 * @throws SQLException
	 */
	public long delete() throws SQLException {
		long retId = -1;
		if (contractId > 0) // if id is greater than 0, entry is there in the
							// database
		{
			String deleteContractStatement = "DELETE FROM contracts WHERE contractId = ?";
			PreparedStatement stmt = conn
					.prepareStatement(deleteContractStatement);
			stmt.setLong(1, contractId);
			stmt.executeUpdate(); //delete the entry in the database
			retId = contractId;
			contractId = 0;
		}
		return retId; //return the id of deleted contract
	}

	public List<RevenueRecognition> getRevenueRecognitions() {
		return revenueRecognitions;
	}

	public void setRevenueRecognitions(
			List<RevenueRecognition> revenueRecognitions) {
		this.revenueRecognitions = revenueRecognitions;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getContractId() {
		return contractId;
	}

	public void setRevenue(Money revenue) {
		this.revenue = revenue;
	}

	public void setWhenSigned(MfDate whenSigned) {
		this.whenSigned = whenSigned;
	}

	public static Contract findProduct(long contractID) throws SQLException {
		Contract ret = null;
		for (Object o : createdObjects) {
			Contract c = (Contract) o;
			if (c.getContractId() == contractID) {
				return c;
			}
		}

		final String findContractStatement = "SELECT * " + "FROM contracts "
				+ "WHERE contractId = ?";
		PreparedStatement stmt = conn.prepareStatement(findContractStatement);
		stmt.setLong(1, contractID);
		ResultSet r = stmt.executeQuery();
		r.next();
		Money cRevenue = Money.dollars(r.getBigDecimal("revenue"));
		MfDate cWhenSigned = new MfDate(r.getDate("dateSigned"));
		long cId = r.getLong("contractId");
		ret = new Contract(null, cRevenue, cWhenSigned);
		ret.setContractId(cId);
		return ret;
	}
}
