package com.example.storage.controllers;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.storage.model.Tariff;
import com.example.storage.model.User;
import com.example.storage.service.TariffService;
import com.example.storage.service.UserService;

@Controller
@Secured("ROLE_admin")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("tariffServiceImpl")
	private TariffService tariffService;

	@GetMapping("")
	public ModelAndView admin() {
		var modelAndView = new ModelAndView();
		
		var usersList = userService.getAllUsers();
		usersList.sort((o1, o2) -> o2.getUserId() > o1.getUserId()  ? 1 : -1);
		
		modelAndView.setViewName("views/admin/admin");
		modelAndView.addObject("users", usersList);
		
		return modelAndView;
	}
	
	@GetMapping("/edit/user/{id}")
	public ModelAndView editUser(@PathVariable(name="id") Integer id) {
		var modelAndView = new ModelAndView();
		
		modelAndView.setViewName("views/admin/edit");
		modelAndView.addObject("user", userService.getUserById(id));
		modelAndView.addObject("tariffs", tariffService.getAllTariffs());
		
		return modelAndView;
	}
	
	@PostMapping("/edit") 
	public String editUser(Model model, 
			@ModelAttribute User user, 
			@RequestParam("tariffId") Integer tariffId) {
	
		
		user.setTariff(tariffService.getTariffById(tariffId));
		userService.updateUser(user);
		
		return "redirect:/admin";
	}
	
}
