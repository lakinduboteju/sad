package sad.hw1.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

public class RevenueRecognition extends ActiveRecord{
	private Money amount;
	private MfDate date;
	private Contract contract;

	public RevenueRecognition(Money amount, MfDate date) {
		super();
		this.amount = amount;
		this.date = date;
		createdObjects = new ArrayList<Object>();
		createdObjects.add(this);
	}
	
	public RevenueRecognition(Money amount, MfDate date, Contract contract) {
		super();
		this.amount = amount;
		this.date = date;
		this.contract =  contract;
		createdObjects = new ArrayList<Object>();
		createdObjects.add(this);
	}

	public Money getAmount() {
		return amount;
	}

	boolean isRecognizableBy(MfDate asOf) {
		return asOf.after(date) || asOf.equals(date);
	}

	/**
	 * Saves revenue recognition to the database
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public void save() throws SQLException, ApplicationException {
		if (contract.getContractId() == 0) // contract has not been saved. so
											// save product first
		{
			contract.saveOrUpdate();
		}
		String insertContractStatement = "INSERT INTO revenueRecognitions VALUES (?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(insertContractStatement);
		stmt.setLong(1, contract.getContractId());
		stmt.setBigDecimal(2, amount.amount());
		stmt.setDate(3, date.toSqlDate());
		stmt.executeUpdate();
	}

	public MfDate getDate() {
		return date;
	}

	public void setDate(MfDate date) {
		this.date = date;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public static RevenueRecognition findRevenueRecognition(long contractID,
			MfDate recognizedOn) throws SQLException {
		RevenueRecognition ret = null;
		for (Object o : createdObjects) {
			RevenueRecognition rr = (RevenueRecognition) o;
			if (rr.getContract().getContractId() == contractID) {
				return rr;
			}
		}

		final String findRecognitionStatement = "SELECT * " + "FROM revenueRecognitions "
				+ "WHERE contract = ? AND recognizedOn = ?";
			PreparedStatement stmt = conn
					.prepareStatement(findRecognitionStatement);
			stmt.setLong(1, contractID);
			stmt.setDate(2, recognizedOn.toSqlDate());
			ResultSet r = stmt.executeQuery();
			r.next();
			Money rrAmount = Money.dollars(r.getBigDecimal("amount"));
			MfDate rrDate = new MfDate(r.getDate("recognizedOn"));
			ret = new RevenueRecognition(rrAmount, rrDate);
			return ret;
	}
}
