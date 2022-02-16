package com.team7.propertypredict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.team7.propertypredict.model.NewProject;
import com.team7.propertypredict.repository.NewProjectRepository;


@RestController
@RequestMapping("/newprojects")
public class NewProjectController {
	
	@Autowired
	private NewProjectRepository newProjRepo;
	
    @PostMapping
    public NewProject save(@RequestBody NewProject newProject) {
        return newProjRepo.save(newProject);
    }
    
    @GetMapping
    public ModelAndView getAllNewProject() {
    	ModelAndView mav = new ModelAndView("newproj_list");
    	List<NewProject> newProjects = newProjRepo.findAll();
    	mav.addObject("newProjectList", newProjects);
    	return mav;
    }
    
    @GetMapping("/{id}")
    public NewProject findProduct(@PathVariable int id) {
        return newProjRepo.findProductById(id);
    }
	
	
	
}
