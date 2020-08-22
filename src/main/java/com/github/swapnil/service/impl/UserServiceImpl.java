package com.github.swapnil.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.github.swapnil.exception.ChannelAlreadySubscribedException;
import com.github.swapnil.exception.NoBasePackEnabledException;
import com.github.swapnil.exception.NotEnoughBalanceException;
import com.github.swapnil.exception.SubscriptionExpiredException;
import com.github.swapnil.model.ActivityStatus;
import com.github.swapnil.model.Subscription;
import com.github.swapnil.model.TvChannel;
import com.github.swapnil.model.User;
import com.github.swapnil.service.UserService;
import com.github.swapnil.utility.NotificationUtility;

public class UserServiceImpl implements UserService {
	@Override
	public void recharge(User user, long amount) {
		if (amount <= 0L) {
			throw new IllegalArgumentException("Cannot recharge with amout < 0");
		}

		user.setBalance(user.getBalance() + amount);
		System.out.println(String.format("Recharge completed successfully. (Current Balance: %d)", user.getBalance()));
	}

	@Override
	public void subscribe(User user, Subscription subscription, Long durationInMonths) {
		Long totalAmount = subscription.getPrice() * durationInMonths;
		Long totalPayableAmount = subscription.getPrice() * durationInMonths;
		boolean discountApplied = false;

		if (user.getBalance() < totalPayableAmount) {
			throw new NotEnoughBalanceException(String.format("Cannot subscribe to '%s' pack", subscription.getType()));
		}

		if (durationInMonths >= 3) {
			discountApplied = true;
			totalPayableAmount = (long) (totalPayableAmount * 0.9);
		}

		user.setBalance(user.getBalance() - totalPayableAmount);
		user.setSubscription(subscription);
		user.getSubscription().setActivityStatus(ActivityStatus.Active);
		user.getSubscription().setExpiresAt(Instant.now().plus(durationInMonths * 30, ChronoUnit.DAYS));

		// Logging output
		logSubscriptionMsgs(user, subscription, durationInMonths, totalAmount, totalPayableAmount, discountApplied);

		// Send Notification messages
		NotificationUtility.getInstance().sendEmailNotification(user);
		NotificationUtility.getInstance().sendSMSNotification(user);
	}

	@Override
	public void addChannelsToSubscription(User user, List<TvChannel> channels) {
		channels.forEach(channel -> addChannelToSubscription(user, channel));
	}

	@Override
	public void addChannelToSubscription(User user, TvChannel channel) {
		if (user.getSubscription() == null) {
			throw new NoBasePackEnabledException(String.format("Cannot add new channel '%s'", channel.getName()));
		}

		if (Instant.now().compareTo(user.getSubscription().getExpiresAt()) > 0) {
			user.getSubscription().setActivityStatus(ActivityStatus.Disabled);
			throw new SubscriptionExpiredException(String.format("Cannot add new channel '%s'", channel.getName()));
		}

		if (user.getBalance() < channel.getPrice()) {
			throw new NotEnoughBalanceException(String.format("Cannot add new channel '%s'", channel.getName()));
		}

		Optional<TvChannel> channelAlready = user.getSubscription().getChannels().stream()
				.filter(existingChannel -> existingChannel.getName().equalsIgnoreCase(channel.getName())).findFirst();

		if (channelAlready.isPresent()) {
			throw new ChannelAlreadySubscribedException("Cannot add " + channel.getName());
		}

		user.getSubscription().addChannel(channel);
		System.out.println(String.format("Added channel %s (Rs. %s) to subscription", channel, channel.getPrice()));
		user.setBalance(user.getBalance() - channel.getPrice());
		System.out.println(String.format("Debited Rs. %s from user account. (Current balance: Rs. %s)", channel.getPrice(), user.getBalance()));
	}

	private void logSubscriptionMsgs(User user, Subscription subscription, Long durationInMonths, Long totalAmount,
			Long totalPayableAmount, boolean discountApplied) {
		System.out.println("#################################### Subscription successful ####################################");
		System.out.println(
				String.format("You have successfully subscribed to the following pack - '%s' (Monthly price: Rs. %s)",
						subscription.getType(), subscription.getPrice()));

		System.out.println(String.format("This pack is valid for %d months.", durationInMonths));
		System.out.println(String.format("This pack will expire on %s.",
				new Date(user.getSubscription().getExpiresAt().toEpochMilli())));
		System.out.println(String.format("Total subscription amount: Rs. %d.", totalAmount));

		if (discountApplied) {
			System.out.println(String.format("Discount applied Rs. %s.", ((long) totalAmount * 0.1)));
		}

		System.out.println(String.format("Final price%s: Rs. %d.", discountApplied ? " (After discount)" : "",
				totalPayableAmount));
		System.out.println("#################################### Subscription successful ####################################");
	}
}
