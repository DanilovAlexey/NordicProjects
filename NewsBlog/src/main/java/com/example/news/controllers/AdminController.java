package com.example.news.controllers;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

	@GetMapping("/admin")
	public String index() {
		return "views/admin/index";
	}
	
}
