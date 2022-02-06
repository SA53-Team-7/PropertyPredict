package com.team7.propertypredict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.service.ProjectService;

import helper.ProjectHelper;

@RestController
@RequestMapping("/api/mobile/projects")
public class PropertyMobileRestController {

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
}
