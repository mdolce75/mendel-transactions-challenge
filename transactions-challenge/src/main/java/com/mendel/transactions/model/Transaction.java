package com.mendel.transactions.model;

public record Transaction(
        Long id,
        double amount,
        String type,
        Long parentId
) {
}