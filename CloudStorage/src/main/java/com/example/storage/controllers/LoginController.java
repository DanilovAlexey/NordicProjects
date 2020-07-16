package com.example.storage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.storage.model.User;


@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage(Model model, @RequestParam(name="error", required=false) Boolean error) {
		model.addAttribute("form", new User());
		if (error != null && error) {
			model.addAttribute("error", error);
		}
		return "views/login";
	}
	
	@GetMapping({"/register"})
	public String register(Model model) {
		return "views/register";
	}
	
	@PostMapping({"/register"})
	public String register() {
		return "redirect:/login";
	}
}
