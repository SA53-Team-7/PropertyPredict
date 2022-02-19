package com.team7.propertypredict.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.repository.TransactionRepository;

	
@Component
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository trepo;
	
	@Autowired
	ProjectService pservice;

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

	@Override
	public List<Project> getSimilarProjectIDsByPrice(Integer id) {
		List<String> projectIDs = new ArrayList<String>();
		List<Project> results = new ArrayList<Project>();
		Double avg_amt, total_amt = 0.0;
		Double lower = null, upper = null;
		
		// Find shortlisted projects
		List<Project> shortlisted = pservice.findAllShortlistProjects(id);
		
		// If user doesn't have any shortlisted projects, take the first element of recently transacted properties
		if (shortlisted.size() == 0) {
			shortlisted.add(getRecentlyTransactedProjects().get(0));
		}
		
		// Find interested districts
		List<String> districts = trepo.findInterestedDistricts(id);
		
		if (districts.size() == 0) {
			districts.addAll(getDistrictValues(shortlisted.get(0).getProjectId()));
		}
		
		// Find average price of all shortlisted properties
		for (Project p : shortlisted) {
			total_amt += pservice.findAveragePriceByProjectId(p.getProjectId());
		}
		
		avg_amt = total_amt / shortlisted.size();
		lower = 0.9 * avg_amt;
		upper = 1.1 * avg_amt;
		
		// Find project IDs with similar price range and in the same district
		for (String district : districts) {
			projectIDs.addAll(trepo.findSimilarProjects(district, lower, upper));
		}
		
		// Randomly choose 3 projects with similar characteristics
		Random r = new Random();
		List<Integer> pidTracker = new ArrayList<>();
		
		while (pidTracker.size() != 3) {
			Integer pid = Integer.valueOf(projectIDs.get(r.nextInt(projectIDs.size())));
			
			if (!pidTracker.contains(pid)) {
				results.add(pservice.findProjectById(pid));
				pidTracker.add(pid);
			}
		}
		
		return results;
	}

	@Override
	public List<Project> getRecentlyTransactedProjects() {
		LocalDate currDate = LocalDate.now();
		List<Project> results = new ArrayList<Project>();		
		String month = String.valueOf(currDate.getMonthValue()).length() == 2 ? String.valueOf(currDate.getMonthValue()) : "0" + String.valueOf(currDate.getMonthValue());
		String year = String.valueOf(currDate.getYear()).substring(2);
		String recentDt = month + year;
		
		// Get list of all transactions from the current month
		List<String> recentProjects = trepo.findRecentProjects(recentDt);
		
		// Choose 6 random transactions from the current month
		Random r = new Random();
		List<Integer> pidTracker = new ArrayList<>();

		while (pidTracker.size() != 6) {	
			Integer pid = Integer.valueOf(recentProjects.get(r.nextInt(recentProjects.size())));
			
			if (!pidTracker.contains(pid)) {
				results.add(pservice.findProjectById(pid));
				pidTracker.add(pid);
			}
		}
		return results;
	}
}
