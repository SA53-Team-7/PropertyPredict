package com.team7.propertypredict.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.service.ProjectService;

@RestController
@RequestMapping("/api/mobile/projects")
public class ProjectsMobileRestController {

	@Autowired
	private ProjectService pService;

	@GetMapping("/get/{id}")
	public List<Project> getProject(@PathVariable("id") Integer id) {
		List<Project> projects = new ArrayList<>();
		projects.add(pService.findProjectById(id)); 
		return projects;
	}

	@GetMapping("/search/{string}")
	public List<Project> searchProjects(@PathVariable("string") String searchString) {
		return pService.searchProjects(searchString);
	}

	@GetMapping("/recommend/{district}")
	public List<Project> getAndroidRecommendations(@PathVariable("district") String district) {
		return pService.getMobileRecommendationsByDistrict(district);
	}
}
