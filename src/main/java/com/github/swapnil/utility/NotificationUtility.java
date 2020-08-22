package com.github.swapnil.utility;

import com.github.swapnil.model.User;

public class NotificationUtility {
	private static volatile NotificationUtility instance;

	private NotificationUtility() {
	}

	public static NotificationUtility getInstance() {
		if (instance == null) {
			synchronized (NotificationUtility.class) {
				if (instance == null) {
					instance = new NotificationUtility();
				}
			}
		}
		return instance;
	}

	public void sendEmailNotification(User user) {
		System.out.println(String.format("Email notification sent to user%s!",
				user.getEmail() != null ? " (Email: " + user.getEmail() + ")" : ""));
	}

	public void sendSMSNotification(User user) {
		System.out.println(String.format("SMS notification sent to phone%s!",
				user.getPhone() != null ? " (Phone: " + user.getPhone() + ")" : ""));
	}
}
