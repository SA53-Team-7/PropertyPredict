package com.team7.propertypredict.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.ProjectDetails;
import com.team7.propertypredict.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService pService;

	@GetMapping("/view/{street}")
	public String viewProject(@PathVariable String street, Model model) {
		ArrayList<Project> projects = pService.findProjectsByStreet(street);
		ArrayList<ProjectDetails> projectDetails = new ArrayList<ProjectDetails>();
		for(Project project:projects) {
			ProjectDetails pd = pService.getProjectDetails(project.getProjectId());
			projectDetails.add(pd);
		}
		model.addAttribute("projects", projectDetails);
		return "projects";
	}
}
