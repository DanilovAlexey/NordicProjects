package com.example.storage.controllers;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.storage.service.FileService;
import com.example.storage.service.UserService;

@Controller
@Secured({ "ROLE_admin", "ROLE_user" })
public class HomeController {

	@Autowired
	@Qualifier("fileServiceImpl")
	private FileService fileService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Value("${cloud.folder.root}")
	private String cloudFolder;

	@GetMapping({ "/", "/home" })
	public ModelAndView index(@RequestParam(name="error", required=false) String error, Authentication authentication) {
		var modelAndView = new ModelAndView();

		var currUser = userService.getUser(authentication.getName());
		File userDir = new File(cloudFolder + File.separator + currUser.getUserId());
		userDir.mkdir();

		modelAndView.setViewName("views/home");
		var y = FileUtils.sizeOfDirectory(userDir);
		var x = currUser.getTariff().getTariffLimitMb();
		var result = y * 1.0 / (x * 1048576) * 100;
		if (result > 100) {
			result = 100;
		}
		modelAndView.addObject("count", Math.ceil(result) + "%");
		modelAndView.addObject("width", "width: " + Math.ceil(result) + "%");
		modelAndView.addObject("files", currUser.getFiles());
		modelAndView.addObject("tariff", currUser.getTariff().getTariffName());
		modelAndView.addObject("error", error);

		return modelAndView;
	}

}
