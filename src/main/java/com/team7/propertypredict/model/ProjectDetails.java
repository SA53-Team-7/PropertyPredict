package com.team7.propertypredict.model;

public class ProjectDetails {
	
	private String name;
	private String street;
	private Integer averagePrice;
	private Integer totalUnits;
	private String tenure;
	private String saleType;
	private String area;
	private String floorRange;
	
	public ProjectDetails() {
		super();
	}

	public ProjectDetails(String name, String street, Integer averagePrice, Integer totalUnits, String tenure,
			String saleType, String area, String floorRange) {
		super();
		this.name = name;
		this.street = street;
		this.averagePrice = averagePrice;
		this.totalUnits = totalUnits;
		this.tenure = tenure;
		this.saleType = saleType;
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

	public Integer getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Integer averagePrice) {
		this.averagePrice = averagePrice;
	}

	public Integer getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(Integer totalUnits) {
		this.totalUnits = totalUnits;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
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
	
	

}
