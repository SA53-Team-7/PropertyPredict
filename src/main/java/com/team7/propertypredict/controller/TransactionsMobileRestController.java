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

	//pls remove later, testing only
	@GetMapping("/test")
	public List<Transaction> getAllTransactions() {
		return tService.findAllTransactions();
	}
	
//	@GetMapping("/{id}")
//	public ResponseEntity<Transaction> getTransactionsFromProject(@PathVariable("id") Integer id) {
//		Optional<Transaction> transactions = tService.getTransactionsByProjectId(id);
//
//		if (transactions.isPresent()) {
//			return new ResponseEntity<>(transactions.get(), HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
	
	@GetMapping("/{id}")
	public List<Transaction> getTransactionsFromProject(@PathVariable("id") Integer id) {
		List<Transaction> transactions = tService.getTransactionsByProjectId(id);
		return transactions;
	}
	
	@GetMapping("/district/{id}")
	public List<Transaction> getMobileRecommendationsDistrict(@PathVariable("id") Integer id) {
		List<Transaction> transactions = tService.getMobileRecommendationsDistrict(id);
		return transactions;
	}
}
