package evolution.factory;

import java.util.Random;

import evolution.orders.Buy;
import evolution.orders.Order;
import evolution.orders.Wait;
import game.BusinessType;

public class OrderFactory {

	private Integer gameLength;

	public OrderFactory(Integer gameLength) {
		this.gameLength = gameLength;
	}

	public Order generateGene(Random rng) {
		Order generated;
		if (rng.nextBoolean()) {
			generated = generateWait(rng);
		} else {
			generated = generateBuy(rng);
		}
		return generated;
	}

	public Order generateBuy(Random rng) {
		Order generated;
		BusinessType[] businesses = BusinessType.values();
		BusinessType randomBusiness = businesses[rng.nextInt(businesses.length)];
		generated = Buy.getInstance(randomBusiness);
		return generated;
	}

	public Order generateWait(Random rng) {
		Order generated;
		generated = Wait.getInstance(rng.nextInt(gameLength / 10));
		return generated;
	}

	public Order generateWait(Random rng, int coef) {
		Order generated;
		generated = Wait.getInstance(1 + rng.nextInt(gameLength / coef));
		return generated;
	}

}
