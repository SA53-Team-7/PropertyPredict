package com.team7.propertypredict.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.team7.propertypredict.helper.ProjectDetails;
import com.team7.propertypredict.model.Project;

@Service
public interface ProjectService {
	
	//ArrayList<Project> findProjectsByStreet(String street);
	
	List<Project> findAllProjects();
	
	List<Project> getTop20Projects();

	ArrayList<Project> searchProjects(String searchString);

	// Get all the projects given a location
	ArrayList<Project> findProjectsByStreet(String street);
	
	// Get all the project details given a project id 
	ProjectDetails getProjectDetails(Integer pid);
	
	// Get project given a project id
	Project findProjectById(Integer pid);
	
	// Get the average price of all the transactions of a project given a project id
	Double findAveragePriceByProjectId(Integer pid);
	
	// Get the total units of all the transactions of a project given a project id
	Integer findTotalUnitsByProjectId(Integer pid);
	
	// Get the tenure of all the transactions of a project given a project id
	String findTenureByProjectId(Integer pid);
	
	// Get the sale type of all the transactions of a project given a project id
	String findSaleTypeByProjectId(Integer pid);
	
	// Get the minimum area of the house from all the transactions of a project given a project id
	Double findMinAreaByProjectId(Integer pid);
	
	// Get the maximum area of the house from all the transactions of a project given a project id
	Double findMaxAreaByProjectId(Integer pid);
	
	// Get all the floor range from all the transactions of a project given a project id
	ArrayList<String> findfloorRangeByProjectId(Integer pid);

}
