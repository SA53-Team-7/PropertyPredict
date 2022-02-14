package com.team7.propertypredict.service;

import org.springframework.stereotype.Service;

@Service
public interface DataService {

	// Retrieve token from URA
	String getToken();

	// Load URA data into database
	void extractData(String token, Integer batch);

	// Clear project and transaction tables before loading new batch of data
	void clearTables();

	// Get row count for projects
	long getProjectCount();

	// Get row count for transactions
	long getTransactionCount();

}
