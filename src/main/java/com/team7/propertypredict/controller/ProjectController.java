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
import com.team7.propertypredict.helper.Property;
import com.team7.propertypredict.model.Amenity;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.repository.AmenityRepository;
import com.team7.propertypredict.repository.AmenityTypeRepository;
import com.team7.propertypredict.service.AmenityService;
import com.team7.propertypredict.service.ProjectService;
import com.team7.propertypredict.service.TransactionService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService pService;
	
	@Autowired
	private TransactionService tService;
	
	@Autowired
	private AmenityTypeRepository atRepo;
	
	@Autowired
	private AmenityRepository aRepo;
	
	
	@Autowired
	private AmenityService aService;
	
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
		
		Property propertyDetails = pService.getPropertyDetails(pid);
		Map<String, List<Location>> locationDetails = pService.getLocationDetails(pid);
		String map = pService.getMap(pid);
		Boolean exist = (map== "@{/images/unknown.png}") ? false : true;
		
		model.addAttribute("locations", locationDetails);
		model.addAttribute("property", propertyDetails);
		model.addAttribute("map", map);
		model.addAttribute("exist", exist);
		return "map";
		
	}
	
	@GetMapping("/test")
	public String test( Model model) {
		Map<String, List<Location>> all = pService.getLocationDetails(855);
		List<Amenity> am = aRepo.findAmenitiesByAmenityType(1);
		Property prop = pService.getProperty(855);
		model.addAttribute("prop", prop);
		model.addAttribute("am", am);
		model.addAttribute("all", all);
		return "test";
	}
		
}
