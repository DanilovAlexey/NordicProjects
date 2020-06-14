package com.example.news.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.news.models.BlogItem;
import com.example.news.storage.BlogsStorage;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {
	
	@Autowired
	BlogsStorage blogStorage;

	@GetMapping("/admin")
	public String index(Model model) {
		model.addAttribute("form", new BlogItem());
		return "views/admin/index";
	}

	@PostMapping("/admin")
	public String addBlogHandler(Model model, 
			@ModelAttribute BlogItem form, 
			@RequestParam("image") MultipartFile file,
			Authentication authentication) {
		
		var time =  System.currentTimeMillis() / 1000L;
		
		form.setUserLogin(authentication.getName());
		form.setBlogDate(time);
		
		if (!file.isEmpty()) {
			Path root = Paths.get("src/main/resources/static/images/blog/");
			

			try {
				Files.copy(file.getInputStream(), root.resolve(time + "_" + file.getOriginalFilename()));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			form.setBlogImage(time + "_" + file.getOriginalFilename());

		}
		
		blogStorage.addBlogItem(form);
				
		return "redirect:/blogs";
	}
}
