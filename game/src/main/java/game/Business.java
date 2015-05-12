package game;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public enum Business {
	LEMON_STAND(4 / 1.07, 0.6, 1l, 1.07), NEWSPAPER_DELIVERY(60d, 3, 60l, 1.15), CAR_WASH(
			720d, 6, 540l, 1.14), PIZZA_DELIVERY(8640d, 12, 4320l, 1.13), DONUT_SHOP(
			103680d, 24, 51840l, 1.12), SHRIMP_BOAT(1244160d, 96, 622080l, 1.11), HOCKEY_TEAM(
			14929920d, 384, 7464960l, 1.10), MOVIE_STUDIO(179159040d, 1536,
			89579520l, 1.09), BANK(2149908480d, 6144, 1074954240l, 1.08), OIL_COMPANY(
			25798901760d, 36864, 29668737024l, 1.07);

	private BigDecimal initialCost;
	private Double initialTime;
	private BigDecimal initialProfit;
	private BigDecimal coefficient;

	private transient Cache<Integer, BigDecimal> costOfTheNextCache = CacheBuilder
			.newBuilder().maximumSize(1000).build();
	private transient Cache<Integer, BigDecimal> profitWaitingFor = CacheBuilder
			.newBuilder().maximumSize(1000).build();

	private Business(Double initialCost, double initalTime, Long initialProfit,
			Double coefficient) {
		this.initialCost = new BigDecimal(initialCost.toString());
		this.initialTime = initalTime;
		this.initialProfit = new BigDecimal(initialProfit.toString());
		this.coefficient = new BigDecimal(coefficient.toString());
	}

	public Double getInitialCost() {
		return initialCost.doubleValue();
	}

	public BigDecimal uncachedProfitWaitingFor(int seconds) {
		Integer ticks = (int) (seconds / initialTime);
		BigDecimal profit = new BigDecimal(ticks.toString())
				.multiply(initialProfit);
		return profit;
	}

	public BigDecimal costOfTheNext(int currentNumber) {
		try {
			return costOfTheNextCache.get(currentNumber,
					new Callable<BigDecimal>() {

						@Override
						public BigDecimal call() throws Exception {
							return uncachedCostOfTheNext(currentNumber);
						}
					});
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public BigDecimal profitWaitingFor(int seconds) {
		try {
			return profitWaitingFor.get(seconds, new Callable<BigDecimal>() {

				@Override
				public BigDecimal call() throws Exception {
					return uncachedProfitWaitingFor(seconds);
				}
			});
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private BigDecimal uncachedCostOfTheNext(int currentNumber) {
		// Geometric progression: an = a . r ^ (n-1) <=> an = r . an-1
		// a = initial cost, r = coefficient, an = nth term of the sequence
		// Source: http://en.wikipedia.org/wiki/Geometric_progression
		BigDecimal exactCostOfTheNext = initialCost.multiply(coefficient
				.pow(currentNumber));
		return exactCostOfTheNext.round(new MathContext(2, RoundingMode.DOWN));
	}
}
