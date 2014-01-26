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
public class MfDate extends GregorianCalendar {
	/**
	 * Constructs a date
	 */
	public MfDate() {
		super();
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
		super(year, (month - 1), day); // in GregorianCalendar month is
										// represented from 0 to 11 (0 for
										// January, ..., 11 for December)
	}
	
	public MfDate(Date date)
	{
		super();
		setTime(date);
	}

	/**
	 * Gets the date which is converted to java.sql.Date
	 * 
	 * @return java.sql.Date object of MfDate
	 */
	public Date toSqlDate() {
		return Date.valueOf(get(Calendar.YEAR) + "-"
				+ (get(Calendar.MONTH) + 1) + "-" + get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Adds days to currently holding date and gives new MfDate object
	 * @param numberOfDays number of days to add
	 * @return new Date with added days
	 */
	public MfDate addDays(int numberOfDays) {
		int year = this.get(Calendar.YEAR);
		int month = this.get(Calendar.MONTH);
		int day = this.get(Calendar.DAY_OF_MONTH);
		MfDate retDate = new MfDate(year, month, day);
		retDate.add(Calendar.DAY_OF_MONTH, numberOfDays);
		return retDate;
	}

	/**
	 * Sets year
	 * 
	 * @param year
	 *            Year (eg: 2013, 2014)
	 */
	public void setYear(int year) {
		set(Calendar.YEAR, year);
	}

	/**
	 * Sets month
	 * 
	 * @param month
	 *            Month (eg: 1 for January, 2 for February, ..., 12 for
	 *            December)
	 */
	public void setMonth(int month) {
		set(Calendar.MONTH, (month-1));
	}

	/**
	 * Sets day
	 * 
	 * @param day
	 *            Day (eg: 1, 2, ..., 31)
	 */
	public void setDay(int day) {
		set(Calendar.DAY_OF_MONTH, day);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MfDate)
		{
			MfDate tempDate = (MfDate) obj;
			if(tempDate.get(Calendar.YEAR) == this.get(Calendar.YEAR) &&
					tempDate.get(Calendar.MONTH) == this.get(Calendar.MONTH) &&
					tempDate.get(Calendar.DAY_OF_MONTH) == this.get(Calendar.DAY_OF_MONTH))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int year = this.get(Calendar.YEAR);
		int month = this.get(Calendar.MONTH);
		int day = this.get(Calendar.DAY_OF_MONTH);
		return (int)((year ^ (year >>> 32)) + (month ^ (month >>> 32)) + (day ^ (day >>> 32)));
	}

	@Override
	public String toString() {
		int year = this.get(Calendar.YEAR);
		int month = this.get(Calendar.MONTH);
		int day = this.get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day;
	}
}
