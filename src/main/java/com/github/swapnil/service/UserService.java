package com.github.swapnil.service;

import java.util.List;

import com.github.swapnil.model.Subscription;
import com.github.swapnil.model.TvChannel;
import com.github.swapnil.model.User;

public interface UserService {
	public void recharge(User user, long amount);

	public void subscribe(User user, Subscription subscription, Long months);

	public void addChannelsToSubscription(User user, List<TvChannel> channels);

	public void addChannelToSubscription(User user, TvChannel channel);
}
