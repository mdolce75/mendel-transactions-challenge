package com.mendel.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.transactions.dto.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTransaction() throws Exception {

        TransactionRequest request = new TransactionRequest(
                5000.0,
                "cars",
                null
        );

        mockMvc.perform(
                        put("/transactions/10")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTransactionsByType() throws Exception {

        TransactionRequest request = new TransactionRequest(
                5000.0,
                "cars",
                null
        );

        mockMvc.perform(
                put("/transactions/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        mockMvc.perform(
                        get("/transactions/types/cars")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(10));
    }

    @Test
    void shouldCalculateTransactionSum() throws Exception {

        TransactionRequest parent = new TransactionRequest(
                5000.0,
                "cars",
                null
        );

        TransactionRequest child1 = new TransactionRequest(
                10000.0,
                "shopping",
                10L
        );

        TransactionRequest child2 = new TransactionRequest(
                5000.0,
                "shopping",
                11L
        );

        mockMvc.perform(
                put("/transactions/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parent))
        );

        mockMvc.perform(
                put("/transactions/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(child1))
        );

        mockMvc.perform(
                put("/transactions/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(child2))
        );

        mockMvc.perform(
                        get("/transactions/sum/10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(20000));
    }

}