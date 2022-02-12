package com.team7.propertypredict.service;

import java.util.List;

import com.team7.propertypredict.model.Amenity;
import com.team7.propertypredict.model.AmenityType;

public interface AmenityService {
	
	// Save a list of amenities into the database
	void saveAmenities(List<String> searchList, String amenityType);
	
	// Get AmenityType by type
	AmenityType findByType(String type);

	// Find all Amenities
	List<Amenity> findAllAmenities();
}
