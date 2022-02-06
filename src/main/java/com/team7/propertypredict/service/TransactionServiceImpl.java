package com.team7.propertypredict.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.repository.ProjectRepository;
import com.team7.propertypredict.repository.TransactionRepository;

@Component
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository trepo;
	
	@Autowired
	ProjectRepository prepo;

	@Override
	public List<Transaction> getTransactionsByProjectId(Integer id) {
		List<Transaction> txnList = (List<Transaction>) trepo.findAllTransactionsByProject(id);
		return txnList;	
	}

	@Override
	public List<String> getFloorRangeValues(Integer id) {
		List<String> filters = new ArrayList<String>();
		filters.add("All");	
		filters.addAll(trepo.findDistinctFloorRange(id));
		return filters;
	}

	@Override
	public List<String> getFloorAreaValues(Integer id) {
		List<String> filters = new ArrayList<String>();
		filters.add("All");
		filters.addAll(trepo.findDistinctFloorArea(id));
		return filters;
	}
	
}
