package com.team7.propertypredict.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team7.propertypredict.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	
	
	@Query(value = "SELECT * FROM transactions WHERE project_project_id =:id ORDER BY substring(contract_date, 3, 2), substring(contract_date, 1, 2)", nativeQuery=true)
	List<Transaction> findAllTransactionsByProject(Integer id);

	//@Query(value = "SELECT * FROM transactions WHERE project_project_id =:id ORDER BY substring(contract_date, 3, 2), substring(contract_date, 1, 2)", nativeQuery=true)
	
	@Query(value = "SELECT distinct floor_area FROM transactions WHERE project_project_id = :id ORDER BY floor_area ASC", nativeQuery=true)
	List<String> findDistinctFloorArea(Integer id);
	
	@Query(value = "SELECT distinct floor_range FROM transactions WHERE project_project_id = :id ORDER BY floor_range ASC", nativeQuery=true)
	List<String> findDistinctFloorRange(Integer id);
}
