package com.team7.propertypredict.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="amenitytype")
public class AmenityType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer typeId;
	private String type;
	
	@OneToMany(mappedBy = "amenityType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) 
	private List<Amenity> amenities;

	public AmenityType() {
		super();
	}

	public AmenityType(Integer typeId, String type, List<Amenity> amenities) {
		super();
		this.typeId = typeId;
		this.type = type;
		this.amenities = amenities;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

}
