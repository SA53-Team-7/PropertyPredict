package com.team7.propertypredict.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team7.propertypredict.helper.SearchResultHelper;
import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.ProjectService;
import com.team7.propertypredict.service.TransactionService;
import com.team7.propertypredict.service.UserService;
import com.team7.propertypredict.validator.UserValidator;

@Controller
public class CommonController {
	
 	@Autowired
 	TransactionService tService;
	
 	@Autowired
 	ProjectService pService;
	
 	@Autowired
	private UserService uService;
 	
 	@Autowired
 	private UserValidator uValidator;

	@GetMapping("/")
 	public String viewHome(Model model, HttpSession session) {
		String name = (String) session.getAttribute("userName");
		model.addAttribute("name", name);
		model.addAttribute("districtFilter", tService.getDistinctDistrict());
		model.addAttribute("propTypeFilter", tService.getDistinctPropertyType());
		model.addAttribute("segmentFilter", pService.findDistinctSegment());
		
		// Data for recommendations (non-logged in users)
		model.addAttribute("popularProp", pService.getPopularLocationsByTxn());
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}
	
	@RequestMapping(value = "/home/authenticate", method = RequestMethod.POST)
	public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
			Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return "login";
		} else {

			User u = uService.authenticate(user.getEmail(), user.getPassword());
			if (u == null)
				return "login";
			
			session.setAttribute("userId", user.getUserId());
			session.setAttribute("userObj", user);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/registration")
    public String registration(Model model) {
		User user = new User();
        model.addAttribute("userForm", user);
        return "registration";
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult) {

		uValidator.validate(userForm, bindingResult);
		
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        uService.save(userForm);
        return "redirect:/login";
    }
	
 	@RequestMapping(value = "/search", method = RequestMethod.GET) 
 	public String submitSearchRequest(Model model, @Param("searchStr") String searchStr, @Param("district") String district, 
 			@Param("propertyType") String propertyType, @Param("segment") String segment) {
		
 		String districtModified = district.compareTo("All") == 0 ? "" : district;
 		String typeModified = propertyType.compareTo("All") == 0 ? "" : propertyType;
 		String segmentModified = segment.compareTo("All") == 0 ? "" : segment;
 		ArrayList<SearchResultHelper> result = pService.searchProjectsWeb(searchStr, segmentModified, districtModified, typeModified);
 		ArrayList<String> propTypes = pService.findDistinctPropTypeByPara(searchStr, segmentModified, districtModified, typeModified);
 		ArrayList<String> tenureFilters = pService.findDistinctTenureByPara(searchStr, segmentModified, districtModified, typeModified);
 		model.addAttribute("searchresult", result);
 		model.addAttribute("typeFilter", propTypes);
 		model.addAttribute("tenureFilter", tenureFilters);
 		return "search-result";
 	}
}
