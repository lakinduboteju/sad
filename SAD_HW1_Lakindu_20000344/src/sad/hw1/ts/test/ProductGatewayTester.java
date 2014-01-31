package sad.hw1.ts.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import sad.hw1.gateway.ProductGateway;
import sad.util.ApplicationException;

public class ProductGatewayTester {

	@Test
	public void testInsertAndDeleteProduct() {
		long id = -1;
		try {
			id = ProductGateway.insertNewWordProcessor("MyWordProcessor");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		assertTrue(id > 0);

		ProductGateway pg = new ProductGateway();
		try {
			pg.deleteProduct(id);
			id = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(0, id);
	}

}
