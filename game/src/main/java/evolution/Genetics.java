package evolution;

import java.util.ArrayList;
import java.util.List;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CachingFitnessEvaluator;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.islands.IslandEvolution;
import org.uncommons.watchmaker.framework.islands.IslandEvolutionObserver;
import org.uncommons.watchmaker.framework.islands.RingMigration;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.operators.ListCrossover;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;

import evolution.factory.OrderListFactory;
import evolution.operator.AddGene;
import evolution.operator.ProtectedListInversion;
import evolution.operator.RemoveGene;
import evolution.orders.Order;

public class Genetics {
	public void run(EvolutionObserver observer, int populationSize,
			int eliteCount, TerminationCondition... conditions) {
		EvolutionEngine<List<Order>> engine = generateEngine();
		engine.addEvolutionObserver(observer);
		engine.evolve(populationSize, eliteCount, conditions);
	}

	public void runIsland(IslandEvolutionObserver<List<Order>> observer, int numberOfIlands, int populationSize,
			int eliteCount, Integer epochLenght, Integer migration, TerminationCondition... conditions) {

		Integer gameLength = 3600;
		FitnessEvaluator<List<Order>> fitnessEvaluator = new Fitness(gameLength);
		;
		FitnessEvaluator<List<Order>> cachedEvaluator = new CachingFitnessEvaluator<List<Order>>(
				fitnessEvaluator);
		EvolutionPipeline evolutionaryOperator = generatePipeline(gameLength);

		CandidateFactory<List<Order>> candidateFactory = new OrderListFactory(
				gameLength, 200);

		IslandEvolution<List<Order>> engine = new IslandEvolution<List<Order>>(
				numberOfIlands, // Number of islands.
				new RingMigration(), candidateFactory, evolutionaryOperator,
				cachedEvaluator, new RouletteWheelSelection(),
				new MersenneTwisterRNG());
		engine.addEvolutionObserver(observer);
		engine.evolve(populationSize, eliteCount, epochLenght, migration, conditions);

	}

	private EvolutionEngine<List<Order>> generateEngine() {
		Integer gameLength = 3600;
		FitnessEvaluator<List<Order>> fitnessEvaluator = new Fitness(gameLength);
		;
		FitnessEvaluator<List<Order>> cachedEvaluator = new CachingFitnessEvaluator<List<Order>>(
				fitnessEvaluator);
		EvolutionPipeline evolutionaryOperator = generatePipeline(gameLength);

		CandidateFactory<List<Order>> candidateFactory = new OrderListFactory(
				gameLength, 200);
		EvolutionEngine<List<Order>> engine = new GenerationalEvolutionEngine<List<Order>>(
				candidateFactory, evolutionaryOperator, cachedEvaluator,
				new RouletteWheelSelection(), new MersenneTwisterRNG());

		return engine;
	}

	private EvolutionPipeline generatePipeline(Integer gameLength) {
		List<EvolutionaryOperator<List<Order>>> pipeline = new ArrayList<EvolutionaryOperator<List<Order>>>();
		pipeline.add(new ListCrossover());
		pipeline.add(new ProtectedListInversion(new Probability(0.5)));
		pipeline.add(new AddGene(gameLength, new Probability(0.5)));
		// pipeline.add(new ModifyOperand(realm, new Probability(0.2)));
		pipeline.add(new RemoveGene(new Probability(0.5)));
		EvolutionPipeline evolutionaryOperator = new EvolutionPipeline(pipeline);
		return evolutionaryOperator;
	}

}
