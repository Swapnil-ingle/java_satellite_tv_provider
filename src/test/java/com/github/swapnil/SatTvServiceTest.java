package com.github.swapnil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.github.swapnil.model.Subscription;
import com.github.swapnil.model.SubscriptionType;
import com.github.swapnil.model.TvChannel;
import com.github.swapnil.service.SatTvService;
import com.github.swapnil.service.impl.SatTvServiceImpl;
import com.github.swapnil.utility.TvChannelUtility;

public class SatTvServiceTest {
	private SatTvService satTvSvc = new SatTvServiceImpl();

	@Test
	public void userShouldSeeAvailableBasePacksTest() {
		List<Subscription> basePacks = satTvSvc.getAvailableBasePacks();
		assertEquals(3, basePacks.size());
	}
	
	@Test
	public void getAllAvailableChannelsTest() {
		Map<String, TvChannel> channels = TvChannelUtility.getInstance().getAvailableTvChannels();
		assertEquals(7, channels.size());
	}

	@Test
	public void getAllAvailableChannelsInSilverPackTest() {
		List<TvChannel> channels = TvChannelUtility.getInstance().getTvChannelsByType(SubscriptionType.GOLD);
		assertEquals(5, channels.size());
	}
}
