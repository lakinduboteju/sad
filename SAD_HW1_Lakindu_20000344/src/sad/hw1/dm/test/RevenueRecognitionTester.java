package sad.hw1.dm.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import sad.hw1.domain.Contract;
import sad.hw1.domain.Product;
import sad.hw1.domain.RevenueRecognition;
import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

public class RevenueRecognitionTester {

	@Test
	public void testSave() {
		Product p = Product.newWordProcessor("My Word");
		Money amount = new Money(1589.45);
		MfDate date = new MfDate(2014, 1, 28);
		Contract c = new Contract(p, amount, date);
		RevenueRecognition rr = new RevenueRecognition(amount, date, c);
		try {
			rr.save();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
			fail("Exception occurred!");
		}
	}

}
