package sad.hw1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import sad.hw1.gateway.ContractGateway;
import sad.hw1.gateway.ProductGateway;
import sad.hw1.gateway.RevenueRecognitionGateway;
import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

public class RevenueRecognitionGatewayTester {

	@Test
	public void testInsertRecognition() {
		long productID = 0;
		try {
			productID = ProductGateway.insertNewWordProcessor("New Word 2014");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		Money revenue = new Money(2250.24);
		MfDate dateSigned = new MfDate(2014, 1, 27);
		long contractID = -1;
		ContractGateway cg = new ContractGateway();
		try {
			contractID = cg.insertContract(revenue, dateSigned, productID);
			cg.close();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}

		RevenueRecognitionGateway rrg = new RevenueRecognitionGateway();
		try {
			rrg.insertRecognition(contractID, revenue, dateSigned);
			rrg.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			fail("SQL exception occurred");
		}

		ProductGateway pg = new ProductGateway();
		try {
			pg.deleteProduct(productID);
			pg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindContract() {
		long productID = 0;
		try {
			productID = ProductGateway.insertNewWordProcessor("New Word 2014");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		Money revenue = new Money(2250.24);
		MfDate dateSigned = new MfDate(2014, 1, 27);
		long contractID = -1;
		ContractGateway cg = new ContractGateway();
		try {
			contractID = cg.insertContract(revenue, dateSigned, productID);
			cg.close();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}

		RevenueRecognitionGateway rrg = new RevenueRecognitionGateway();
		try {
			rrg.insertRecognition(contractID, revenue, dateSigned);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		ResultSet rs = null;
		try {
			rs = rrg.findContract(contractID);
			rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}

		String productName = null;
		try {
			productName = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}
		assertEquals("New Word 2014", productName);

		String productType = null;
		try {
			productType = rs.getString("type");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}
		assertEquals("W", productType);

		BigDecimal contractRevenue = null;
		try {
			contractRevenue = rs.getBigDecimal("revenue");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}
		assertEquals(revenue.amount(), contractRevenue);

		Date contractDateSigned = null;
		try {
			contractDateSigned = rs.getDate("dateSigned");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}
		assertEquals(dateSigned.toSqlDate(), contractDateSigned);
	}

	@Test
	public void testFindRecognitionsFor() {
		long productID = 0;
		try {
			productID = ProductGateway.insertNewWordProcessor("New Word 2014");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		Money revenue = new Money(2250.24);
		MfDate dateSigned = new MfDate(2014, 1, 27);
		long contractID = -1;
		ContractGateway cg = new ContractGateway();
		try {
			contractID = cg.insertContract(revenue, dateSigned, productID);
			cg.close();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}

		RevenueRecognitionGateway rrg = new RevenueRecognitionGateway();
		try {
			rrg.insertRecognition(contractID, revenue, dateSigned);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		ResultSet rs = null;
		try {
			rs = rrg.findRecognitionsFor(contractID, new MfDate(2014, 1, 26));
			assertFalse(rs.next()); // result set should be empty. So rs.next()
									// should be false
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}

		rs = null;
		try {
			rs = rrg.findRecognitionsFor(contractID, new MfDate(2014, 1, 27));
			assertTrue(rs.next()); // result set should not be empty. So
									// rs.next() should be true
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}

		BigDecimal amount = null;
		try {
			amount = rs.getBigDecimal("amount");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL exception occurred");
		}
		assertEquals(new BigDecimal(new BigInteger("225024"), 2), amount); // 2250.24
	}

}
