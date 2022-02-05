package com.team7.propertypredict.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.Transaction;

import helper.ProjectHelper;

@Service
public interface TransactionService {
	
	List<Transaction> findAllTransactions();

	List<Transaction> getTransactionsByProjectId(Integer id);
}
