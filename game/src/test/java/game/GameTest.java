package game;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Map<BusinessType, Integer> possessions = new HashMap<BusinessType, Integer>();

	@Before
	public void init() {
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
	public void initialPortfolioHasOneLemonStand() {
		// Given
		Game game = new Game();
		// When
		Integer numberOfLemonStand = game.numberOf(BusinessType.LEMON_STAND);
		// Then
		assertEquals(Integer.valueOf(1), numberOfLemonStand);
		assertEquals(Integer.valueOf(0), game.numberOf(BusinessType.NEWSPAPER_DELIVERY));
	}

	
	@Test
	public void buyALemonStand() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("4");
		// When
		game.buy(BusinessType.LEMON_STAND);
		// Then
		assertEquals(Integer.valueOf(2), game.numberOf(BusinessType.LEMON_STAND));
	}

	@Test
	public void buyANewsPapperStand() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("60");
		// When
		game.buy(BusinessType.NEWSPAPER_DELIVERY);
		// Then
		assertEquals(Integer.valueOf(1), game.numberOf(BusinessType.NEWSPAPER_DELIVERY));
	}

	@Test
	public void buyANewsPapperStandNotEnoughMoney() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("59");
		// When
		game.buy(BusinessType.NEWSPAPER_DELIVERY);
		// Then
		assertEquals(Integer.valueOf(1), game.numberOf(BusinessType.LEMON_STAND));
	}

	@Test
	public void buyASecondPapperDeliveryNotEnoughMoney() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("68");
		addBusinessToPortfolio(game, BusinessType.NEWSPAPER_DELIVERY, 1);
		// When
		game.buy(BusinessType.NEWSPAPER_DELIVERY);
		// Then
		assertEquals(Integer.valueOf(1), game.numberOf(BusinessType.NEWSPAPER_DELIVERY));
	}

	@Test
	public void buyALemonStandDecreaseMoney() {
		// Given
		Game game = new Game();
		game.moneyInHand = new BigDecimal("4");
		// When
		game.buy(BusinessType.LEMON_STAND);
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
		game.buy(BusinessType.LEMON_STAND);

		BigDecimal actualMoney = game.moneyInHand;
		BigDecimal expectedMoney = new BigDecimal("2");

		possessions.put(BusinessType.LEMON_STAND, 1);

		// Then
		assertEquals(expectedMoney, actualMoney);
		assertEquals(Integer.valueOf(1), game.numberOf(BusinessType.LEMON_STAND));
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
		addBusinessToPortfolio(game, BusinessType.NEWSPAPER_DELIVERY, 1);
		game.moneyInHand = new BigDecimal("0");
		// When
		game.doNothingFor(4);
		BigDecimal actual = game.cash();
		actual = actual.subtract(BusinessType.LEMON_STAND.profitWaitingFor(4));
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
		addBusinessToPortfolio(game, BusinessType.LEMON_STAND, 1);
		game.moneyInHand = new BigDecimal("10");
		// When
		game.doNothingFor(1);
		BigDecimal actual = game.cash();
		// Then
		BigDecimal expected = new BigDecimal("12");
		assertEquals(expected, actual);
	}

	private void addBusinessToPortfolio(Game game, BusinessType businessType,
			Integer number) {
		for (int i = 0; i < number; i++) {
			game.addBusiness(businessType);
		}

	}
}
