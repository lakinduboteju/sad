package sad.hw1.test;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import sad.util.MfDate;

public class MfDateTester {

	@Test
	public void testAddDays() {
		MfDate d = new MfDate(2014, 1, 30);
		d.addDays(3);
		assertEquals("2014-1-30 + 3days = 2014-2-2", new MfDate(2014, 2, 2), d);
	}
	
	@Test
	public void testToSqlDate() {
		MfDate d = new MfDate();
		d.setYear(2014);
		d.setMonth(1);
		d.setDay(31);
		assertEquals("2014-1-31", Date.valueOf("2014-1-31"), d.toSqlDate());
	}

}
