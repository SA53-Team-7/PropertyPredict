package com.team7.propertypredict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.team7.propertypredict.service.AmenityService;
import com.team7.propertypredict.service.ProjectService;


@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PropertypredictApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertypredictApplication.class, args);
	}

	@Autowired
	private AmenityService aService;
	
	@Autowired 
	private ProjectService pService;

	@Bean
	CommandLineRunner runner() {
		return args -> {
			//List<String> list = pService.getAmenityNameFromOneMapKmlFile("hawkercentre.kml");
			// List<String> list = AmenityHelper.ewLine
			//aService.saveAmenities(list, "Hawker Centre");
		};
	};

	@Configuration
	@EnableScheduling
	@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
	class SchedulingConfiguration {
	
	}
}
