package com.example.news.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.example.news.config.UsersStorage;
import com.example.news.dto.RequestFormPassword;
import com.example.news.models.LentaHeadlines;
import com.example.news.models.LentaNews;
import com.example.news.models.LentaNewsRubric;
import com.example.news.models.User;
import com.example.news.storage.BlogsStorage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
	final int AMOUNT_OF_NEWS = 5;

	@Autowired
	UsersStorage usersStorage;

	@Autowired
	BlogsStorage blogStorage;

	@GetMapping({ "", "/", "home" })
	public String homePage(Model model, @RequestParam(name = "id", required = false, defaultValue = "1") Integer reqId,
			@RequestParam(name = "slug", required = false) String slug)
			throws URISyntaxException, IOException, InterruptedException {

		var httpClient = HttpClient.newHttpClient();

		var httpRequest = HttpRequest.newBuilder().GET().uri(new URI("https://api.lenta.ru/lists/latest")).build();

		var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		var objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

		var lenta = objectMapper.readValue(response.body(), LentaHeadlines.class);

		List<LentaNews> filteredNews = new ArrayList<LentaNews>();

		if (slug == null) {
			// model.addAttribute("news", lenta.getHeadlines());
			filteredNews = lenta.getHeadlines();
		} else {
			for (var item : lenta.getHeadlines()) {
				if (item.getRubric().getSlug().equals(slug)) {
					filteredNews.add(item);

				}
			}
		}

		HashSet<LentaNewsRubric> rubrics = new HashSet<LentaNewsRubric>();
		for (var item : lenta.getHeadlines()) {
			rubrics.add(item.getRubric());
		}

		List<Integer> pagNum = new ArrayList<>();

		for (int i = 1; i < filteredNews.size() / AMOUNT_OF_NEWS; i++) {
			pagNum.add(i);
		}

		if (filteredNews.size() >= 5) {
			List<LentaNews> result = new ArrayList<LentaNews>();

			int start = (reqId - 1) * AMOUNT_OF_NEWS;
			int end = AMOUNT_OF_NEWS * reqId;

			for (int i = start; i < end; i++) {
				result.add(filteredNews.get(i));

			}
			model.addAttribute("news", result);

		} else {
			model.addAttribute("news", filteredNews);
		}

		model.addAttribute("nums", pagNum);
		model.addAttribute("rubrics", rubrics);

		return "views/home";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "views/login";
	}

	@GetMapping("/login-error")
	public String loginPage(Model model) {
		model.addAttribute("loginError", true);
		return "views/login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("form", new RequestFormPassword());
		return "views/registration";
	}

	@PostMapping("/register")
	public String registerHandlerPage(Model model, @ModelAttribute RequestFormPassword form,
			@RequestParam("avatar") MultipartFile file) {

		User user = new User(form.getEmail(), form.getUserName(), form.getPassword(), User.DEFAULT_AVATAR);

		if (!file.isEmpty()) {
			Path root = Paths.get("src/main/resources/static/images/avatars/");
			var prefix = System.currentTimeMillis() / 1000L;

			try {
				Files.copy(file.getInputStream(), root.resolve(prefix + "_" + file.getOriginalFilename()));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			user.setUserAvatar(prefix + "_" + file.getOriginalFilename());

		}

		usersStorage.addUser(user);

		return "redirect:/home";
	}

	@GetMapping("/blogs")
	public ModelAndView blogs() {
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/blogs");
		modelAndView.addObject("blogsList", blogStorage.getBlogsList());
		modelAndView.addObject("usersList", usersStorage.getUsers());

		return modelAndView;
	}

	@GetMapping("image/{path}/{imageName}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "path") String path,
			@PathVariable(value = "imageName") String imageName) throws IOException {
		Path root = Paths.get("src/main/resources/static/images/");

		File serverFile = new File(root + "/" + path + "/" + imageName);

		return Files.readAllBytes(serverFile.toPath());
	}

}
