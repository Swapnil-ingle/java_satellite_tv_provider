package com.github.swapnil.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.github.swapnil.exception.SatTvException;
import com.github.swapnil.model.Subscription;
import com.github.swapnil.model.SubscriptionType;
import com.github.swapnil.model.User;
import com.github.swapnil.service.SatTvService;
import com.github.swapnil.service.UserService;
import com.github.swapnil.service.impl.SatTvServiceImpl;
import com.github.swapnil.service.impl.UserServiceImpl;
import com.github.swapnil.utility.TvChannelUtility;

public class Driver {
	public static void main(String[] args) {
		Processor processor = new Processor();
		System.out.println(BANNER);
		boolean run = true;
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		while (run) {
			try {
				int choice = Integer.valueOf(sc.readLine());

				switch (choice) {
				case 0:
					System.out.println(BANNER);
				case 1:
					processor.viewAccountBalanceAndCurrSubscription();
					break;
				case 2:
					System.out.println("Enter amount to recharge:");
					int amount = Integer.valueOf(sc.readLine());
					processor.rechargeAccount(amount);
					break;
				case 3:
					System.out.println("Available packs for subscription");
					processor.viewAvailablePackAndChannels();
					break;
				case 4:
					System.out.println("Enter pack you wish to subscribe: (Silver: 'S', Gold: 'G', Platinum: 'P')");
					String enteredType = sc.readLine();
					SubscriptionType type = enteredType.equalsIgnoreCase("S") ? SubscriptionType.SILVER
							: (enteredType.equalsIgnoreCase("G") ? SubscriptionType.GOLD
									: (enteredType.equalsIgnoreCase("P") ? SubscriptionType.PLATINUM : null));

					if (type == null) {
						throw new SatTvException("Invalid Subscription (base-pack) choosen. Please try again");
					}
					
					System.out.println("Enter the number of months");
					int forMonths = Integer.valueOf(sc.readLine());

					processor.subscribeToBasePack(type, forMonths);
					break;
				case 5:
					System.out.println("Enter the channel names (Separated by comma)");
					String input = sc.readLine();
					String[] channels = input.contains(",") ? input.split(",") : new String[] {input};
					processor.addChannelsToSubscription(channels);
					break;
				case 6:
					System.out.println("Update email and phone-number for notification");
					System.out.println("Enter phone");
					Long phone = Long.valueOf(sc.readLine());
					System.out.println("Enter email");
					String email = sc.readLine();
					
					processor.editUserPhoneAndEmail(phone, email);
					break;
				case 7:
					run = false;
					System.out.println("System will now exit!");
					break;
				default:
					System.out.println("Please enter a valid choice!");
					System.out.println(BANNER);
					break;
				}
			} catch (SatTvException satTvException) {
				System.out.println(satTvException.getMessage());
			} catch (Exception e) {
				System.out.println("Fatal Error: ");
				e.printStackTrace();
			}
		}

		closeQuietly(sc);
	}

	private static void closeQuietly(BufferedReader sc) {
		try {
			sc.close();
		} catch (IOException e) {
			// Ignore
		}
	}

	private static final String BANNER = 
			"############### Welcome to SatTV ###############\n" + 
			"0. See this menu \n" +
			"1. View account balance and current subscription \n" + 
			"2. Recharge account \n" + 
			"3. View available packs and channels \n" + 
			"4. Subscribe to base packs \n" + 
			"5. Add channels to existing subscription \n" + 
			"6. Update email and phone number for notification \n" + 
			"7. Exit";
}

class Processor {
	private User user;

	private final UserService userSvc = new UserServiceImpl();

	private final SatTvService satTvSvc = new SatTvServiceImpl();

	public Processor() {
		user = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void viewAccountBalanceAndCurrSubscription() {
		System.out.println("Account balance is Rs. " + user.getBalance());
		if (user.getSubscription() != null) {
			System.out.println("Current subscription: " + user.getSubscription());
			return;
		}

		System.out.println("Current subscription: Not Subscribed!");
	}

	public void rechargeAccount(int amount) {
		userSvc.recharge(user, amount);
	}

	public void viewAvailablePackAndChannels() {
		List<Subscription> basePacks = satTvSvc.getAvailableBasePacks();
		basePacks.forEach(pack -> System.out.println(pack.toString()));
	}

	public void subscribeToBasePack(SubscriptionType type, int forMonths) {
		userSvc.subscribe(user, satTvSvc.getBasePack(type), Long.valueOf(forMonths));
	}

	public void addChannelsToSubscription(String[] channels) {
		boolean channelsAreAvail = TvChannelUtility.getInstance().areChannelAvailable(Arrays.asList(channels));

		if (!channelsAreAvail) {
			System.out.println("Check section#4 - 'Available packs and channels'");
			return;
		}

		userSvc.addChannelsToSubscription(user, TvChannelUtility.getInstance().getChannelsFromName(Arrays.asList(channels)));
	}

	public void editUserPhoneAndEmail(Long phone, String email) {
		user.setPhone(phone);
		user.setEmail(email);
		System.out.println("Email and phone updated successfully!");
	}
}
