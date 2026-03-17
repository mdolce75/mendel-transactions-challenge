package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;


@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void createTransaction(Transaction transaction) {
        repository.save(transaction);
    }

    public List<Long> getTransactionsByType(String type) {
        return repository.findByType(type);
    }

    public double getTransactionSum(Long transactionId) {

        Optional<Transaction> rootOpt = repository.findById(transactionId);

        if (rootOpt.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;

        Queue<Long> queue = new LinkedList<>();
        queue.add(transactionId);

        while (!queue.isEmpty()) {

            Long currentId = queue.poll();

            Optional<Transaction> transaction = repository.findById(currentId);

            if (transaction.isPresent()) {
                sum += transaction.get().amount();
            }

            List<Transaction> children = repository.findChildren(currentId);

            for (Transaction child : children) {
                queue.add(child.id());
            }
        }

        return sum;
    }
}