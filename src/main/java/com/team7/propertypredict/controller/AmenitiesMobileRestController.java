package com.team7.propertypredict.controller;

import com.team7.propertypredict.model.Amenity;
import com.team7.propertypredict.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mobile/amenities")
public class AmenitiesMobileRestController {

    @Autowired
    private AmenityService amenityService;

    @GetMapping()
    public List<Amenity> getAllAmenities() {
        return amenityService.findAllAmenities();
    }

    @GetMapping("/trains")
    public List<Amenity> getAllTrainStations() {
        return amenityService.getAllTrainStations();
    }
}
