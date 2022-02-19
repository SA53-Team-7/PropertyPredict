package com.team7.propertypredict.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team7.propertypredict.helper.Location;
import com.team7.propertypredict.helper.ProjectDetails;
import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.Transaction;
import com.team7.propertypredict.model.User;
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
		// Integer uid = (Integer) session.getAttribute("userId");

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

		Map<String, List<Location>> locationDetails = pService.getLocationDetails(pid);
		Map<String, List<Location>> filteredLocations = pService.filterLocationsByDistance(locationDetails, 1000);
		String map = pService.getMapWithAmenities(pid, filteredLocations);
		Boolean exist = (map == "@{/images/unknown.png}") ? false : true;

		model.addAttribute("property", pService.getPropertyDetails(pid));
		model.addAttribute("locations", filteredLocations);
		model.addAttribute("map", map);
		model.addAttribute("exist", exist);
		return "map";

	}

	@GetMapping("/add-shortlist/{pid}")
	public String addShortlist(HttpSession session, @PathVariable Integer pid, Model model) {
		User user = (User) session.getAttribute("userObj");

		if (user == null) {
			return "forward:/login";
		}

		pService.updateShortlistedProject(pid, user.getUserId());

		return "redirect:/project/view-shortlist";
	}

	@GetMapping("/view-shortlist")
	public String viewShortlist(Model model, HttpSession session) {
		User user = (User) session.getAttribute("userObj");

		/*
		 * if (user == null) { return "forward:/login"; }
		 */
		List<ProjectDetails> projects = pService.getProjectsDetails(1);

		model.addAttribute("projects", projects);
		model.addAttribute("names", pService.getNamesFromProjectDetailList(projects, 1));
		model.addAttribute("searchStr", "Unavailable");
		return "shortlist";
	}

	@GetMapping("/filter-shortlist")
	public String filterShortlist(Model model, HttpSession session, @Param("searchStr") String searchStr) {
		User user = (User) session.getAttribute("userObj");

		/*
		 * if (user == null) { return "forward:/login"; }
		 */
		List<ProjectDetails> projects = pService.getProjectsDetails(1);
		List<ProjectDetails> filterProjects = pService.getProjectDetailFromSearch(projects, searchStr);
		
		model.addAttribute("projects", filterProjects);
		model.addAttribute("names", pService.getNamesFromProjectDetailList(filterProjects, 1));
		model.addAttribute("searchStr", filterProjects.isEmpty() ? searchStr : "Unavailable");
		return "shortlist";
	}

	@GetMapping("/compare")
	public String compare(Model model) {
		model.addAttribute("names", pService.findAllProjectNames());
		return "compare";
	}

	@RequestMapping(value = "/compare-result", method = RequestMethod.GET)
	public String submitSearchRequest(Model model, @Param("searchStr1") String searchStr1,
			@Param("searchStr2") String searchStr2, @Param("searchStr3") String searchStr3) {
		List<String> searchStrs = Arrays.asList(searchStr1, searchStr2, searchStr3);
		List<Project> projects = new ArrayList<Project>();
		List<ProjectDetails> projectDetails = new ArrayList<ProjectDetails>();
		ProjectDetails project1 = null;
		ProjectDetails project2 = null;
		ProjectDetails project3 = null;
		for (String str : searchStrs) {
			projects.add(pService.findProjectByName(str));
		}
		for (Project project : projects) {
			projectDetails.add(pService.getProjectDetails(project.getProjectId()));
			project1 = pService.getProjectDetails(project.getProjectId());
			project2 = pService.getProjectDetails(project.getProjectId());
			project3 = pService.getProjectDetails(project.getProjectId());
		}
		model.addAttribute("projects", projectDetails);
		model.addAttribute("project1", project1);
		model.addAttribute("project2", project2);
		model.addAttribute("project3", project3);
		return "test";
	}
}
