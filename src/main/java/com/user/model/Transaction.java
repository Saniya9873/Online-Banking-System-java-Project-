package com.user.model;

import java.sql.Timestamp;

public class Transaction {
    private Long id;
    private String senderAccount;
    private String receiverAccount;
    private Double amount;
    private Timestamp dateTime;

    // Default constructor
    public Transaction() {
    }

    // Constructor with fields
    public Transaction(String senderAccount, String receiverAccount, Double amount, Timestamp dateTime) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    // Constructor with all fields including id
    public Transaction(Long id, String senderAccount, String receiverAccount, Double amount, Timestamp dateTime) {
        super();
        this.id = id;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", senderAccount=" + senderAccount + ", receiverAccount=" + receiverAccount
                + ", amount=" + amount + ", dateTime=" + dateTime + "]";
    }
}