package com.team7.propertypredict.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.ProjectDetails;

@Service
public interface ProjectService {
	
	ArrayList<Project> findProjectsByStreet(String street);
	ProjectDetails getProjectDetails(Integer pid);
	
	Project findProjectById(Integer pid);
	Double findAveragePriceByProjectId(Integer pid);
	Integer findTotalUnitsByProjectId(Integer pid);
	String findTenureByProjectId(Integer pid);
	Double findMinAreaByProjectId(Integer pid);
	Double findMaxAreaByProjectId(Integer pid);
	ArrayList<String> findfloorRangeByProjectId(Integer pid);
}
