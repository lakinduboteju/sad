package sad.hw1.ts.test;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;
import sad.util.Money;

/**
 * This class is a JUnit 4 Test Case to test Money class (JUnit 4 Tests will run
 * only on Java 7 not on 6)
 * 
 * @author lakindu
 * 
 */
public class MoneyTester {

	@Test
	public void testAmount() {
		Money m = new Money(999.99);
		BigDecimal excepted = new BigDecimal(new BigInteger("99999"), 2); // 999.99
		assertEquals(excepted, m.amount());

		m = new Money(999);
		excepted = new BigDecimal(new BigInteger("99900"), 2); // 999.00
		assertEquals(excepted, m.amount());
	}

	@Test
	public void testAdd() {
		Money m = new Money(999.99);
		Money mAdd = new Money(0.01);
		BigDecimal excepted = new BigDecimal(new BigInteger("100000"), 2); // 999.99
																			// +
																			// 0.01
																			// =
																			// 1000.00
		m = m.add(mAdd);
		assertEquals(excepted, m.amount());
	}

	@Test
	public void testSubtract() {
		Money m = new Money(1000.00);
		Money mSub = new Money(0.01);
		BigDecimal excepted = new BigDecimal(new BigInteger("99999"), 2); // 1000.00
																			// -
																			// 0.01
																			// =
																			// 999.99
		m = m.subtract(mSub);
		assertEquals(excepted, m.amount());
	}

	@Test
	public void testMultiply() {
		Money m = new Money(999.99);
		m = m.multiply(2.01);
		BigDecimal excepted = new BigDecimal(new BigInteger("200998"), 2); // 999.99
																			// x
																			// 2.01
																			// =
																			// 2009.98
		assertEquals(excepted, m.amount());
	}

	@Test
	public void testGreaterThan() {
		Money m1 = new Money(999.98);
		Money m2 = new Money(999.99);
		assertEquals(true, m2.greaterThan(m1)); // 999.99 > 999.98
	}

	@Test
	public void testAllocate() {
		Money m = new Money(997.73);
		Money[] allocatedAmounts = m.allocate(4); // 249.44 + 249.43 + 249.43 +
													// 249.43 = 997.73
		assertEquals(allocatedAmounts[0], new Money(249.44));
		assertEquals(allocatedAmounts[1], new Money(249.43));
		assertEquals(allocatedAmounts[2], new Money(249.43));
		assertEquals(allocatedAmounts[3], new Money(249.43));
	}
}
