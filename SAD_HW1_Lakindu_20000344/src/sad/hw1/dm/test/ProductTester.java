package sad.hw1.dm.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import sad.hw1.domain.Product;
import sad.util.ApplicationException;

public class ProductTester {

	@Test
	public void testSaveOrUpdate() {
		Product p = new Product("Word 2014", "W", null);
		long autoGeneratedId = -1;
		try {
			autoGeneratedId = p.saveOrUpdate();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}
		assertTrue(autoGeneratedId > 0);
		
		p.setName("Database 2014");
		p.setType("D");
		long retId = -1;
		try {
			retId = p.saveOrUpdate();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}
		assertTrue((retId > 0) && (retId == autoGeneratedId));
	}

	@Test
	public void testDelete()
	{
		Product p = new Product("Word 2014", "W", null);
		long autoGeneratedId = -1;
		try {
			autoGeneratedId = p.saveOrUpdate();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}
		
		long deletedId = -1;
		try {
			deletedId = p.delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue((deletedId > 0) && (deletedId == autoGeneratedId));
	}
}
