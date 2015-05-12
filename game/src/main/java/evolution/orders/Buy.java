package evolution.orders;

import java.util.HashMap;
import java.util.Map;

import game.BusinessType;
import game.Game;

public class Buy implements Order {
	private static final Map<BusinessType, Buy> businesses;
	private BusinessType business;
	
	static{
		businesses = new HashMap<BusinessType, Buy>();
		for(BusinessType business:BusinessType.values()){
			businesses.put(business, new Buy(business));
		}
	}
	
	private Buy(BusinessType business) {
		this.business = business;
	}
	
	public static final Buy getInstance(BusinessType business) {
		return businesses.get(business);
	}


	@Override
	public boolean execute(Game game) {
		return game.buy(business);
	}

	@Override
	public double length() {
		return 0;
	}

	@Override
	public String toString() {
		return "Buy "+ business;
	}
	

}
