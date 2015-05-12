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

	public boolean buy(BusinessType businessType) {
		Integer currentlyOwned = portfolio.getBusinessesOwned().get(businessType);
		if (currentlyOwned == null)
			currentlyOwned = 0;
		BigDecimal costOfTheNext = businessType.costOfTheNext(currentlyOwned);
		if (playerHasEnoughMoney(costOfTheNext, currentlyOwned)) {
			buyBusiness(businessType, costOfTheNext, currentlyOwned);
			return true;
		}
		return false;
	}

	private void buyBusiness(BusinessType businessType, BigDecimal costOfTheNext,
			Integer currentlyOwned) {
		portfolio.getBusinessesOwned().put(businessType, currentlyOwned + 1);
		moneyInHand = moneyInHand.subtract(costOfTheNext);
	}

	private boolean playerHasEnoughMoney(BigDecimal costOfTheNext,
			Integer currentlyOwned) {
		return moneyInHand.compareTo(costOfTheNext) >= 0;
	}

	public void addBusiness(BusinessType businessType) {
		portfolio.addBusiness(businessType);
	}

	public Integer numberOf(BusinessType businessType) {
		return portfolio.numberOf(businessType);
	}
}
