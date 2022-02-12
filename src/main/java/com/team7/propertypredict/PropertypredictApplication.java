package com.team7.propertypredict;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.team7.propertypredict.service.AmenityService;

@SpringBootApplication
public class PropertypredictApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertypredictApplication.class, args);
	}
	
	/*@Autowired
	private AmenityService aService;

	@Bean
	CommandLineRunner runner() {
		return args -> {
			List<String> stationList = Arrays.asList("TAMPINES MRT STATION (EW2)", "Pasir Ris MRT Station (EW1)");
			aService.saveAmenities(stationList, "MRT");
		};
	}*/

}
