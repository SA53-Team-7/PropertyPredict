package com.team7.propertypredict.helper;

public class Property{
	
    private int projectId;
    private String propertyName;
    private String region;
    private String street;
    private Double latitude;
    private Double longitude;
    
	public Property() {
		super();
	}

	public Property(int projectId, String propertyName, String region, String street, Double latitude,
			Double longitude) {
		super();
		this.projectId = projectId;
		this.propertyName = propertyName;
		this.region = region;
		this.street = street;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

    
}
