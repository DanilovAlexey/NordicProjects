package com.example.storage.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured({"ROLE_admin", "ROLE_user"})
public class HomeController {

	
	@GetMapping({"/", "/home"})
	public String index() {
		return "views/home";
	}
	
	@GetMapping({"/admin"})
	public String admin() {
		return "views/admin";
	}
}
