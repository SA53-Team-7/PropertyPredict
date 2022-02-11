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
	private String xCoordinates;
	private String yCoordinates;
	private String latitude;
	private String longitude;
	
	@ManyToOne
	@JoinColumn(name = "amenityType_amenityType_id",nullable = false)
	private AmenityType amenityType;

	public Amenity() {
		super();
	}
	
	public Amenity(String name, AmenityType amenityType) {
		super();
		this.name = name;
		this.amenityType = amenityType;
	}

	public Amenity(String name, String xCoordinates, String yCoordinates, String latitude, String longitude,
			AmenityType amenityType) {
		super();
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public AmenityType getAmenityType() {
		return amenityType;
	}

	public void setAmenityType(AmenityType amenityType) {
		this.amenityType = amenityType;
	}

	@Override
	public String toString() {
		return "Amenity [amenityId=" + amenityId + ", name=" + name + ", xCoordinates=" + xCoordinates
				+ ", yCoordinates=" + yCoordinates + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", amenityType=" + amenityType + "]";
	}
	
	

}
