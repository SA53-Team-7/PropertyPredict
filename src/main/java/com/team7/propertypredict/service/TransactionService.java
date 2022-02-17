package com.team7.propertypredict.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team7.propertypredict.model.Transaction;

@Service
public interface TransactionService {
	
	List<Transaction> findAllTransactions();

	// Gets all transactions for a specific project
	List<Transaction> getTransactionsByProjectId(Integer id);
	
	// Get all filter values for floor range
	List<String> getFloorRangeValues(Integer id);
	
	// Get all filter values for floor area
	List<String> getFloorAreaValues(Integer id);
	
	// Get all filter values for district
	List<String> getDistrictValues(Integer id);

	// Get all filter values for TOP year
	List<String> getDistinctTOP(Integer id);
	
	// Get all filter values for tenure
	List<String> getDistinctTenure(Integer id);

	// Get all filter values for district
	List<String> getDistinctDistrict();
	
	// Get all filter values for property types
	List<String> getDistinctPropertyType();

	// Get all distinct property type by project ID
	List<String> getDistinctPropertyTypeById(Integer id);

	// Get all project IDs with high transaction count
	List<String> getTopProjectIDsByTransactions();
}
