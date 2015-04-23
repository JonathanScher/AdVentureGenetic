package evolution.orders;

import game.Game;

public interface Order {
	boolean execute(Game game);

	double length();
}
