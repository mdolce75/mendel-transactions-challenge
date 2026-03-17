package com.mendel.transactions.service;

import com.mendel.transactions.dto.TransactionRequest;
import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionRepository repository;
    private TransactionService service;

    @BeforeEach
    void setup() {
        repository = mock(TransactionRepository.class);
        service = new TransactionService(repository);
    }

    @Test
    void shouldSaveTransaction() {

        Transaction transaction = new Transaction(
                1L,
                5000.0,
                "cars",
                null
        );

        service.createTransaction( transaction);

        verify(repository, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldCalculateSum() {

        Transaction parent = new Transaction(10L, 5000.0, "cars", null);
        Transaction child = new Transaction(11L, 10000.0, "shopping", 10L);

        when(repository.findById(10L)).thenReturn(Optional.of(parent));
        when(repository.findChildren(10L)).thenReturn(java.util.List.of(child));
        when(repository.findChildren(11L)).thenReturn(java.util.List.of());

        double sum = service.getTransactionSum(10L);

        assertThat(sum).isEqualTo(15000.0);
    }

}