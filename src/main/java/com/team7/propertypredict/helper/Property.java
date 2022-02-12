package com.team7.propertypredict.helper;

public class Property{
	
    private int projectId;
    private String propertyName;
    private String region;
    private String street;
    private String xCoordinates;
    private String yCoordinates;
    
	public Property() {
		super();
	}

	public Property(int projectId) {
		super();
		this.projectId = projectId;
	}

	public Property(int projectId, String propertyName, String region, String street, String xCoordinates,
			String yCoordinates) {
		super();
		this.projectId = projectId;
		this.propertyName = propertyName;
		this.region = region;
		this.street = street;
		this.xCoordinates = xCoordinates;
		this.yCoordinates = yCoordinates;
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

	public String getxCoordinates() {
		return xCoordinates;
	}

	public void setxCoordinates(String xCoordinates) {
		this.xCoordinates = xCoordinates;
	}

	public String getyCoordinates() {
		return yCoordinates;
	}

	public void setyCoordinates(String yCoordinates) {
		this.yCoordinates = yCoordinates;
	}
    
}
