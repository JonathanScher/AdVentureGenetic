package evolution;

import evolution.orders.Order;
import game.Game;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

public class Fitness implements FitnessEvaluator<List<Order>> {

	private double gameLength;

	public Fitness(double gameLength) {
		this.gameLength = gameLength;
	}

	@Override
	public boolean isNatural() {
		return true;
	}

	@Override
	public double getFitness(List<Order> candidate,
			List<? extends List<Order>> population) {
		double time = time(candidate);
		if (time > gameLength) {
			return 1 / time;
		}
		Game game = new Game();
		Double numberOfSuccessfulOrders = execute(game, candidate);
		
		if(numberOfSuccessfulOrders >= candidate.size()) {
			return game.moneyInHand.doubleValue();
		} else {
			return numberOfSuccessfulOrders;
		}
	}

	public double time(List<Order> candidate) {
		double length = 0;
		for (Order order : candidate) {
			length += order.length();
		}
		return length;
	}

	public double execute(Game game, List<Order> candidate) {
		Double numberOfSuccessfulOrder = 0d;
		for (Order order : candidate) {
			boolean success = order.execute(game);
			if (!success) {
				return numberOfSuccessfulOrder;
			}
			numberOfSuccessfulOrder++;
		}
		return numberOfSuccessfulOrder;
	}
}
