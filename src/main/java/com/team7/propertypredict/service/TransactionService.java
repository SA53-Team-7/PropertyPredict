package com.team7.propertypredict.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team7.propertypredict.model.Transaction;

@Service
public interface TransactionService {
	
	// Gets all transactions for a specific project
	List<Transaction> getTransactionsByProjectId(Integer id);
	
	// Get all filter values for floor range
	List<String> getFloorRangeValues(Integer id);
	
	// Get all filter values for floor area
	List<String> getFloorAreaValues(Integer id);
	
}
