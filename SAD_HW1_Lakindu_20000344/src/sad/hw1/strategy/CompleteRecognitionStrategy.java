package sad.hw1.strategy;

import sad.hw1.domain.Contract;
import sad.hw1.domain.RevenueRecognition;

public class CompleteRecognitionStrategy extends RecognitionStrategy {
	public void calculateRevenueRecognitions(Contract contract) {
		contract.addRevenueRecognition(new RevenueRecognition(contract
				.getRevenue(), contract.getWhenSigned()));
	}

}
