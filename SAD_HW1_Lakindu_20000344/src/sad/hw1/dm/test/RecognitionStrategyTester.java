package sad.hw1.dm.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import sad.hw1.domain.Contract;
import sad.hw1.domain.Product;
import sad.hw1.domain.RevenueRecognition;
import sad.hw1.strategy.RecognitionStrategy;
import sad.hw1.strategy.ThreeWayRecognitionStrategy;
import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

public class RecognitionStrategyTester {

	@Test
	public void test() {
		Product p = Product.newSpreadsheet("My Spread Sheet");
		Contract c = new Contract(p, new Money(2344.50),
				new MfDate(2014, 1, 28));
		p.calculateRevenueRecognitions(c);
		List<RevenueRecognition> rrList = c.getRevenueRecognitions();
		assertEquals(3, rrList.size()); // there should be 3 revenue
										// recognitions inside the contract now
		
		for(RevenueRecognition rr : rrList)
		{
			try {
				rr.setContract(c);
				rr.save();
			} catch (SQLException | ApplicationException e) {
				e.printStackTrace();
				fail("Exception occurred!");
			}
		}
		
		try {
			c.saveOrUpdate();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
			fail("Exception occurred!");
		}
	}

}
