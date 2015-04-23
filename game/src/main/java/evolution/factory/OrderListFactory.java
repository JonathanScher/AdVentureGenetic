package evolution.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import evolution.orders.Order;
import evolution.orders.Wait;

public class OrderListFactory extends AbstractCandidateFactory<List<Order>> {
	private OrderFactory factory;
	private Integer numberOfGenes;

	public OrderListFactory(Integer gameLength, Integer numberOfGenes) {
		super();
		factory = new OrderFactory(gameLength);
		this.numberOfGenes = numberOfGenes;
	}

	@Override
	public List<Order> generateRandomCandidate(Random rng) {
		List<Order> generated = new ArrayList<Order>();
		for (int i = 0; i < numberOfGenes; i++) {
			if (i % 2 == 0) {
				generated.add(factory.generateWait(rng, numberOfGenes /(numberOfGenes - i)));
			} else {
				generated.add(factory.generateBuy(rng));
			}
		}
		return generated;
	}
}
