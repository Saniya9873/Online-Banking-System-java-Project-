package com.user.model;

public class UserAccountInfo {
    private String name;
    private String email;
    private String accountNumber;
    private double balance;

    public UserAccountInfo(String name, String email, String accountNumber, double balance) {
        this.name = name;
        this.email = email;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}
