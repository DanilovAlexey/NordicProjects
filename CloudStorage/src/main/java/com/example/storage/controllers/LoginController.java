package com.example.storage.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${cloud.folder.root}")
	private String cloudFolder;

	@GetMapping("/login")
	public String loginPage(Model model, @RequestParam(name = "error", required = false) Boolean error) {
		model.addAttribute("form", new User());
		if (error != null && error) {
			model.addAttribute("error", error);
		}
		return "views/login";
	}

	@GetMapping({ "/register" })
	public String register(Model model, @RequestParam(name = "error", required = false) Boolean error) {
		model.addAttribute("form", new User());

		if (error != null && error) {
			model.addAttribute("error", error);
		}
		return "views/register";
	}

	@PostMapping({ "/register" })
	public String register(Model model, @ModelAttribute User user) throws IOException {

		if (userService.getUser(user.getUserEmail()) != null) {

			return "redirect:/register?error=true";
		} else {

			user.setUserRole("user");
			user.setUserPassword(bcryptPasswordEncoder.encode(user.getUserPassword()));
			user.setTariff(tariffService.getAllTariffs().get(0));

			userService.addUser(user);

			Files.createDirectory(Paths.get(cloudFolder + File.separator + user.getUserId()));

			return "redirect:/login";
		}
	}

}
