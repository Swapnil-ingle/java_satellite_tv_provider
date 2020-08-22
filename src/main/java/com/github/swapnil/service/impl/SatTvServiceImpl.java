package com.github.swapnil.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.swapnil.model.Subscription;
import com.github.swapnil.model.SubscriptionType;
import com.github.swapnil.service.SatTvService;
import com.github.swapnil.utility.TvChannelUtility;

public class SatTvServiceImpl implements SatTvService {

	@Override
	public List<Subscription> getAvailableBasePacks() {
		List<Subscription> subscriptions = new ArrayList<>();
		Subscription silver = getBasePack(SubscriptionType.SILVER);
		Subscription gold = getBasePack(SubscriptionType.GOLD);
		Subscription platinum = getBasePack(SubscriptionType.PLATINUM);

		subscriptions.add(silver);
		subscriptions.add(gold);
		subscriptions.add(platinum);

		return subscriptions;
	}

	@Override
	public Subscription getBasePack(SubscriptionType type) {
		Subscription subscription = null;
		switch (type) {
		case SILVER:
			subscription = new Subscription();
			subscription.setType(type);
			subscription.setPrice(50L);
			subscription.setChannels(TvChannelUtility.getInstance().getTvChannelsByType(type));
			break;
		case GOLD:
			subscription = new Subscription();
			subscription.setType(type);
			subscription.setPrice(100L);
			subscription.setChannels(TvChannelUtility.getInstance().getTvChannelsByType(type));
			break;
		case PLATINUM:
			subscription = new Subscription();
			subscription.setType(type);
			subscription.setPrice(200L);
			subscription.setChannels(TvChannelUtility.getInstance().getTvChannelsByType(type));
			break;
		default:
			System.out.println("Invalid base pack type: " + type);
			break;
		}

		return subscription;
	}
}
