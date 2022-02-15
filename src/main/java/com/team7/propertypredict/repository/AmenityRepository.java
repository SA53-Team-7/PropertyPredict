package com.team7.propertypredict.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

    @Query(value = "SELECT * FROM amenities", nativeQuery = true)
    List<Amenity> findAllAmenities();

    @Query(value = "SELECT * FROM amenities WHERE amenity_type_amenity_type_id = 1", nativeQuery = true)
    List<Amenity> getAllTrainStations();
    
    @Query(value = "SELECT * FROM amenities a WHERE a.amenity_type_amenity_type_id = :tid", nativeQuery = true)
    List<Amenity> findAmenitiesByAmenityType(@Param ("tid") Integer tid);
    
    @Query(value = "SELECT * FROM amenities a WHERE a.name = :name", nativeQuery = true)
    Amenity findAmenityByName(@Param ("name") String name);

}
