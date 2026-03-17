package com.mendel.transactions.repository;

import com.mendel.transactions.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {

    private final Map<Long, Transaction> transactions = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        transactions.put(transaction.id(), transaction);
    }

    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(transactions.get(id));
    }

    public List<Long> findByType(String type) {
        return transactions.values()
                .stream()
                .filter(t -> t.type().equals(type))
                .map(Transaction::id)
                .toList();
    }

    public List<Transaction> findChildren(Long parentId) {
        return transactions.values()
                .stream()
                .filter(t -> Objects.equals(t.parentId(), parentId))
                .toList();
    }

}