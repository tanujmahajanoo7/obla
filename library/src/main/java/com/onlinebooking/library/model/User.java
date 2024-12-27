package com.onlinebooking.library.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
    private String userId;
    private String name;
    private String subscriptionPlan; // SILVER, GOLD, PLATINUM
    private int age;
    private int borrowedBooks;
    private int borrowedMagazines;
    private int transactionsThisMonth;
    @OneToMany
    private List<Item> borrowedItems;

    // Getters, setters, constructors
    public String getUserId() {
    	return userId;
    }
    public void setUserId(String userId) {
    	this.userId = userId;
    }
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public String getSubscriptionPlan() {
    	return subscriptionPlan;
    }
    public void setSubscriptionPlan(String subscriptionPlan) {
    	this.subscriptionPlan = subscriptionPlan;
    }
    public int getAge() {
    	return age;
    }
    public void setAge(int age) {
    	this.age = age;
    }
    public int getBorrowedBooks() {
    	return borrowedBooks;
    }
    public void setBorrowedBooks(int borrowedBooks) {
    	this.borrowedBooks = borrowedBooks;
    }
    public int getBorrowedMagazines() {
    	return borrowedMagazines;
    }
    public void setBorrowedMagazines(int borrowedMagazines) {
    	this.borrowedMagazines = borrowedMagazines;
    }
    public int getTransactionsThisMonth() {
    	return transactionsThisMonth;
    }
    public void setTransactionsThisMonth(int transactionsThisMonth) {
    	this.transactionsThisMonth = transactionsThisMonth;
    }
	public List<Item> getBorrowedItems() {
		return borrowedItems;
	}
	public void setBorrowedItems(List<Item> borrowedItems) {
		this.borrowedItems = borrowedItems;
	}
	public User(String userId, String name, String subscriptionPlan, int age, int borrowedBooks, int borrowedMagazines,
			int transactionsThisMonth) {
		super();
		this.userId = userId;
		this.name = name;
		this.subscriptionPlan = subscriptionPlan;
		this.age = age;
		this.borrowedBooks = borrowedBooks;
		this.borrowedMagazines = borrowedMagazines;
		this.transactionsThisMonth = transactionsThisMonth;
	}
	public User(String userId, String name, String subscriptionPlan, int age, int borrowedBooks, int borrowedMagazines,
			int transactionsThisMonth, List<Item> borrowedItems) {
		super();
		this.userId = userId;
		this.name = name;
		this.subscriptionPlan = subscriptionPlan;
		this.age = age;
		this.borrowedBooks = borrowedBooks;
		this.borrowedMagazines = borrowedMagazines;
		this.transactionsThisMonth = transactionsThisMonth;
		this.borrowedItems = borrowedItems;
	}
	public User() {
		
	}
}