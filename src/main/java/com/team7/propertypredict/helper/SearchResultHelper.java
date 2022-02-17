package com.team7.propertypredict.helper;

public class SearchResultHelper {
	private String projectId;
	private String name;
	private String street;
	private String segment;
	private String district;
	private String type;
	private String tenure;
	
	public SearchResultHelper() {
		super();
	}
	
	public SearchResultHelper(String projectId, String name, String street, String segment, String district,
			String type, String tenure) {
		super();
		this.projectId = projectId;
		this.name = name;
		this.street = street;
		this.segment = segment;
		this.district = district;
		this.type = type;
		this.tenure = tenure;
	}

	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	
	public String getSegment() {
		return segment;
	}
	
	public void setSegment(String segment) {
		this.segment = segment;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTenure() {
		return tenure;
	}
	
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
}
