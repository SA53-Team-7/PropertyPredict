package com.team7.propertypredict.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties("transactions")
@Table(name="saletype")
public class SaleType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer typeId;
	
	private String type;
	
	@OneToMany(mappedBy = "saleType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) 
	private List<Transaction> transactions;

	public SaleType() {
		super();
	}

	public SaleType(Integer typeId, List<Transaction> transactions) {
		super();
		this.typeId = typeId;
		this.transactions = transactions;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
	

}
