package com.team7.propertypredict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.team7.propertypredict.helper.AmenityHelper;
import com.team7.propertypredict.service.AmenityService;

@SpringBootApplication
public class PropertypredictApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertypredictApplication.class, args);
	}
	
	@Autowired
	private AmenityService aService;

	@Bean
	CommandLineRunner runner() {
		return args -> {
			List<String> list = AmenityHelper.neLine;
			//aService.saveAmenities(list, "Train Station");
		};
	}
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {
	
}
