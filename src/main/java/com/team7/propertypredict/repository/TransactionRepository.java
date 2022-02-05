package com.team7.propertypredict.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	@Query(value = "SELECT * FROM transactions WHERE project_project_id = :id", nativeQuery = true)
	List<Transaction> findTransactionsByProjectId(@Param ("id") Integer id);
}
