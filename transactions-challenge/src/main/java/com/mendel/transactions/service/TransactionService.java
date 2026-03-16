package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void create(Transaction transaction) {
        repository.save(transaction);
    }

    public List<Long> findByType(String type) {

        List<Long> result = new ArrayList<>();

        for (Transaction transaction : repository.findAll()) {

            if (transaction.getType().equals(type)) {
                result.add(transaction.getId());
            }

        }

        return result;
    }

    public double sum(Long transactionId) {

        Map<Long, List<Transaction>> childrenMap = buildChildrenMap();

        Queue<Long> queue = new LinkedList<>();
        queue.add(transactionId);

        double sum = 0;

        while (!queue.isEmpty()) {

            Long currentId = queue.poll();

            Optional<Transaction> optionalTransaction =
                    repository.findById(currentId);

            if (optionalTransaction.isEmpty()) {
                continue;
            }

            Transaction transaction = optionalTransaction.get();

            sum += transaction.getAmount();

            List<Transaction> children =
                    childrenMap.getOrDefault(currentId, List.of());

            for (Transaction child : children) {
                queue.add(child.getId());
            }

        }

        return sum;
    }

    private Map<Long, List<Transaction>> buildChildrenMap() {

        Map<Long, List<Transaction>> map = new HashMap<>();

        for (Transaction transaction : repository.findAll()) {

            if (transaction.getParentId() != null) {

                map.computeIfAbsent(
                        transaction.getParentId(),
                        k -> new ArrayList<>()
                ).add(transaction);

            }

        }

        return map;
    }
}