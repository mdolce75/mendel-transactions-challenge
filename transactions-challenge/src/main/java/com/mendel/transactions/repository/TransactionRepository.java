package com.mendel.transactions.repository;

import com.mendel.transactions.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {

    private final Map<Long, Transaction> transactions = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(transactions.get(id));
    }

    public Collection<Transaction> findAll() {
        return transactions.values();
    }
}