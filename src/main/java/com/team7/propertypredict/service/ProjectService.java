package com.team7.propertypredict.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.team7.propertypredict.model.Project;

import helper.ProjectHelper;

@Service
public interface ProjectService {
	
	//ArrayList<Project> findProjectsByStreet(String street);
	
	List<Project> findAllProjects();
	
	List<Project> getTop20Projects();
}
