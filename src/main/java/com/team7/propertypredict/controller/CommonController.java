package com.team7.propertypredict.controller;

// import java.util.ArrayList;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;

// import com.team7.propertypredict.model.Project;
// import com.team7.propertypredict.service.ProjectService;
// import com.team7.propertypredict.service.TransactionService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.UserService;

@Controller
public class CommonController {
	
// 	@Autowired
// 	TransactionService tService;
	
// 	@Autowired
// 	ProjectService pService;
	
// 	@GetMapping("/")
// 	public String viewHome(Model model) {
// 		model.addAttribute("districtFilter", tService.getDistinctDistrict());
// 		model.addAttribute("propTypeFilter", tService.getDistinctPropertyType());
// 		model.addAttribute("segmentFilter", pService.findDistinctSegment());
// 		return "index";
// 	}
	
// 	@RequestMapping(value = "/search", method = RequestMethod.GET) 
// 	public String submitSearchRequest(Model model, @Param("searchStr") String searchStr, @Param("district") String district, 
// 			@Param("propertyType") String propertyType, @Param("segment") String segment) {
		
// 		String districtModified = district.compareTo("All") == 0 ? "" : district;
// 		String typeModified = propertyType.compareTo("All") == 0 ? "" : propertyType;
// 		String segmentModified = segment.compareTo("All") == 0 ? "" : segment;
		
// 		ArrayList<Project> result = pService.searchProjectsWeb(searchStr, segmentModified, districtModified, typeModified);
// 		model.addAttribute("searchresult", result);
// 		return "search-result";

	private UserService uService;

	@GetMapping("/")
	public String index(HttpSession session) {

		if (session.getAttribute("user") == null) {
			return "redirect:/login";
		} else {
			return "redirect:/home";
		}
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

			// storing items in sessions
			session.setAttribute("userName", u.getUsername());
			session.setAttribute("userObj", u);
			return "redirect:/home";
		}
	}
	
	@RequestMapping(value = "/home")
	public String homePage(Model model, HttpSession session) {
		String name = (String) session.getAttribute("userName");
		model.addAttribute("name", name);
		return "home";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
