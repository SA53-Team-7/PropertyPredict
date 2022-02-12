package com.team7.propertypredict;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PropertypredictApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertypredictApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			//List<String> stationList = Arrays.asList("TAMPINES MRT STATION (EW2)", "Pasir Ris MRT Station (EW1)");
		};
	}

}
