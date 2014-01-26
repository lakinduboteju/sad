package sad.hw1.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import sad.hw1.gateway.RevenueRecognitionGateway;
import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

/**
 * This class executes transaction scripts for recognizedRevenue procedure and
 * calculateRevenueRecognitions procedure
 * 
 * @author Martin Fowler
 * 
 */
public class RevenueRecognitionService {
	private RevenueRecognitionGateway db;

	public RevenueRecognitionService() {
		db = new RevenueRecognitionGateway();
	}

	/**
	 * Transaction script to get the sum of recognitions up to some date for a
	 * contract
	 * 
	 * @param contractNumber
	 *            id of contract
	 * @param asOf
	 *            the date
	 * @return total sum of recognition in Money form
	 * @throws ApplicationException
	 *             when SQLExceptions occurred
	 */
	public Money recognizedRevenue(long contractNumber, MfDate asOf)
			throws ApplicationException {
		Money result = Money.dollars(0);
		try {
			ResultSet rs = db.findRecognitionsFor(contractNumber, asOf);
			while (rs.next()) {
				result = result.add(Money.dollars(rs.getBigDecimal("amount")));
			}
			return result;
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * Transaction script that adds the appropriate revenue recognitions to the
	 * database based on the product type
	 * 
	 * @param contractNumber
	 *            id of contract
	 */
	public void calculateRevenueRecognitions(long contractNumber) {
		try {
			ResultSet contracts = db.findContract(contractNumber);
			contracts.next();
			Money totalRevenue = Money.dollars(contracts
					.getBigDecimal("revenue"));
			MfDate recognitionDate = new MfDate(contracts.getDate("dateSigned"));
			String type = contracts.getString("type");
			if (type.equals("S")) {
				Money[] allocation = totalRevenue.allocate(3);
				db.insertRecognition(contractNumber, allocation[0],
						recognitionDate);
				db.insertRecognition(contractNumber, allocation[1],
						recognitionDate.addDays(60));
				db.insertRecognition(contractNumber, allocation[2],
						recognitionDate.addDays(90));
			} else if (type.equals("W")) {
				db.insertRecognition(contractNumber, totalRevenue,
						recognitionDate);
			} else if (type.equals("D")) {
				Money[] allocation = totalRevenue.allocate(3);
				db.insertRecognition(contractNumber, allocation[0],
						recognitionDate);
				db.insertRecognition(contractNumber, allocation[1],
						recognitionDate.addDays(30));
				db.insertRecognition(contractNumber, allocation[2],
						recognitionDate.addDays(60));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() throws SQLException {
		db.close();
	}
}
