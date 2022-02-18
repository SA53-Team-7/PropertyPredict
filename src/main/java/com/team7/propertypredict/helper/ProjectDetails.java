package com.team7.propertypredict.helper;

import java.util.List;

import com.team7.propertypredict.model.Transaction;

public class ProjectDetails {
	
	private Integer projectId;
	private String name;
	private String street;
	private String averagePrice;
	private String area;
	private String floorRange;
	private List<String> TOPYears;
	private List<String> tenureYears;
	private String map;
	private Boolean mapExist;
	private List<String> dates;
	private List<Double> prices;
	
	public ProjectDetails() {
		super();
	}

	public ProjectDetails(String name, String street, String averagePrice,
			String area, String floorRange) {
		super();
		this.name = name;
		this.street = street;
		this.averagePrice = averagePrice;
		this.area = area;
		this.floorRange = floorRange;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloorRange() {
		return floorRange;
	}

	public void setFloorRange(String floorRange) {
		this.floorRange = floorRange;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public List<String> getTOPYears() {
		return TOPYears;
	}

	public void setTOPYears(List<String> tOPYears) {
		TOPYears = tOPYears;
	}

	public List<String> getTenureYears() {
		return tenureYears;
	}

	public void setTenureYears(List<String> tenureYears) {
		this.tenureYears = tenureYears;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public Boolean getMapExist() {
		return mapExist;
	}

	public void setMapExist(Boolean mapExist) {
		this.mapExist = mapExist;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public List<Double> getPrices() {
		return prices;
	}

	public void setPrices(List<Double> prices) {
		this.prices = prices;
	}
}
