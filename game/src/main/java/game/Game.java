package game;

import java.math.BigDecimal;

public class Game {
	private static final BigDecimal LEMON_STAND_INITIAL_PROFIT = new BigDecimal(
			"1");
	public BigDecimal moneyInHand;
	private Portfolio portfolio = new Portfolio();

	public Game() {
		moneyInHand = LEMON_STAND_INITIAL_PROFIT;
	}

	public BigDecimal cash() {
		return moneyInHand;
	}

	public void doNothingFor(int seconds) {
		moneyInHand = moneyInHand.add(portfolio.profitWaitingFor(seconds));
	}

	public boolean buy(Business businessType) {
		Integer currentlyOwned = numberOf(businessType);
		BigDecimal costOfTheNext = businessType.costOfTheNext(currentlyOwned);
		if (playerHasEnoughMoney(costOfTheNext, currentlyOwned)) {
			buyBusiness(businessType, costOfTheNext, currentlyOwned);
			return true;
		}
		return false;
	}

	private void buyBusiness(Business businessType, BigDecimal costOfTheNext,
			Integer currentlyOwned) {
		addBusiness(businessType);
		moneyInHand = moneyInHand.subtract(costOfTheNext);
	}

	private boolean playerHasEnoughMoney(BigDecimal costOfTheNext,
			Integer currentlyOwned) {
		return moneyInHand.compareTo(costOfTheNext) >= 0;
	}

	public void addBusiness(Business businessType) {
		portfolio.addBusiness(businessType);
	}

	public Integer numberOf(Business businessType) {
		return portfolio.numberOf(businessType);
	}
}
