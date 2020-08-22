package com.github.swapnil.model;

public class User {
	private Long balance = 100L;

	private Subscription subscription;

	private String email;

	private Long phone;

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [balance=" + balance + ", subscription=" + subscription + ", email=" + email + ", phone=" + phone
				+ "]";
	}
}
