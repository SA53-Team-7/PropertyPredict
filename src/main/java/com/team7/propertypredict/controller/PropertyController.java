package com.team7.propertypredict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.service.ProjectService;
import com.team7.propertypredict.service.TransactionService;

@Controller
public class PropertyController {
	
	@Autowired
	TransactionService tservice;

	@GetMapping(value = "/property/{pid}")
	public String viewProperty(Model model, @PathVariable Integer pid) {
				
		// Get all transactions 
		model.addAttribute("allTxn", (List<Transaction>) tservice.getTransactionsByProjectId(pid));
		  
		// Get filters' values for selection 
		model.addAttribute("floorFilter", (List<String>) tservice.getFloorRangeValues(pid));
		model.addAttribute("areaFilter", (List<String>) tservice.getFloorAreaValues(pid));
	
		return "property";
	}
	 
}
