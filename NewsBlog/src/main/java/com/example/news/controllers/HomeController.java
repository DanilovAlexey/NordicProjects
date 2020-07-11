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

import com.example.news.dto.RequestFormPassword;
import com.example.news.models.LentaHeadlines;
import com.example.news.models.NewsApiOrgArticles;
import com.example.news.models.NewsItem;
import com.example.news.models.Rubric;
import com.example.news.models.User;
import com.example.news.storage.BlogsStorage;
import com.example.news.storage.UsersStorage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
	private final int AMOUNT_OF_NEWS = 5;

	@Autowired
	UsersStorage usersStorage;

	@Autowired
	BlogsStorage blogStorage;

	@GetMapping({ "", "/", "home" })
	public String homePage(Model model, @RequestParam(name = "id", required = false, defaultValue = "1") Integer reqId,
			@RequestParam(name = "slug", required = true, defaultValue = "all") String slug)
			throws URISyntaxException, IOException, InterruptedException {

		var httpClient = HttpClient.newHttpClient();

		var httpRequest = HttpRequest.newBuilder().GET().uri(new URI("https://api.lenta.ru/lists/latest")).build();

		var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		var objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

		var lenta = objectMapper.readValue(response.body(), LentaHeadlines.class);

		var news = new ArrayList<NewsItem>(lenta.getHeadlines());

		var httpRequest2 = HttpRequest.newBuilder().GET().uri(new URI(
				"http://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=5cc01c4964584ce1a8feb76710b818e5"))
				.build();

		var response2 = httpClient.send(httpRequest2, HttpResponse.BodyHandlers.ofInputStream());
		var newsApiOrg = objectMapper.readValue(response2.body(), NewsApiOrgArticles.class);
		news.addAll(newsApiOrg.getArticles());

		List<NewsItem> filteredNews = new ArrayList<NewsItem>();

		if (slug.equals("all") || slug.isBlank() || slug.isEmpty()) {

			news.sort((o1, o2) -> o1.getDate() < o2.getDate() ? 1 : -1);
			filteredNews = news;

		} else {
			for (var item : news) {
				if (item.getRubric().getSlug().equals(slug)) {
					filteredNews.add(item);
				}
			}
		}

		HashSet<Rubric> rubrics = new HashSet<Rubric>();
		for (var item : news) {
			if (!item.getRubric().getSlug().equals("all"))
				rubrics.add(item.getRubric());

		}

		List<Integer> pagNum = new ArrayList<>();

		for (int i = 1; i < filteredNews.size() / AMOUNT_OF_NEWS; i++) {
			pagNum.add(i);
		}

		if (filteredNews.size() >= 5) {
			List<NewsItem> result = new ArrayList<>();

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
		
		var blogs = blogStorage.getBlogsList();
		blogs.sort((o1, o2) -> o1.getBlogDate() < o2.getBlogDate() ? 1 : -1);
		
		modelAndView.setViewName("views/blogs");
		modelAndView.addObject("blogsList", blogs);
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
