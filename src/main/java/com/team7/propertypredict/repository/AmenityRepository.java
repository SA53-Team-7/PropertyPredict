package com.team7.propertypredict.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team7.propertypredict.model.Amenity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

    @Query(value = "SELECT * FROM amenities", nativeQuery = true)
    List<Amenity> findAllAmenities();

}
