package com.team7.propertypredict.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team7.propertypredict.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}
