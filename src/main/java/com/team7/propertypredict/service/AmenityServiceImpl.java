package com.team7.propertypredict.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.team7.propertypredict.controller.MapRestController;
import com.team7.propertypredict.helper.AmenityAPI;
import com.team7.propertypredict.model.Amenity;
import com.team7.propertypredict.model.AmenityType;
import com.team7.propertypredict.repository.AmenityRepository;
import com.team7.propertypredict.repository.AmenityTypeRepository;

@Component
public class AmenityServiceImpl implements AmenityService {

	@Autowired
	private AmenityRepository aRepo;

	@Autowired
	private AmenityTypeRepository atRepo;

	@Autowired
	private MapRestController mControl;

	@Override
	public void saveAmenities(List<String> searchList, String amenityType) {
		
		List<String> duplicateLocations = new ArrayList<String>();
		AmenityType type = findByType(amenityType);
		if(type==null) {
			type = new AmenityType(amenityType);
			atRepo.save(type);
		}
		for (String search : searchList) {
			AmenityAPI ap = mControl.getAmenityDetails(search);

			if (ap.getFound() == 1) {

				List<LinkedHashMap<String, String>> results = ap.getResults();
				for (LinkedHashMap<String, String> r : results) {
					Set<String> keys = r.keySet();
					String name, x, y, lat, lng;
					name = x = y = lat = lng = "";
					for (String key : keys) {
						if (key == "SEARCHVAL") {
							name = r.get(key);
						} else if (key == "X") {
							x = r.get(key);
						} else if (key == "Y") {
							y = r.get(key);
						} else if (key == "LATITUDE") {
							lat = r.get(key);
						} else if (key == "LONGITUDE") {
							lng = r.get(key);
						}
					}
					Amenity am = new Amenity(name, x, y, lat, lng, type);
					aRepo.saveAndFlush(am);
				}
			} else {
				duplicateLocations.add(search);
			}
		}
	}

	@Override
	public AmenityType findByType(String type) {
		return atRepo.findByType(type);
	}

	@Override
	public List<Amenity> findAllAmenities() {
		return aRepo.findAllAmenities();
	}
}
