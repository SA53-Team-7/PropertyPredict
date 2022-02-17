package com.team7.propertypredict.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"project", "saleType"})
@Table(name="transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer txnId;
	private String contractDate;
	private Double floorArea;
	private Double price;
	private String propType;
	private String areaType;
	private String tenure;
	private String floorRange;
	
	@ManyToOne
	@JoinColumn(name = "saletype_saletype_id",nullable = false)
	private SaleType saleType;
	
	private String district;
	private Integer noOfUnits;
	
	@ManyToOne
	@JoinColumn(name = "project_project_id",nullable = false)
	private Project project;
	
	public Transaction() {
		super();
	}

	public Transaction(String contractDate, Double floorArea, Double price, String propType, String areaType,
			String tenure, String floorRange, String district, Integer noOfUnits) {
		super();
		this.contractDate = contractDate;
		this.floorArea = floorArea;
		this.price = price;
		this.propType = propType;
		this.areaType = areaType;
		this.tenure = tenure;
		this.floorRange = floorRange;
		this.district = district;
		this.noOfUnits = noOfUnits;
	}

	public Integer getTxnId() {
		return txnId;
	}

	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public Double getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Double floorArea) {
		this.floorArea = floorArea;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getFloorRange() {
		return floorRange;
	}

	public void setFloorRange(String floorRange) {
		this.floorRange = floorRange;
	}

	public SaleType getSaleType() {
		return saleType;
	}

	public void setSaleType(SaleType saleType) {
		this.saleType = saleType;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Integer getNoOfUnits() {
		return noOfUnits;
	}

	public void setNoOfUnits(Integer noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
