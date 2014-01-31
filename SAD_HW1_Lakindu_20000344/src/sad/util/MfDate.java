package sad.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class can hold a date, convert the date to java.sql.Date and do
 * calculations such as adding days to current date
 * 
 * @author lakindu
 * 
 */
public class MfDate {
	private GregorianCalendar calendar;
	/**
	 * Constructs a date
	 */
	public MfDate() {
		calendar = new GregorianCalendar();
	}

	/**
	 * Constructs a date
	 * 
	 * @param year
	 *            Year (eg: 2013, 2014)
	 * @param month
	 *            Month (eg: 1 for January, 2 for February, ...)
	 * @param day
	 *            Day (eg: 1, 2, ..., 31)
	 */
	public MfDate(int year, int month, int day) {
		calendar = new GregorianCalendar(year, (month - 1), day); // in GregorianCalendar month is
										// represented from 0 to 11 (0 for
										// January, ..., 11 for December)
	}
	
	public MfDate(Date date)
	{
		calendar = new GregorianCalendar();
		calendar.setTime(date);
	}

	/**
	 * Gets the date which is converted to java.sql.Date
	 * 
	 * @return java.sql.Date object of MfDate
	 */
	public Date toSqlDate() {
		return Date.valueOf(calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Adds days to currently holding date and gives new MfDate object
	 * @param numberOfDays number of days to add
	 * @return new Date with added days
	 */
	public MfDate addDays(int numberOfDays) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		MfDate retDate = new MfDate(year, (month+1), day);
		retDate.calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
		return retDate;
	}

	/**
	 * Sets year
	 * 
	 * @param year
	 *            Year (eg: 2013, 2014)
	 */
	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
	}

	/**
	 * Sets month
	 * 
	 * @param month
	 *            Month (eg: 1 for January, 2 for February, ..., 12 for
	 *            December)
	 */
	public void setMonth(int month) {
		calendar.set(Calendar.MONTH, (month-1));
	}

	/**
	 * Sets day
	 * 
	 * @param day
	 *            Day (eg: 1, 2, ..., 31)
	 */
	public void setDay(int day) {
		calendar.set(Calendar.DAY_OF_MONTH, day);
	}
	
	public boolean after(MfDate date)
	{
		return calendar.after(date.calendar);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MfDate)
		{
			MfDate tempDate = (MfDate) obj;
			if(tempDate.calendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
					tempDate.calendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
					tempDate.calendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return (int)((year ^ (year >>> 32)) + (month ^ (month >>> 32)) + (day ^ (day >>> 32)));
	}

	@Override
	public String toString() {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day;
	}
}
