package com.example.storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.storage.model.User;
import com.example.storage.service.TariffService;
import com.example.storage.service.UserService;
import com.example.storage.service.UserServiceImpl;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("tariffServiceImpl")
	private TariffService tariffService;
	
	@Autowired
	private PasswordEncoder bcryptPasswordEncoder; 

	@GetMapping("/login")
	public String loginPage(Model model, @RequestParam(name = "error", required = false) Boolean error) {
		model.addAttribute("form", new User());
		if (error != null && error) {
			model.addAttribute("error", error);
		}
		return "views/login";
	}

	@GetMapping({ "/register" })
	public String register(Model model) {
		model.addAttribute("form", new User());
		return "views/register";
	}

	@PostMapping({ "/register" })
	public String register(@ModelAttribute User user) {
		user.setUserRole("user");
		user.setUserPassword(bcryptPasswordEncoder.encode(user.getUserPassword()));
		
		user.setTariff(tariffService.getAllTariffs().get(0));
		
		userService.addUser(user);
		return "redirect:/login";
	}

}
