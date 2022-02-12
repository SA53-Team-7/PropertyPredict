package com.team7.propertypredict.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Map {
	
	private Double latitude;
	private Double longitude;
	
	public Map() {
		super();
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

	public void setLongtitude(Double longitude) {
		this.longitude = longitude;
	}
	
}
