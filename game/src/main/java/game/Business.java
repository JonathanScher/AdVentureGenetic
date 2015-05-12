package game;

import java.math.BigDecimal;

public class Business {
	private BusinessType type;
	
	public Business(BusinessType type) {
		this.type = type;
	}

	public BigDecimal profitWaitingFor(int seconds) {
		return type.profitWaitingFor(seconds);
	}

	public String toString(){
		return type.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Business other = (Business) obj;
		if (type != other.type)
			return false;
		return true;
	}

}
