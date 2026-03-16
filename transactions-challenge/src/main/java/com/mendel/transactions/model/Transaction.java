package com.mendel.transactions.model;

public class Transaction {

    private Long id;
    private double amount;
    private String type;
    private Long parentId;

    public Transaction() {
    }

    public Transaction(Long id, double amount, String type, Long parentId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}