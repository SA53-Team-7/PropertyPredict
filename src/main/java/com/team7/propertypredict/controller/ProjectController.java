package com.team7.propertypredict.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.repository.ProjectRepository;
import com.team7.propertypredict.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService pService;
	
	@Autowired
	private ProjectRepository pRepo;

	@GetMapping("/view/{street}")
	public String viewProject(@PathVariable String street, Model model) {
		ArrayList<Project> projects = pService.findProjectsByStreet(street);
		Double price = pRepo.findAveragePriceByProjectId(885);
		model.addAttribute("price", price);
		model.addAttribute("projects",projects);
		return "projects";
	}
}
