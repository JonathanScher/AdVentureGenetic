package evolution;

import java.util.List;

import javax.swing.JFrame;

import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;
import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;

import evolution.orders.Order;

public class Main {
	public static void runGAI() {
		Genetics geneticAlgorithm = new Genetics();

		EvolutionMonitor<List<Order>> evolutionMonitor = new EvolutionMonitor<>();
		JFrame frame = new JFrame();
		frame.add(evolutionMonitor.getGUIComponent());
		frame.setVisible(true);
		frame.setSize(800, 600);

		
		TerminationCondition targetCondition = new ElapsedTime(Long.MAX_VALUE);
		// targetCondition = new GenerationCount(2000);
		geneticAlgorithm.run(evolutionMonitor, 10000, 1000, targetCondition);
		//geneticAlgorithm.runIsland(evolutionMonitor, 5, 1000/5, 100/5, 400, 100, targetCondition);
	}

	public static void main(String[] args) {
		runGAI();
	}
}
