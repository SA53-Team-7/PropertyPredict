package com.team7.propertypredict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.team7.propertypredict.controller.MapRestController;
import com.team7.propertypredict.helper.AmenityAPI;
import com.team7.propertypredict.helper.Results;
import com.team7.propertypredict.model.Amenity;
import com.team7.propertypredict.model.AmenityType;
import com.team7.propertypredict.repository.AmenityRepository;
import com.team7.propertypredict.repository.AmenityTypeRepository;

@SpringBootApplication
public class PropertypredictApplication {

	@Autowired
	private AmenityRepository aRepo;
	
	@Autowired
	private AmenityTypeRepository atRepo;
	
	@Autowired 
	private MapRestController mControl;
	
	public static void main(String[] args) {
		SpringApplication.run(PropertypredictApplication.class, args);
	}
	
	/*@Bean
	CommandLineRunner runner() {
		return args -> {
			AmenityAPI ap = mControl.getAmenityDetails("Pasir Ris MRT Station (EW1)");
			if(true) {
				AmenityType mrt = new AmenityType("MRT");	
				atRepo.save(mrt);
			
				List<Results> results = ap.getResults();
				for(Results r:results) {
					String name = r.getSEARCHVAL();
					String x = r.getX();
					String y = r.getY();
					String lat = r.getLATITUDE();
					String lng = r.getLONGITUDE();
					Amenity am = new Amenity(name,x,y,lat,lng,mrt);
					aRepo.saveAndFlush(am);
				}
				System.out.println(ap.toString());
			}
			
		};
	}*/

}
