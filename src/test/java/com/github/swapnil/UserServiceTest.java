package com.github.swapnil;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import com.github.swapnil.exception.ChannelAlreadySubscribedException;
import com.github.swapnil.exception.NoBasePackEnabledException;
import com.github.swapnil.exception.NotEnoughBalanceException;
import com.github.swapnil.exception.SubscriptionExpiredException;
import com.github.swapnil.model.Subscription;
import com.github.swapnil.model.SubscriptionType;
import com.github.swapnil.model.TvChannel;
import com.github.swapnil.model.User;
import com.github.swapnil.service.SatTvService;
import com.github.swapnil.service.UserService;
import com.github.swapnil.service.impl.SatTvServiceImpl;
import com.github.swapnil.service.impl.UserServiceImpl;
import com.github.swapnil.utility.TvChannelUtility;

public class UserServiceTest {
	private UserService userSvc = new UserServiceImpl();

	private SatTvService satTvSvc = new SatTvServiceImpl();

	@Test
	public void userCanRechargeWithPositiveBalanceTest() {
		User user = new User();
		userSvc.recharge(user, 100L);
		assertEquals(200L, user.getBalance().longValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void userCannotRechargeWithNegativeBalanceTest() {
		User user = new User();
		userSvc.recharge(user, -100L);
	}

	@Test
	public void amountDeductsWhenUserSubscribeToBasePackTest() {
		User user = new User();
		userSvc.subscribe(user, getSilverSubscription(), 1L);
		assertEquals(50L, user.getBalance().longValue());
	}

	@Test(expected = NotEnoughBalanceException.class)
	public void cannotSubscribeIfNotEnoughBalTest() {
		User user = new User();
		userSvc.subscribe(user, getGoldSubscription(), 3L);
	}

	@Test
	public void userGet10PerDiscountIfDurationIsAtLeast3MonthsTest() {
		User user = new User();
		userSvc.recharge(user, 50L);
		userSvc.subscribe(user, getSilverSubscription(), 3L);
		assertEquals(15L, user.getBalance().longValue());
	}

	@Test
	public void userAddIndividualChannelTest() {
		User user = new User();
		userSvc.subscribe(user, getSilverSubscription(), 1L);
		TvChannel channel = TvChannelUtility.getInstance().getAvailableTvChannels().get("Discovery");
		userSvc.addChannelToSubscription(user, channel);
		assertEquals(40L, user.getBalance().longValue());
	}

	@Test(expected = NoBasePackEnabledException.class)
	public void userAddingIndividualChannelShouldFailIfNoBpEnabledTest() {
		User user = new User();
		TvChannel channel = TvChannelUtility.getInstance().getAvailableTvChannels().get("DD Gold");
		userSvc.addChannelToSubscription(user, channel);
	}

	@Test(expected = NotEnoughBalanceException.class)
	public void userAddingIndividualChannelShouldFailIfNotEnoughBalance() {
		User user = new User();
		userSvc.subscribe(user, getGoldSubscription(), 1L);
		TvChannel channel = TvChannelUtility.getInstance().getAvailableTvChannels().get("DD Gold");
		userSvc.addChannelToSubscription(user, channel);
	}

	@Test(expected = ChannelAlreadySubscribedException.class)
	public void userCannotAddSameChannelTwice() {
		User user = new User();
		userSvc.subscribe(user, getSilverSubscription(), 1L);
		TvChannel channel = TvChannelUtility.getInstance().getAvailableTvChannels().get("Zee");
		userSvc.addChannelToSubscription(user, channel);
		System.out.println(user.getSubscription().getChannels());
	}

	@Test(expected = SubscriptionExpiredException.class)
	public void userCannotAddChannelsIfSubscriptionExpired() {
		User user = new User();
		userSvc.subscribe(user, getSilverSubscription(), 1L);
		user.getSubscription().setExpiresAt(Instant.now().minus(2, ChronoUnit.DAYS));
		TvChannel channel = TvChannelUtility.getInstance().getAvailableTvChannels().get("Discovery");
		userSvc.addChannelToSubscription(user, channel);
	}
	
	private Subscription getGoldSubscription() {
		return satTvSvc.getBasePack(SubscriptionType.GOLD);
	}

	private Subscription getSilverSubscription() {
		return satTvSvc.getBasePack(SubscriptionType.SILVER);
	}
}