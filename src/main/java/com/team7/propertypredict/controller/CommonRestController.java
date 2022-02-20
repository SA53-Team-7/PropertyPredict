package com.team7.propertypredict.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team7.propertypredict.service.DataService;

@RestController
@RequestMapping("/api")
public class CommonRestController {
	
	@Autowired
	DataService dService;
	
	@GetMapping("/mortgage")
	@ResponseBody
	public String getMonthlyMortagePayment(@RequestParam("loan") String loan, @RequestParam("tenure") String tenure, 
			@RequestParam("interest") String interest) {
				
		Double principal = Double.parseDouble(loan);
		Integer termInMonths = Integer.parseInt(tenure) * 12;
		Double intRateByMonth = Double.parseDouble(interest) / 100 / 12;
				
		Double top = Math.pow(1 + intRateByMonth, termInMonths);
		Double bottom = top - 1;
		
		// Calculate monthly payment amount rounded up to nearest integer
		Double payment = Math.ceil(principal * intRateByMonth * (top / bottom));
		
		// Calculate interest to pay for the first month
		Double intPayment = principal * intRateByMonth; 
		
		// Calculate principal to pay for the first month
		Double prinPayment = payment - intPayment;
		
		String output = "{" + "\"payment\":" + payment.toString() + ", \"interest\":" + intPayment.toString() + ", \"principal\":" 
				+ prinPayment.toString() + "}";		
			
		return output;
	}
	
	// Extract data from URA based on CRON schedule
	@RequestMapping("/data")
//	@Scheduled(cron = "0 0 1 * * WED", zone = "Asia/Singapore")
	public void loadData() {
		long duration, difference; 
		Date start, end;
		 
		String token = dService.getToken();
		
		// Clear data from previous batch
		dService.clearTables();
		
		if (dService.getProjectCount() == 0 && dService.getTransactionCount() == 0) {
			System.out.println("Table cleared. Start loading new batch.");
			// Load data from latest batch
			for (int i = 1; i < 5; i++) { 
				System.out.println("Batch " + String.valueOf(i) + " started");
				start = Calendar.getInstance().getTime();
				dService.extractData(token, i); 
				end = Calendar.getInstance().getTime(); difference = end.getTime() -start.getTime(); 
				duration = TimeUnit.MILLISECONDS.toSeconds(difference);
				System.out.println("Batch " + String.valueOf(i) + " completed in " + duration + " seconds"); 
			}
		}
	}
}
