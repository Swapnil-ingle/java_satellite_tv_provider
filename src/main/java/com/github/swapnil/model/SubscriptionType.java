package com.github.swapnil.model;

public enum SubscriptionType {
	SILVER(0), GOLD(1), PLATINUM(2);

	private int numVal;

	SubscriptionType(int numVal) {
		this.numVal = numVal;
	}

	public int getNumVal() {
		return this.numVal;
	}
}
