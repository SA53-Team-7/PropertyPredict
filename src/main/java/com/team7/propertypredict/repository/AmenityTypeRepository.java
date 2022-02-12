package com.team7.propertypredict.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.AmenityType;

public interface AmenityTypeRepository extends JpaRepository<AmenityType, Integer> {
	
	@Query(value = "SELECT * FROM amenitytype at where at.type=:type", nativeQuery = true)
	AmenityType findByType(@Param ("type") String type);

}
