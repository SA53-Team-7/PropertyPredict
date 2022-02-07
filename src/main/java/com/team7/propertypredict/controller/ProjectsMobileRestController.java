package com.team7.propertypredict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

	@GetMapping()
	public List<Project> getAllProjects() {
		return pService.findAllProjects();
	}
	
	@GetMapping("/test")
	public List<Project> get20Projects() {
		return pService.getTop20Projects();
	}

	@GetMapping("/search/{string}")
	public List<Project> searchProjects(@PathVariable("string") String searchString) {
		return pService.searchProjects(searchString);
	}
}
