package com.team7.propertypredict.service;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.team7.propertypredict.controller.MapRestController;
import com.team7.propertypredict.helper.ProjectDetails;
import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.repository.ProjectRepository;

import helper.SearchResultHelper;

@Component
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository pRepo;
	
	@Autowired
	private TransactionService tService;
	
	@Autowired 
	private MapRestController mController;
	
	@Override
	public List<Project> findAllProjects(){
		return pRepo.findAllProjects();
	}	
  
	@Override
	public List<Project> getTop20Projects(){
		return pRepo.getTop20Projects();
	}

	@Override
	public ArrayList<Project> searchProjects(String searchString){
		return pRepo.searchProjects(searchString);
	}

	@Override
	public ArrayList<Project> findProjectsByStreet(String street){
		return pRepo.findProjectsByStreet(street);
	}
	
	@Override
	public ProjectDetails getProjectDetails(Integer pid) {
		ProjectDetails pd = new ProjectDetails();
		
		Project project = findProjectById(pid);
		Integer min = findMinAreaByProjectId(pid).intValue();
		Integer max = findMaxAreaByProjectId(pid).intValue();
		ArrayList<String> floors = findfloorRangeByProjectId(pid);

		Locale usa = new Locale("en", "US");
		NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
		String averagePrice = dollarFormat.format(findAveragePriceByProjectId(pid));
		
		Integer top = 0;
		for(String floor:floors) {
			if(floor.length()==5) {
				String f = floor.substring(3, 5);
				Integer t = Integer.parseInt(f);
				if(t>top) {
					top = t;
				}
			}
		}
		String topFloor ="";
		if(top==0) {
			topFloor = "-";
		}
		else if(top/10==0) {
			topFloor = "01-0"+top;
		}
		else {
			topFloor = top.toString();
		}
		
		pd.setName(project.getName());
		pd.setStreet(project.getStreet());
		pd.setAveragePrice(averagePrice);
		pd.setArea(min + "-" + max + " (square metre)");
		pd.setFloorRange(topFloor);
		return pd;
	}
	
	@Override
	public Project findProjectById(Integer pid) {
		return pRepo.findProjectById(pid);
	}
	
	@Override
	public Double findAveragePriceByProjectId(Integer pid) {
		return pRepo.findAveragePriceByProjectId(pid);
	}
	
	@Override
	public Integer findTotalUnitsByProjectId(Integer pid) {
		return pRepo.findTotalUnitsByProjectId(pid);
	}
	
	@Override
	public 	String findTenureByProjectId(Integer pid) {
		return pRepo.findTenureByProjectId(pid);
	}
	
	@Override
	public 	String findSaleTypeByProjectId(Integer pid){
		return pRepo.findSaleTypeByProjectId(pid);
	}
	
	@Transactional
	public Double findMinAreaByProjectId(Integer pid) {
		Double min = pRepo.findMinAreaByProjectId(pid);
		return min;
	}
	
	@Transactional
	public Double findMaxAreaByProjectId(Integer pid) {
		Double max = pRepo.findMaxAreaByProjectId(pid);
		return max;
	}
	
	@Transactional
	public ArrayList<String> findfloorRangeByProjectId(Integer pid){
		ArrayList<String> floors = pRepo.findfloorRangeByProjectId(pid);
		return floors;
	}

	@Override
	public ArrayList<String> findDistinctSegment() {
		ArrayList<String> result = new ArrayList<>();
		result.add("All");
		result.addAll(pRepo.findDistinctSegments());
		return result;
	}

	@Override
	public ArrayList<SearchResultHelper> searchProjectsWeb(String searchStr, String segment, String district, String type) {
		ArrayList<Project> result = pRepo.searchProjectsWeb(searchStr, segment, district, type);
		ArrayList<SearchResultHelper> searchResults = new ArrayList<>();
		
		for (Project p : result) {
			
			String tenureModified = "";
			String districtModified = "";
			String typeModified = "";
			
			// Get tenure
			List<String> tenureList = tService.getDistinctTenure(p.getProjectId());
			
			for (String s : tenureList) {
				tenureModified += s + ",";
			} 
			
			// Get district
			List<String> districtList = tService.getDistrictValues(p.getProjectId());
			
			for (String s : districtList) {
				districtModified += s + ",";
			}
			
			// Get type 
			List<String> typeList = tService.getDistinctPropertyTypeById(p.getProjectId());
			for (String s : typeList) {
				typeModified += s + ",";
			}

			SearchResultHelper s = new SearchResultHelper(p.getProjectId().toString(), p.getName(), p.getStreet(), p.getSegment(), 
					districtModified.substring(0, districtModified.lastIndexOf(',')), typeModified.substring(0, typeModified.lastIndexOf(',')), 
					tenureModified.substring(0, tenureModified.lastIndexOf(',')));
			searchResults.add(s);
			
		}
		
		return searchResults;		
	}

	@Override
	public ArrayList<String> findDistinctPropTypeByPara(String searchStr, String segment, String district,
			String type) {
		return pRepo.findDistinctTypeByPara(searchStr, segment, district, type);
	}

	@Override
	public ArrayList<String> findDistinctTenureByPara(String searchStr, String segment, String district, String type) {
		List<String> result = pRepo.findDistinctTenureByPara(searchStr, segment, district, type);
		ArrayList<String> filters = new ArrayList<>();
		
		for (String s : result) {
			String[] splitArr = s.split(" ", 2);
			
			if (!filters.contains(splitArr[0])) {
				filters.add(splitArr[0]);
			}
		}
		
		return filters;
	}
	
	@Override
	public String getMap(Integer pid) {
		
		String map;
		String map1 = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?"
				+ "layerchosen=default&lat=";
		String map2 = "&zoom=17&height=300&width=512";
		String x = findXById(pid);
		String y = findYById(pid);
		Double lat = mController.getCoordinates(x, y).getLatitude();
		Double lng = mController.getCoordinates(x, y).getLongitude();
		
		if(x.isEmpty() || y.isEmpty()) {
			map = "@{/images/location-unknown.png}";
		}
		else {
			map = map1 + lat + "&lng=" + lng + map2 + "&points=[" + lat + "," + lng + ",\"168,228,160\", \"A\"]";
		}	
		return map;
	}
	
	@Override
	public String findXById(Integer pid) {
		return pRepo.findXById(pid);
	}
	
	@Override
	public String findYById(Integer pid) {
		return pRepo.findYById(pid);
	}
}
