package com.user.model;

public class Account {
    private String accountNumber;
    private Long userId;
    private String accountType;
    private Double balance;
    private String pin;

    // Default constructor
    public Account() {
    }

    // Constructor with fields
    public Account(String accountNumber,  String accountType, Double balance, String pin) {
        this.accountNumber = accountNumber;
        
        this.accountType = accountType;
        this.balance = balance;
        this.pin = pin;
    }

    // Constructor with all fields including accountNumber
    public Account(String accountNumber, Long userId, String accountType, Double balance, String pin) {
        super();
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.pin = pin;
    }

    public Account(String accountNumber, double balance) {
    	 this.accountNumber = accountNumber;
         this.balance = balance;
         }

	// Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Account [accountNumber=" + accountNumber + ", userId=" + userId + ", accountType=" + accountType
                + ", balance=" + balance + ", pin=" + pin + "]";
    }
}