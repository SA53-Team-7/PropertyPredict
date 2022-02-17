package com.team7.propertypredict.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team7.propertypredict.helper.Location;
import com.team7.propertypredict.helper.Property;
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
	public String viewProject(@PathVariable Integer pid, Model model, HttpSession session) {
		//Integer uid = (Integer) session.getAttribute("userId");
			
		// Get property details
		model.addAttribute("shortlisted", pService.checkIfShortlisted(pid, 1));
		model.addAttribute("project", pService.getProjectDetails(pid));
		
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
		Integer distanceFilter = 1000;
		Map<String, List<Location>> locationDetails = pService.getLocationDetails(pid);
		Map<String, List<Location>> filteredLocations = pService.filterLocationsByDistance(locationDetails, distanceFilter);
		String map = pService.getMapWithAmenities(pid, filteredLocations);
		Boolean exist = (map== "@{/images/unknown.png}") ? false : true;
		
		model.addAttribute("distanceFilter", distanceFilter);
		model.addAttribute("property", propertyDetails);
		model.addAttribute("locations", filteredLocations);
		model.addAttribute("map", map);
		model.addAttribute("exist", exist);
		return "map";
		
	}
	
	@GetMapping("/add-shortlist/{pid}")
	public String addShortlist(HttpSession session, @PathVariable Integer pid, Model model) {
		//Integer uid = (Integer) session.getAttribute("userId");
		
		/*if(userId==null) {
			return "forward:/login";
		}*/	

		pService.updateShortlistedProject(pid, 1);
		
		return "redirect:/project/view-shortlist";
	}
	
	@GetMapping("/view-shortlist")
	public String viewShortlist(Model model, HttpSession session) {
		//Integer uid = (Integer) session.getAttribute("userId");
		
		/*if(uid==null) {
		return "forward:/login";
		}*/

		model.addAttribute("projects", pService.getProjectsDetails(1));	
		return "shortlist";
	}
}
