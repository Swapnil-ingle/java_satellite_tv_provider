package com.github.swapnil.exception;

public class SubscriptionExpiredException extends SatTvException {
	private static final long serialVersionUID = 1L;

	public SubscriptionExpiredException(String msg) {
		super(msg + " - Subscription expired!");
	}
}
