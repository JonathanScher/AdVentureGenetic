package evolution.orders;

import game.Game;

import java.util.HashMap;
import java.util.Map;

public class Wait implements Order {
	private static final Map<Integer, Wait> waitOrders;

	static{
		waitOrders = new HashMap<Integer, Wait>();
	}
	
	private int timeToWait;

	public static Wait getInstance(int timeToWait){
		Wait waitOrder = waitOrders.get(timeToWait);
		if (waitOrder == null){
			waitOrder = new Wait(timeToWait);
			waitOrders.put(timeToWait, waitOrder);
		}
		return waitOrder;
	}
	
	private Wait(int timeToWait) {
		this.timeToWait = timeToWait;
	}

	public boolean execute(Game game) {
		game.doNothingFor(timeToWait);
		return true;
	}

	public double length() {
		return timeToWait;
	}

	@Override
	public String toString() {
		return "Wait: " + timeToWait;
	}
}
