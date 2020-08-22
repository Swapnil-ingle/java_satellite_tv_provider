package com.github.swapnil.model;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class Subscription {
	private SubscriptionType type;

	private List<TvChannel> channels;

	private Long price;

	private Instant expiresAt;

	private ActivityStatus activityStatus;

	public Subscription() {
	}

	public SubscriptionType getType() {
		return type;
	}

	public void setType(SubscriptionType type) {
		this.type = type;
	}

	public List<TvChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<TvChannel> channels) {
		this.channels = channels;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public void addChannel(TvChannel channel) {
		this.channels.add(channel);
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
	}

	@Override
	public String toString() {
		String channelNames = channels.stream().map(TvChannel::getName).collect(Collectors.joining(", "));
		return String.format("%s pack: %s. (Rs. %s)", type, channelNames, price);
	}
}
