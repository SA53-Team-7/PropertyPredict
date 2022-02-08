package com.team7.propertypredict.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.UserService;

@Controller
public class CommonController {
	
	@Autowired
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
}
