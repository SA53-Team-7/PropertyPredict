package com.team7.propertypredict.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommonRestController {

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
}
