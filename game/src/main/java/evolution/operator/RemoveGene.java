package evolution.operator;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;

import evolution.orders.Order;

/**
 * Mutation that removes one random element of the list
 * 
 * @author Jonathan Scher
 *
 * @param <T>
 */
public class RemoveGene extends AbstractMutation {

	public RemoveGene(Probability probability) {
		super(probability);
	}

	@Override
	public void mutate(List<Order> element, Random rng) {
		Integer size = element.size();
		if (size == 1) {
			element.remove(0);
		} else if (size > 0) {
			element.remove(rng.nextInt(size - 1));
		}
	}
}
