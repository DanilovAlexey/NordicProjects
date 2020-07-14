package com.example.storage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping({"/login"})
	public String login() {
		return "views/login";
	}
	
	@GetMapping({"/register"})
	public String register() {
		return "views/register";
	}
}
