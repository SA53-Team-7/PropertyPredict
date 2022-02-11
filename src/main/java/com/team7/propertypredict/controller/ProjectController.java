package com.team7.propertypredict.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.team7.propertypredict.helper.Location;
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
	private TransactionService tService;
	
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
		
		List<Location> locations = new ArrayList<Location>();
		Location location1 = new Location("Pasir Ris MRT", 1.3730433, 103.9492845);
		Location location2 = new Location("Tampines MRT", 1.3551504,  103.9430099);
		locations.add(location1);
		locations.add(location2);
		Map<String, Double> amenities = pService.getAmenities(pid, locations);
		
		// Get One Map
		ProjectDetails projectDetails = pService.getProjectDetails(pid);
		String map = pService.getMap(pid);
		Boolean exist = (map== "@{/images/unknown.png}") ? false : true;
		Double distance = pService.calculateDistance(pid, location1);
		
		model.addAttribute("amenities", amenities);
		model.addAttribute("distance", distance);
		model.addAttribute("project", projectDetails);
		model.addAttribute("map", map);
		model.addAttribute("exist", exist);
		return "map";
		
	}
}
