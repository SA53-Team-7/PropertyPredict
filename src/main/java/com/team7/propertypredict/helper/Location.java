package com.team7.propertypredict.helper;

public class Location{
	
	private String name;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private String marker;
    
	public Location() {
		super();
	}

	public Location(String name, double latitude, double longitude) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location(String name, Double latitude, Double longitude, Double distance) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}
	
}
