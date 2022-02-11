package com.team7.propertypredict.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="amenities")
public class Amenity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer amenityId;
	private String name;
	private Double xCoordinates;
	private Double yCoordinates;
	private Double latitude;
	private Double longitude;
	
	@ManyToOne
	@JoinColumn(name = "amenityType_amenityType_id",nullable = false)
	private AmenityType amenityType;

	public Amenity() {
		super();
	}

	public Amenity(Integer amenityId, String name, Double xCoordinates, Double yCoordinates, Double latitude,
			Double longitude, AmenityType amenityType) {
		super();
		this.amenityId = amenityId;
		this.name = name;
		this.xCoordinates = xCoordinates;
		this.yCoordinates = yCoordinates;
		this.latitude = latitude;
		this.longitude = longitude;
		this.amenityType = amenityType;
	}

	public Integer getAmenityId() {
		return amenityId;
	}

	public void setAmenityId(Integer amenityId) {
		this.amenityId = amenityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getxCoordinates() {
		return xCoordinates;
	}

	public void setxCoordinates(Double xCoordinates) {
		this.xCoordinates = xCoordinates;
	}

	public Double getyCoordinates() {
		return yCoordinates;
	}

	public void setyCoordinates(Double yCoordinates) {
		this.yCoordinates = yCoordinates;
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

	public AmenityType getAmenityType() {
		return amenityType;
	}

	public void setAmenityType(AmenityType amenityType) {
		this.amenityType = amenityType;
	}
	
	

}
