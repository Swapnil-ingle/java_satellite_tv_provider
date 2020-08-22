package com.github.swapnil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.swapnil.model.User;

public class UserTest {
	@Test
	public void userInitBalanceIs100Test() {
		User user = new User();
		assertEquals(100L, user.getBalance().longValue());
	}

	@Test
	public void userCanViewCurrentSubscriptionTest() {
		User user = new User();
		assertEquals(null, user.getSubscription());
	}

	@Test
	public void userCanViewCurrentBalTest() {
		User user = new User();
		user.setBalance(200L);
		assertEquals(200L, user.getBalance().longValue());
	}
}
