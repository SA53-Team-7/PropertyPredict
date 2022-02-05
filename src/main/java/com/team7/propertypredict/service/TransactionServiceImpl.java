package com.team7.propertypredict.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.repository.ProjectRepository;
import com.team7.propertypredict.repository.TransactionRepository;

import helper.ProjectHelper;

@Component
public class TransactionServiceImpl implements TransactionService{

	@Resource
	private TransactionRepository tRepo;

	@Override
	public List<Transaction> getTransactionsByProjectId(Integer id) {		
		return tRepo.findTransactionsByProjectId(id);
	}

	@Override
	public List<Transaction> findAllTransactions() {
		return tRepo.findAll();
	}
}
