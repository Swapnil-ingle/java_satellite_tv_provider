package com.github.swapnil.exception;

public class NoBasePackEnabledException extends SatTvException {
	private static final long serialVersionUID = 1L;

	public NoBasePackEnabledException(String msg) {
		super(msg + " - No base pack enabled!");
	}
}
