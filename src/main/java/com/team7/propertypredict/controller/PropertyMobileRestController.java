package com.team7.propertypredict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class PropertyMobileRestController {

	@Autowired
	private ProjectService pService;

	@GetMapping()
	public List<Project> getAllProjects() {
		return pService.findAllProjects();
	}
	
}
