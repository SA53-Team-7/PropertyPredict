package com.team7.propertypredict.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.UserService;
import com.team7.propertypredict.validator.UserValidator;

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
			session.setAttribute("username", u.getUsername());
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
	
	@GetMapping("/registration")
    public String registration(Model model) {
		User user = new User();
        model.addAttribute("userForm", user);

        return "registration";
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        uService.save(userForm);

        return "redirect:/home";
    }

}
