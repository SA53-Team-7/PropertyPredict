package com.team7.propertypredict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team7.propertypredict.helper.Map;
import com.team7.propertypredict.helper.ProjectDetails;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.service.ProjectService;
import com.team7.propertypredict.service.TransactionService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService pService;
	
	@Autowired
	TransactionService tService;
	
	// View property details and past transactions given a project id
	@GetMapping("/viewProperty/{pid}")
	public String viewProject(@PathVariable Integer pid, Model model) {
		
		// Get property details
		ProjectDetails projectDetails = pService.getProjectDetails(pid);
		model.addAttribute("project", projectDetails);
		
		// Get all transactions 
		model.addAttribute("allTxn", (List<Transaction>) tService.getTransactionsByProjectId(pid));
		  
		// Get filters' values for selection 
		model.addAttribute("floorFilter", (List<String>) tService.getFloorRangeValues(pid));
		model.addAttribute("areaFilter", (List<String>) tService.getFloorAreaValues(pid));
		model.addAttribute("districtFilter", (List<String>) tService.getDistrictValues(pid));
		model.addAttribute("topFilter", (List<String>) tService.getDistinctTOP(pid));
		model.addAttribute("tenureFilter", (List<String>) tService.getDistinctTenure(pid));
		return "property";
	}
	
	@GetMapping("/view-map/{pid}")
	public String viewMap(@PathVariable Integer pid, Model model) {
		
		String map = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?layerchosen=default&lat=1.2424409850962639&lng=103.83675517458369&zoom=17&height=300&width=512&points=[1.2424409850962639,103.83675517458369,%22168,228,160%22,%20%22A%22]";
		// Get One Map
		ProjectDetails projectDetails = pService.getProjectDetails(pid);
		//String map = pService.getMap(pid);
		model.addAttribute("project", projectDetails);
		model.addAttribute("map", map);
		return "map";
		
	}
	
}
