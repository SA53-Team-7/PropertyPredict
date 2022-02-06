package com.team7.propertypredict.model;

public class ProjectDetails {
	
	private String name;
	private String street;
	private String averagePrice;
	private String tenure;
	private String area;
	private String floorRange;
	
	public ProjectDetails() {
		super();
	}

	public ProjectDetails(String name, String street, String averagePrice, String tenure,
			String area, String floorRange) {
		super();
		this.name = name;
		this.street = street;
		this.averagePrice = averagePrice;
		this.tenure = tenure;
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

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
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
