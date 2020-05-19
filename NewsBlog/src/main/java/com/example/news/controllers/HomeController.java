package com.example.news.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping({ "", "/", "home" })
	public String home() {
		return "views/home";
	}

	@GetMapping("/login")
	public String login() {
		return "views/login";
	}

	@GetMapping("/register")
	public String register() {
		return "views/registration";
	}
	
	@GetMapping("/blogs")
	public String blogs() {
		return "views/blogs";
	}

}
