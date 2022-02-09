package com.team7.propertypredict.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.service.ProjectService;
import com.team7.propertypredict.service.TransactionService;

@Controller
public class CommonController {
	
	@Autowired
	TransactionService tService;
	
	@Autowired
	ProjectService pService;
	
	@GetMapping("/")
	public String viewHome(Model model) {
		model.addAttribute("districtFilter", tService.getDistinctDistrict());
		model.addAttribute("propTypeFilter", tService.getDistinctPropertyType());
		model.addAttribute("segmentFilter", pService.findDistinctSegment());
		return "index";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET) 
	public String submitSearchRequest(Model model, @Param("searchStr") String searchStr, @Param("district") String district, 
			@Param("propertyType") String propertyType, @Param("segment") String segment) {
		
		String districtModified = district.compareTo("All") == 0 ? "" : district;
		String typeModified = propertyType.compareTo("All") == 0 ? "" : propertyType;
		String segmentModified = segment.compareTo("All") == 0 ? "" : segment;
		
		ArrayList<Project> result = pService.searchProjectsWeb(searchStr, segmentModified, districtModified, typeModified);
		model.addAttribute("searchresult", result);
		return "search-result";
	}
}
