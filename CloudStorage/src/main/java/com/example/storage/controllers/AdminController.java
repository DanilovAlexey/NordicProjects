package com.example.storage.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

	@Value("${cloud.folder.root}")
	private String cloudFolder;

	@GetMapping("")
	public ModelAndView admin() {
		var modelAndView = new ModelAndView();

		var usersList = userService.getAllUsers();
		usersList.sort((o1, o2) -> o2.getUserId() > o1.getUserId() ? 1 : -1);

		modelAndView.setViewName("views/admin/admin");
		modelAndView.addObject("users", usersList);
		modelAndView.addObject("user", new User());

		return modelAndView;
	}

	@GetMapping("/edit/user/{id}")
	public ModelAndView editUser(@PathVariable(name = "id") Integer id) {
		var modelAndView = new ModelAndView();

		modelAndView.setViewName("views/admin/edit");
		modelAndView.addObject("user", userService.getUserById(id));
		modelAndView.addObject("tariffs", tariffService.getAllTariffs());

		return modelAndView;
	}

	@PostMapping("/edit")
	public String editUser(Model model, @ModelAttribute User user, @RequestParam("tariffId") Integer tariffId) {

		user.setTariff(tariffService.getTariffById(tariffId));
		userService.updateUser(user);

		return "redirect:/admin";
	}

	@GetMapping("/delete/user/{id}")
	public ModelAndView deleteUser(@PathVariable(name = "id") Integer id) {
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/admin/delete");
		modelAndView.addObject("user", userService.getUserById(id));

		return modelAndView;
	}

	@PostMapping("/delete/{id}")
	public String deleteUserById(@PathVariable(name = "id") Integer id) throws IOException {
		userService.deleteUser(userService.getUserById(id));
		Files.delete(Paths.get(cloudFolder + File.separator + id));

		return "redirect:/admin";
	}

	@PostMapping("/search")
	public ModelAndView searchUser(@ModelAttribute User email) {
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/admin/result");
		User user = userService.getUser(email.getUserEmail());
		
		if (user != null) {
			modelAndView.addObject("user", user);
			modelAndView.addObject("isUser",true);
		
		} else {
			modelAndView.addObject("error", true);
		}
		modelAndView.addObject("email",new User());

		return modelAndView;

	}

}
