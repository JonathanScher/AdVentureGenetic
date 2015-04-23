package evolution.orders;

import java.util.HashMap;
import java.util.Map;

import game.Business;
import game.Game;

public class Buy implements Order {
	private static final Map<Business, Buy> businesses;
	private Business business;
	
	static{
		businesses = new HashMap<Business, Buy>();
		for(Business business:Business.values()){
			businesses.put(business, new Buy(business));
		}
	}
	
	public static final Buy getInstance(Business business) {
		return businesses.get(business);
	}
	
	private Buy(Business business) {
		this.business = business;
	}

	public boolean execute(Game game) {
		return game.buy(business);
	}

	public double length() {
		return 0;
	}

	@Override
	public String toString() {
		return "Buy "+ business;
	}
	

}
