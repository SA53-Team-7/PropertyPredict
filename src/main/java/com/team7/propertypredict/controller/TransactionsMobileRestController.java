package com.team7.propertypredict.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.service.TransactionService;

@RestController
@RequestMapping("/api/mobile/transactions")
public class TransactionsMobileRestController {

	@Autowired
	private TransactionService tService;
	
	@GetMapping("/{id}")
	public List<Transaction> getTransactionsFromProject(@PathVariable("id") Integer id) {
		List<Transaction> transactions = tService.getTransactionsByProjectId(id);
		return transactions;
	}

	@GetMapping("/top/{id}")
	public List<Transaction> getTopTransactionByProjectId(@PathVariable("id") Integer id) {
		return tService.getTopTransactionByProjectId(id);
	}
}
