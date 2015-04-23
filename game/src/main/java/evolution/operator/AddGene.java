package evolution.operator;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;

import evolution.factory.OrderFactory;
import evolution.orders.Order;

public class AddGene extends AbstractMutation {

	OrderFactory factory;

	public AddGene(Integer gameLength, Probability probability) {
		super(probability);
		factory = new OrderFactory(gameLength);
	}

	@Override
	public void mutate(List<Order> element, Random rng) {
		Order gene = factory.generateGene(rng);
		Integer position = 0;
		if (element.size() > 1) {
			position = rng.nextInt(element.size());
		}
		element.add(position, gene);
	}

}
