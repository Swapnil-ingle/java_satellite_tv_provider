package com.github.swapnil.service;

import java.util.List;

import com.github.swapnil.model.Subscription;
import com.github.swapnil.model.SubscriptionType;

public interface SatTvService {
	public List<Subscription> getAvailableBasePacks();

	public Subscription getBasePack(SubscriptionType type);
}
