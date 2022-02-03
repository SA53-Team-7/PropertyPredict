package com.team7.propertypredict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.service.TransactionService;

@Controller
public class PropertyController {
	
	@Autowired
	TransactionService tservice;
	
	@GetMapping(value = "property/{id}")
	public String viewProperty(Model model, @PathVariable Integer id) {
		// Get all transactions
		model.addAttribute("allTxn", (List<Transaction>) tservice.getTransactionsByProjectId(id));
		
		// Get filters' values for selection
		model.addAttribute("floorFilter", (List<String>) tservice.getFloorRangeValues(id));
		model.addAttribute("areaFilter", (List<String>) tservice.getFloorAreaValues(id));
		
		return "property";
	}
	 
}
