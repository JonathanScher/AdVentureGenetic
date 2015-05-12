package game;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
	private Map<Business, Integer> businessesOwned;

	public Portfolio() {
		businessesOwned = new HashMap<Business, Integer>();
		businessesOwned.put(Business.LEMON_STAND, 1);

	}

	public void addBusiness(Business businessType) {
		businessesOwned.put(businessType, numberOf(businessType) + 1);
	}

	public Integer numberOf(Business businessType) {
		Integer numberOfBusinesses = businessesOwned.get(businessType);
		if (numberOfBusinesses == null) {
			numberOfBusinesses = 0;
		}
		return numberOfBusinesses;
	}

	public BigDecimal profitWaitingFor(int seconds) {
		BigDecimal moneyWaiting = new BigDecimal("0");
		for (Map.Entry<Business, Integer> entry : businessesOwned.entrySet()) {
			BigDecimal numberOfBusinesses = new BigDecimal(entry.getValue()
					.toString());
			BigDecimal profitWaitingFor = entry.getKey().profitWaitingFor(
					seconds);
			moneyWaiting = moneyWaiting.add(profitWaitingFor
					.multiply(numberOfBusinesses));
		}
		return moneyWaiting;
	}
}