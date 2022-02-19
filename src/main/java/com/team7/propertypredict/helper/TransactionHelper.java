package com.team7.propertypredict.helper;

import java.util.Date;

public class TransactionHelper {
	private Date date;
	private Double price;
	
	public TransactionHelper(Date date, Double price) {
		super();
		this.date = date;
		this.price = price;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
