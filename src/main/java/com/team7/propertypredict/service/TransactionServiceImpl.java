package com.team7.propertypredict.service;

import java.time.LocalDate;
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
	public List<Transaction> findAllTransactions() {
		return trepo.findAll();
	}
  
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

	@Override
	public List<String> getDistrictValues(Integer id) {
		return trepo.findDistinctDistrict(id);
	}

	@Override
	public List<String> getDistinctTOP(Integer id) {
		List<String> result = trepo.findDistinctTenure(id);
		List<String> top = new ArrayList<>();
				
		for (String s : result) {
			if (s.compareTo("Freehold") == 0) {
				top.add("0");
			} else if (!s.contains("NA")) {
				top.add(s.substring(s.length() - 4));
			}	
		}
		
		return top;
	}

	@Override
	public List<String> getDistinctTenure(Integer id) {
		List<String> result = trepo.findDistinctTenure(id);
		List<String> tenure = new ArrayList<>();
		
		for (String s : result) {
			if (!s.contains("NA")) {
				String[] x = s.split(" ");
				tenure.add(x[0]);
			}
		}
		
		return tenure;
	}

	@Override
	public List<String> getDistinctDistrict() {
		List<String> result = new ArrayList<>();
		result.add("All");
		result.addAll(trepo.findDistinctDistrict());
		return result;
	}

	@Override
	public List<String> getDistinctPropertyType() {		
		List<String> result = new ArrayList<>();
		result.add("All");
		result.addAll(trepo.findDistinctPropertyType());
		return result;
	}
	
	@Override
	public List<String> getDistinctPropertyTypeById(Integer id) {		
		return trepo.findDistinctPropertyTypeByID(id);
	}
	
	@Override
	public List<String> getTopProjectIDsByTransactions() {
		List<String> parameters = new ArrayList<String>();
		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates.add(LocalDate.now());
		
		// Get dates to use as parameters
		for (int i = 1; i < 6; i++) {
			dates.add(dates.get(i-1).minusMonths(1));
		}
		
		// Format parameters according to DB's values
		for (LocalDate dt : dates) {
			String month = String.valueOf(dt.getMonthValue()).length() == 2 ? String.valueOf(dt.getMonthValue()) : "0" + String.valueOf(dt.getMonthValue()); 
			String year = String.valueOf(dt.getYear()).substring(2);
			parameters.add(month.concat(year));
		}
		
		return trepo.findPopularProjectsByTxn(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3),
				parameters.get(4), parameters.get(5));
	}
}
