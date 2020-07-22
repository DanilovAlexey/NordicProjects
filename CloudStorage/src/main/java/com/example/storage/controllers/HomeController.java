package com.example.storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.storage.model.User;
import com.example.storage.service.FileService;
import com.example.storage.service.UserService;

@Controller
@Secured({"ROLE_admin", "ROLE_user"})
public class HomeController {

	
	@Autowired
	@Qualifier("fileServiceImpl")
	private FileService fileService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@GetMapping({"/", "/home"})
	public ModelAndView index( Authentication authentication) {
		var modelAndView = new ModelAndView();
		
		var currUser = userService.getUser(authentication.getName());
		
		modelAndView.setViewName("views/home");
		modelAndView.addObject("files", currUser.getFiles());
		//System.out.println(currUser.getUserId());
		
		return modelAndView;
	}

}
