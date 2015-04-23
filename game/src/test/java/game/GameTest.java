package game;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GameTest {
	
	private Map<Business, Integer> possessions = new HashMap<Business, Integer>();
	@Before
	public void init(){
	}
	
	@Test
	public void setUpInitialMoney() {
		// Given
		Game game = new Game();
		// When
		BigDecimal actual = game.cash();
		// Then
		BigDecimal expected = new BigDecimal("1");
		assertEquals(expected, actual);
	}

	@Test
	public void setUpInitialPossessions() {
		// Given
		Game game = new Game();
		// When
		Map<Business, Integer> actual = game.businessesOwned();
		// Then
		possessions.put(Business.LEMON_STAND, 1);
		assertEquals(possessions, actual);
	}

	@Test
	public void buyALemonStand() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("4");
		// When
		game.buy(Business.LEMON_STAND);
		// Then
		Map<Business, Integer> actual = game.businessesOwned();
		possessions.put(Business.LEMON_STAND, 2);
		assertEquals(possessions, actual);
	}
	
	@Test
	public void buyANewsPapperStand() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("60");
		// When
		game.buy(Business.NEWSPAPER_DELIVERY);
		// Then
		Map<Business, Integer> actual = game.businessesOwned();
		possessions.put(Business.LEMON_STAND, 1);
		possessions.put(Business.NEWSPAPER_DELIVERY, 1);
		assertEquals(possessions, actual);
	}
	
	@Test
	public void buyANewsPapperStandNotEnoughMoney() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("59");
		// When
		game.buy(Business.NEWSPAPER_DELIVERY);
		// Then
		Map<Business, Integer> actual = game.businessesOwned();
		possessions.put(Business.LEMON_STAND, 1);
		assertEquals(possessions, actual);
	}

	@Test
	public void buyASecondPapperDeliveryNotEnoughMoney() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("68");
		game.businessesOwned().put(Business.NEWSPAPER_DELIVERY, 1);
		// When
		game.buy(Business.NEWSPAPER_DELIVERY);
		// Then
		Map<Business, Integer> actual = game.businessesOwned();
		possessions.put(Business.LEMON_STAND, 1);
		possessions.put(Business.NEWSPAPER_DELIVERY, 1);
		assertEquals(possessions, actual);
	}

	
	@Test
	public void buyALemonStandDecreaseMoney() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("4");
		// When
		game.buy(Business.LEMON_STAND);
		BigDecimal actual = game.moneyInHand;
		// Then
		BigDecimal expected = new BigDecimal("0.0");
		assertEquals(expected, actual);
	}

	@Test
	public void cannotBuyWithNotEnoughMoney() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("2");
		// When
		game.buy(Business.LEMON_STAND);

		BigDecimal actualMoney = game.moneyInHand;
		BigDecimal expectedMoney = new BigDecimal("2");

		Map<Business, Integer> actualPossessions = game.businessesOwned();
		possessions.put(Business.LEMON_STAND, 1);

		// Then
		assertEquals(expectedMoney, actualMoney);
		assertEquals(possessions, actualPossessions);
	}

	@Test
	public void moneyAfter1Second() {
		// Given
		Game game = new Game();
		// When
		game.doNothingFor(1);
		BigDecimal actual = game.cash();
		// Then
		BigDecimal expected = new BigDecimal("2");
		assertEquals(expected, actual);
	}
	
	@Test
	public void moneyAfter1SecondWithNewspapper() {
		// Given
		Game game = new Game();
		game.businessesOwned().put(Business.LEMON_STAND, 0);
		game.businessesOwned().put(Business.NEWSPAPER_DELIVERY, 1);
		game.moneyInHand = new BigDecimal("0");
		// When
		game.doNothingFor(4);
		BigDecimal actual = game.cash();
		// Then
		BigDecimal expected = new BigDecimal("60");
		assertEquals(expected, actual);
	}

	@Test
	public void moneyAfter2Second() {
		// Given
		Game game = new Game();
		// When
		game.doNothingFor(2);
		BigDecimal actual = game.cash();
		// Then
		BigDecimal expected = new BigDecimal("4");
		assertEquals(expected, actual);
	}

	@Test
	public void moneyAfter1SecondWithTwoLemonadeStand() {
		// Given
		Game game = new Game();
		game.businessesOwned().put(Business.LEMON_STAND, 2);
		game.moneyInHand = new BigDecimal("10");
		// When
		game.doNothingFor(1);
		BigDecimal actual = game.cash();
		// Then
		BigDecimal expected = new BigDecimal("12");
		assertEquals(expected, actual);
	}
	
}
