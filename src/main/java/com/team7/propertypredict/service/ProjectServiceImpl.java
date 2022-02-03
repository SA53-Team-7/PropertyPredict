package com.team7.propertypredict.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.repository.ProjectRepository;

@Component
public class ProjectServiceImpl implements ProjectService{

	@Resource
	private ProjectRepository pRepo;
	
	//@Transactional
	//public ArrayList<Project> findProjectsByStreet(String street){
		
	//	ArrayList<Project> projects = pRepo.findProjectsByStreet(street);
	//	return projects;
	//}
	
	@Transactional
	public List<Project> findAllProjects(){
		
		List<Project> projects = pRepo.findAll();
		return projects;
	}
}
