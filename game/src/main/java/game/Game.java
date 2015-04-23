package game;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Game {
	private static final BigDecimal LEMON_STAND_INITIAL_PROFIT = new BigDecimal(
			"1");
	public BigDecimal moneyInHand;
	private Map<Business, Integer> businessesOwned;
	
	public Game() {
		moneyInHand = LEMON_STAND_INITIAL_PROFIT;
		businessesOwned = new HashMap<Business, Integer>();
		businessesOwned.put(Business.LEMON_STAND, 1);
	}

	public BigDecimal cash() {
		return moneyInHand;
	}

	public Map<Business, Integer> businessesOwned() {
		return businessesOwned;
	}

	public void doNothingFor(int seconds) {
		for (Map.Entry<Business, Integer> entry : businessesOwned.entrySet()) {
			BigDecimal numberOfBusinesses = new BigDecimal(entry.getValue()
					.toString());
			BigDecimal profitWaitingFor = entry.getKey().profitWaitingFor(
					seconds);
			moneyInHand = moneyInHand.add(profitWaitingFor
					.multiply(numberOfBusinesses));
		}
	}

	public boolean buy(Business businessType) {
		Integer currentlyOwned = businessesOwned.get(businessType);
		if (currentlyOwned == null)
			currentlyOwned = 0;
		BigDecimal costOfTheNext = businessType.costOfTheNext(currentlyOwned);
		if (playerHasEnoughMoney(businessType, costOfTheNext, currentlyOwned)) {
			buyBusiness(businessType, costOfTheNext, currentlyOwned);
			return true;
		}
		return false;
	}

	private void buyBusiness(Business businessType, BigDecimal costOfTheNext,
			Integer currentlyOwned) {
		businessesOwned.put(businessType, currentlyOwned + 1);
		moneyInHand = moneyInHand.subtract(costOfTheNext);
	}

	private boolean playerHasEnoughMoney(Business businessType,
			BigDecimal costOfTheNext, Integer currentlyOwned) {
		return moneyInHand.compareTo(costOfTheNext) >= 0;
	}
}
