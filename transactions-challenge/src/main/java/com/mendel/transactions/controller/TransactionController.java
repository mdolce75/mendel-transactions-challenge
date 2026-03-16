package com.mendel.transactions.controller;

import com.mendel.transactions.dto.SumResponse;
import com.mendel.transactions.dto.TransactionRequest;
import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final TransactionService service;

	public TransactionController(TransactionService service) {
		this.service = service;
	}

	@PutMapping("/{id}")
	public Map<String, String> create(
			@PathVariable Long id,
			@RequestBody TransactionRequest request
	) {

		Transaction transaction = new Transaction(
				id,
				request.amount(),
				request.type(),
				request.parent_id()
		);

		service.create(transaction);

		return Map.of("status", "ok");
	}

	@GetMapping("/types/{type}")
	public List<Long> findByType(@PathVariable String type) {
		return service.findByType(type);
	}

	@GetMapping("/sum/{id}")
	public SumResponse sum(@PathVariable Long id) {
		return new SumResponse(service.sum(id));
	}
}