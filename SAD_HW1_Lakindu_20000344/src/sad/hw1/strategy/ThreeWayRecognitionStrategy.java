package sad.hw1.strategy;

import sad.hw1.domain.Contract;
import sad.hw1.domain.RevenueRecognition;
import sad.util.Money;

public class ThreeWayRecognitionStrategy extends RecognitionStrategy {
	private int firstRecognitionOffset;
	private int secondRecognitionOffset;

	public ThreeWayRecognitionStrategy(int firstRecognitionOffset,
			int secondRecognitionOffset) {
		this.firstRecognitionOffset = firstRecognitionOffset;
		this.secondRecognitionOffset = secondRecognitionOffset;
	}

	public void calculateRevenueRecognitions(Contract contract) {
		Money[] allocation = contract.getRevenue().allocate(3);
		contract.addRevenueRecognition(new RevenueRecognition(allocation[0],
				contract.getWhenSigned()));
		contract.addRevenueRecognition(new RevenueRecognition(allocation[1],
				contract.getWhenSigned().addDays(firstRecognitionOffset)));
		contract.addRevenueRecognition(new RevenueRecognition(allocation[2],
				contract.getWhenSigned().addDays(secondRecognitionOffset)));
	}

}
