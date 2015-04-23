package evolution.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import evolution.orders.Order;

public abstract class AbstractMutation implements
		EvolutionaryOperator<List<Order>> {

	private Probability probability;

	protected AbstractMutation(Probability probability) {
		this.probability = probability;
	}

	@Override
	public List<List<Order>> apply(List<List<Order>> selectedCandidates,
			Random rng) {
		List<List<Order>> mutants = new ArrayList<List<Order>>();
		for (List<Order> candidate : selectedCandidates) {
			List<Order> mutant = new ArrayList<Order>(candidate);
			mutants.add(mutant);
		}

		mutants.parallelStream().forEach(x -> {
			if (probability.nextEvent(rng)) {
				mutate(x, rng);
			}
		});
		return mutants;
	}

	public abstract void mutate(List<Order> element, Random rng);
}
