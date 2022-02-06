package com.team7.propertypredict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.team7.propertypredict.model.ProjectDetails;
import com.team7.propertypredict.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService pService;

	// View project details given a project id
	@GetMapping("/view/{pid}")
	public String viewProject(@PathVariable Integer pid, Model model) {
		ProjectDetails projectDetails = pService.getProjectDetails(pid);
		model.addAttribute("project", projectDetails);
		return "property";
	}
}
