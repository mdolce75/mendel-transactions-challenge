package com.mendel.transactions.dto;

public record TransactionRequest(
        double amount,
        String type,
        Long parent_id
) {}