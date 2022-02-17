package com.team7.propertypredict.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("NewProject")
public class NewProject implements Serializable {

	@Id
	private long id;
	private String projectName;
	private String landTxnDate;
	private long landTxnPrice;
	private long predictPrice;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getLandTxnDate() {
		return landTxnDate;
	}
	public void setLandTxnDate(String landTxnDate) {
		this.landTxnDate = landTxnDate;
	}
	public long getLandTxnPrice() {
		return landTxnPrice;
	}
	public void setLandTxnPrice(long landTxnPrice) {
		this.landTxnPrice = landTxnPrice;
	}
	public long getPredictPrice() {
		return predictPrice;
	}
	public void setPredictPrice(long predictPrice) {
		this.predictPrice = predictPrice;
	}

}