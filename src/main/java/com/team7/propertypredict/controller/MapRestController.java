package com.team7.propertypredict.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.team7.propertypredict.helper.AmenityAPI;
import com.team7.propertypredict.helper.Map;

@RestController
@RequestMapping("/api/map")
public class MapRestController {
	
	@GetMapping(value="/convert")
	public Map getCoordinates(String x, String y) {
		
		String url = "https://developers.onemap.sg/commonapi/convert/3414to4326?X=" + x + "&Y=" + y;
		
		RestTemplate restTemplate = new RestTemplate();
		Map result = restTemplate.getForObject(url, Map.class);
		return result;
	}
	
	@GetMapping(value="/amenity-details")
	public AmenityAPI getAmenityDetails(String search) {
		
		String url = "https://developers.onemap.sg/commonapi/search?searchVal=" + search + "&returnGeom=Y&getAddrDetails=N";
		
		RestTemplate restTemplate = new RestTemplate();
		AmenityAPI result = restTemplate.getForObject(url, AmenityAPI.class);
		return result;
	}
}
