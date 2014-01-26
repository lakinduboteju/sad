package sad.hw1.domain;

import sad.util.MfDate;
import sad.util.Money;

public class RevenueRecognition {
	private Money amount;
	private MfDate date;

	public RevenueRecognition(Money amount, MfDate date) {
		this.amount = amount;
		this.date = date;
	}

	public Money getAmount() {
		return amount;
	}

	boolean isRecognizableBy(MfDate asOf) {
		return asOf.after(date) || asOf.equals(date);
	}
}
