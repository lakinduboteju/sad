package sad.hw1.domain;

import java.util.ArrayList;
import java.util.List;
import sad.util.MfDate;
import sad.util.Money;

public class Contract {
	private List<RevenueRecognition> revenueRecognitions = new ArrayList<RevenueRecognition>();
	private Product product;
	private Money revenue;
	private MfDate whenSigned;
	private long id;

	public Contract(Product product, Money revenue, MfDate whenSigned) {
		this.product = product;
		this.revenue = revenue;
		this.whenSigned = whenSigned;
	}

	public Money recognizedRevenue(MfDate asOf) {
		Money result = Money.dollars(0);
		for (RevenueRecognition r : revenueRecognitions)
			if (r.isRecognizableBy(asOf))
				result = result.add(r.getAmount());
		return result;
	}

	public void calculateRecognitions() {
		product.calculateRevenueRecognitions(this);
	}

	public void addRevenueRecognition(RevenueRecognition recognition) {
		revenueRecognitions.add(recognition);
	}

	public Money getRevenue() {
		return revenue;
	}

	public MfDate getWhenSigned() {
		return whenSigned;
	}
}
