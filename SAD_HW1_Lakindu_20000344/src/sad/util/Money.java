package sad.util;

import java.math.BigDecimal;

import org.junit.Assert;

/**
 * This class is used to do currency calculations. The primary reason is of
 * using this class is the handling of rounding behavior. So that this class
 * helps reduce the problems of rounding errors.
 * 
 * @author Matt Foemmel and Martin Fowler
 * 
 *         This is a modified version of the Money class of Matt Foemmel and
 *         Martin Fowler
 * 
 */
public class Money {
	private long amount;
	private static final int[] cents = new int[] { 1, 10, 100, 1000 }; //available cent factors
	private static int defaultFractionDigits = 2; // This is used when selecting cent
											// factor

	public Money(double amount) {
		this.amount = Math.round(amount * centFactor());
	}

	public Money(long amount) {
		this.amount = amount * centFactor();
	}

	public BigDecimal amount() {
		return BigDecimal.valueOf(amount, defaultFractionDigits);
	}
	
	public static Money dollars(double amount) {
		return new Money(amount);
	}

	public static Money dollars(BigDecimal amount) {
		amount.setScale(defaultFractionDigits, BigDecimal.ROUND_HALF_EVEN);
		return new Money(amount.doubleValue());
	}

	public boolean equals(Object other) {
		return (other instanceof Money) && equals((Money) other);
	}

	public boolean equals(Money other) {
		return (defaultFractionDigits == other.defaultFractionDigits)
				&& (amount == other.amount);
	}

	public Money add(Money other) {
		assertSameCurrencyAs(other);
		return newMoney(amount + other.amount);
	}

	public Money subtract(Money other) {
		assertSameCurrencyAs(other);
		return newMoney(amount - other.amount);
	}

	public boolean greaterThan(Money other) {
		return (compareTo(other) > 0);
	}

	public Money multiply(double amount) {
		return multiply(new BigDecimal(amount));
	}

	public Money multiply(BigDecimal amount) {
		return multiply(amount, BigDecimal.ROUND_HALF_EVEN);
	}

	public Money multiply(BigDecimal amount, int roundingMode) {
		BigDecimal tempMul = amount().multiply(amount);
		tempMul.setScale(defaultFractionDigits, roundingMode);
		return new Money(tempMul.doubleValue());
	}

	public int compareTo(Object other) {
		if (other instanceof Money)
			return compareTo((Money) other);
		else
			return -1;
	}

	public int compareTo(Money other) {
		if (amount < other.amount)
			return -1;
		else if (amount == other.amount)
			return 0;
		else
			return 1;
	}

	public Money[] allocate(int n) {
		Money lowResult = newMoney(amount / n);
		Money highResult = newMoney(lowResult.amount + 1);
		Money[] results = new Money[n];
		int remainder = (int) amount % n;
		for (int i = 0; i < remainder; i++)
			results[i] = highResult;
		for (int i = remainder; i < n; i++)
			results[i] = lowResult;
		return results;
	}

	public Money[] allocate(long[] ratios) {
		long total = 0;
		for (int i = 0; i < ratios.length; i++)
			total += ratios[i];
		long remainder = amount;
		Money[] results = new Money[ratios.length];
		for (int i = 0; i < results.length; i++) {
			results[i] = newMoney(amount * ratios[i] / total);
			remainder -= results[i].amount;
		}
		for (int i = 0; i < remainder; i++) {
			results[i].amount++;
		}
		return results;
	}

	public int hashCode() {
		return (int) (amount ^ (amount >>> 32));
	}

	/**
	 * Sets cent factor of Money
	 * 
	 * @param indexOfCentFactor
	 *            index of cent factor. Indexes are: 0 for cent factor 1, 1 for
	 *            cent factor 10, 2 for cent factor 100 (default), 3 for cent
	 *            factor 1000
	 */
	public void setDefaultFractionDigits(int indexOfCentFactor) {
		defaultFractionDigits = indexOfCentFactor;
	}

	private Money() {
		this.amount = 0;
	}

	private int centFactor() {
		return cents[defaultFractionDigits];
	}

	private void assertSameCurrencyAs(Money arg) {
		Assert.assertEquals(
				"Money type mismatch (Mismatch in fraction digits)",
				defaultFractionDigits, arg.defaultFractionDigits);
	}

	private Money newMoney(long amount) {
		Money money = new Money();
		money.amount = amount;
		return money;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(amount());
		return str.toString();
	}
}
