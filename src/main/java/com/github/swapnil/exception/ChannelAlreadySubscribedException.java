package com.github.swapnil.exception;

public class ChannelAlreadySubscribedException extends SatTvException {
	private static final long serialVersionUID = 1L;

	public ChannelAlreadySubscribedException(String msg) {
		super(msg + " - Channel already subscribed!");
	}
}
