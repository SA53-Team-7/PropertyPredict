package com.team7.propertypredict.controller;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.team7.propertypredict.model.NewProject;
import com.team7.propertypredict.repository.NewProjectRepository;


@RestController
@RequestMapping("/newprojects")
public class NewProjectController {
	
	@Autowired
	private NewProjectRepository newProjRepo;
	
	@GetMapping("/create")
	public ModelAndView createNewProject() {
		ModelAndView mav = new ModelAndView("newproj_add", "newProject", new NewProject());
		return mav;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST) 
    public ModelAndView save( @ModelAttribute("newProject") NewProject newProject) {
		newProjRepo.save(newProject);
		ModelAndView mav = new ModelAndView();
    	mav.setViewName("forward:/newprojects");
        return mav;
    }
	  
    @RequestMapping
    public ModelAndView getAllNewProject() {
    	ModelAndView mav = new ModelAndView("newproj_list");
    	List<NewProject> newProjects = newProjRepo.findAll();
    	Collections.sort(newProjects, new Comparator<NewProject>() {
    		@Override
    		public int compare(NewProject p1, NewProject p2) {
    			return (int) (p1.getId() - p2.getId());
    		}
    	});
    	mav.addObject("newProjectList", newProjects);
    	return mav;
    }
	
    @RequestMapping("delete/{id}")
    public ModelAndView remove(@PathVariable int id)   {
    	newProjRepo.deleteNewProject(id);
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("forward:/newprojects");
    	return mav;
	}
}
