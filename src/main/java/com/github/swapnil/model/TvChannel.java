package com.github.swapnil.model;

public class TvChannel {
	private String name;

	private Long number;

	private Long price;

	private SubscriptionType type;

	public TvChannel(String name, Long number, Long price, SubscriptionType type) {
		this.name = name;
		this.number = number;
		this.price = price;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public SubscriptionType getType() {
		return type;
	}

	public void setType(SubscriptionType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TvChannel [name=" + name + ", number=" + number + ", price=" + price + ", type=" + type + "]";
	}
}
