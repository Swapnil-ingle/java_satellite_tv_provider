package com.github.swapnil.exception;

public class NotEnoughBalanceException extends SatTvException {
	private static final long serialVersionUID = 1L;

	public NotEnoughBalanceException(String msg) {
		super(msg + " - Not enough balance. Please recharge your account!");
	}
}
