package com.team7.propertypredict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@GetMapping("/view/{street}")
	public String viewProject(@PathVariable String street) {
		return "projects";
	}
}
