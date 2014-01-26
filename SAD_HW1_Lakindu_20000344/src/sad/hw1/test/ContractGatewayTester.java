package sad.hw1.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import sad.hw1.gateway.ContractGateway;
import sad.hw1.gateway.ProductGateway;
import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

public class ContractGatewayTester {

	@Test
	public void testInsertAndDeleteContract() {
		long productID = 0;
		try {
			productID = ProductGateway.insertNewWordProcessor("MyWord2014");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		Money revenue = new Money(1250.50);
		MfDate dateSigned = new MfDate(2014, 1, 26);
		long contractID = -1;
		ContractGateway cg = new ContractGateway();
		try {
			contractID = cg.insertContract(revenue, dateSigned, productID);
			cg.close();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}
		assertTrue(contractID > 0);
		
		ProductGateway pg = new ProductGateway();
		try {
			pg.deleteProduct(productID);
			pg.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
