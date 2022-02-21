package com.team7.propertypredict.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team7.propertypredict.model.NewProject;
import com.team7.propertypredict.repository.NewProjectRepository;

@RestController
@RequestMapping("/api/mobile/newprojects")
public class NewProjectRestController {
	
	@Autowired
	private NewProjectRepository newProjRepo;
	
    @RequestMapping
    public List<NewProject> getAllNewProject() {
    	List<NewProject> newProjects = newProjRepo.findAll();
    	Collections.sort(newProjects, new Comparator<NewProject>() {
    		@Override
    		public int compare(NewProject p1, NewProject p2) {
    			return (int) (p1.getId() - p2.getId());
    		}
    	});
    	return newProjects;
    }
}
