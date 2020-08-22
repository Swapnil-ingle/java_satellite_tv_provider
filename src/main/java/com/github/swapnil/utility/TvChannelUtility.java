package com.github.swapnil.utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.swapnil.model.SubscriptionType;
import com.github.swapnil.model.TvChannel;

public class TvChannelUtility {
	private static volatile TvChannelUtility instance;
	
	private Map<String, TvChannel> channels = new HashMap<>();

	private TvChannelUtility() {
		channels.put("Zee", new TvChannel("Zee", 100L, 10L, SubscriptionType.SILVER));
		channels.put("Sony", new TvChannel("Sony", 101L, 15L, SubscriptionType.SILVER));
		channels.put("Star Plus", new TvChannel("Star Plus", 102L, 20L, SubscriptionType.SILVER));
		channels.put("Discovery", new TvChannel("Discovery", 103L, 10L, SubscriptionType.GOLD));
		channels.put("NatGeo", new TvChannel("NatGeo", 104L, 20L, SubscriptionType.GOLD));
		channels.put("DD Gold", new TvChannel("DD Gold", 105L, 100L, SubscriptionType.PLATINUM));
		channels.put("ESPN", new TvChannel("ESPN", 106L, 25L, SubscriptionType.PLATINUM));
	}

	public static TvChannelUtility getInstance() {
		if (instance == null) {
			synchronized (TvChannelUtility.class) {
				if (instance == null) {
					instance = new TvChannelUtility();
				}
			}
		}
		return instance;
	}

	public Map<String, TvChannel> getAvailableTvChannels() {
		return channels;
	}

	public List<TvChannel> getTvChannelsByType(SubscriptionType type) {
		return channels.values().stream()
			.map(getChannelIfTypeIsCompatible(type))
			.filter(channel -> channel != null)
			.collect(Collectors.toList());
	}

	private Function<? super TvChannel, ? extends TvChannel> getChannelIfTypeIsCompatible(SubscriptionType type) {
		return channel -> {
			if (channel.getType().getNumVal() <= type.getNumVal()) {
				return channel;
			}

			return null;
		};
	}

	public boolean areChannelAvailable(Collection<String> channels) {
		Set<String> channelNames = this.channels.keySet();
		Set<String> notExistingChannels = channels.stream().filter(channel -> !channelNames.contains(channel.trim())).collect(Collectors.toSet());

		if (notExistingChannels.size() > 0) {
			System.out.println("Following channels are not provided from SatTv provider - ");
			System.out.println(notExistingChannels);
			return false;
		}

		return true;
	}

	public boolean isChannelAvailable(String channel) {
		return this.channels.containsKey(channel);
	}

	public List<TvChannel> getChannelsFromName(Collection<String> names) {
		return names.stream().map(name -> this.channels.get(name.trim())).collect(Collectors.toList());
	}

	public TvChannel getChannelFromName(String name) {
		return channels.get(name);
	}
}
