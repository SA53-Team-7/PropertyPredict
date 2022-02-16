package com.team7.propertypredict.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.team7.propertypredict.helper.Location;
import com.team7.propertypredict.helper.ProjectDetails;
import com.team7.propertypredict.helper.Property;
import com.team7.propertypredict.model.Project;

import helper.SearchResultHelper;

@Service
public interface ProjectService {
	
	//ArrayList<Project> findProjectsByStreet(String street);
	
	List<Project> findAllProjects();
	
	List<Project> getTop20Projects();

	ArrayList<Project> searchProjects(String searchString);

	ArrayList<Project> getMobileRecommendationsByDistrict(String district);

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
	
	// Get all distinct segments
	ArrayList<String> findDistinctSegment();
	
	// Search projects for web
	ArrayList<SearchResultHelper> searchProjectsWeb(String searchStr, String segment, String district, String type);
	
	// Get all distinct property type filters by parameters
	ArrayList<String> findDistinctPropTypeByPara (String searchStr, String segment, String district, String type);
	
	// Get all distinct tenure filters by parameters
	ArrayList<String> findDistinctTenureByPara (String searchStr, String segment, String district, String type);
	
	// Get static map 
	String getMap(Integer pid);
	
	// Get static map with amenities
	String getMapWithAmenities(Integer pid, Map<String, List<Location>> locations);
	
	// Get x coordinates
	String findXById(Integer pid);
	
	// Get y coordinates
	String findYById(Integer pid);
	
	// Calculate the difference in distance
	Double calculateDistance(Integer pid, Double latitude, Double longitude);
	
	// Get amenities and its distance
	Map<String, Double> getAmenities(Integer pid, List<Location> locations);
	
	// Get property object
	Property getProperty(Integer pid);
	
	// Get property details
	Property getPropertyDetails(Integer pid);
	
	// Get location details
	Map<String, List<Location>> getLocationDetails(Integer pid);
	
	// Filter locations by distance
	Map<String, List<Location>> filterLocationsByDistance(Map<String, List<Location>> locations, Integer filter);
	
	// Get Amenity's Name from OneMap kml files
	List<String> getAmenityNameFromOneMapKmlFile(String filename) throws IOException, ParserConfigurationException, SAXException;
	
}
