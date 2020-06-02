package com.example.news.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.news.config.UsersStorage;
import com.example.news.dto.RequestFormPassword;
import com.example.news.models.BlogItem;
import com.example.news.models.NewsItem;
import com.example.news.models.User;
import com.example.news.storage.BlogsStorage;

@Controller
public class HomeController {

	private ArrayList<NewsItem> newsList = new ArrayList<NewsItem>() {
		{
			add(new NewsItem(
					"Курорты после пандемии: без «шведских столов» и ключей от номера, но с масками и бассейнами по записи",
					"Сегодня курорты всего мира изобретают все новые и новые способы, как обеспечить безопасность гостей и не допустить распространения вируса на своих территориях. К сожалению, большинство из нововведений превращают отдых практически в нахождение в обсерваторе. Туристам предписывают регулярное тестирование, бесконечные проверки температуры и отказ от многих развлечений и активностей.&nbsp; Попробуем представить, что может нас ожидать уже в следующем отеле, где мы остановимся. &nbsp; РЕГИСТРАЦИЯ&nbsp; На всех входах в отель установят дезинфицирующие коврики. Это нужно, чтобы обеспечить чистоту обуви и не допустить проникновение микробов внутрь.&nbsp; Пол в лобби и других помещениях разрисуют специальными знаками (прощай, работа дизайнера) &mdash; они укажут гостям, где можно стоять, соблюдая правильную социальную дистанцию.&nbsp;",
					"Wed, 20 May 2020 16:41:43 +0300",
					"https://www.turizm.ru/news/uk/kurorty_posle_pandemii_bez_shvedskikh_stolov_i_klyuchei_ot_nomera_no_s_maskami_i_basseinami_po_zapisi/",
					"https://image2.turizm.ru/news/gallery/65912/15899827007110.jpg"));
			add(new NewsItem("Чем заменят двухнедельный карантин по прибытию? Эти меры вам вряд ли понравятся",
					"69% туристов никуда не полетят, если не снимут обязательный карантин для всех приезжающих. Такие данные приводит Международная ассоциация воздушного транспорта (IATA). Напомним, что многие страны ввели для всех иностранцев правило обязательной изоляции на 2 недели. &nbsp;По мнению экспертов, есть другие возможности, которые обеспечивают безопасность и в то же время позволят реально перезапустить авиаперелеты. Проанализируем основные из них. ПЕРЕД ПОЛЕТОМ. Компетентные ведомства страны, куда отправляется турист, должны предварительно собрать всю необходимую и исчерпывающую информацию о последнем. Прежде всего, конечно, о состоянии здоровья, вакцинациях, эпидемиологическом окр",
					"Wed, 20 May 2020 16:41:43 +0300",
					"https://www.turizm.ru/news/uk/kurorty_posle_pandemii_bez_shvedskikh_stolov_i_klyuchei_ot_nomera_no_s_maskami_i_basseinami_po_zapisi/",
					"https://image2.turizm.ru/news/gallery/65909/15899774616891.jpg"));
		}
	};

	@Autowired
	UsersStorage usersStorage;
	
	@Autowired
	BlogsStorage blogStorage;
	
	

	@GetMapping({ "", "/", "home" })
	public ModelAndView home() {
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/home");
		modelAndView.addObject("newsList", newsList);

		
		return modelAndView;
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

		// System.out.println(file.getOriginalFilename());
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
		modelAndView.addObject("blogsList",  blogStorage.getBlogsList());
		modelAndView.addObject("usersList", usersStorage.getUsers());
		
		return modelAndView;
	}
	
	@GetMapping("image/{path}/{imageName}")
	@ResponseBody
	public byte[] getImage(
			@PathVariable(value = "path") String path,
			@PathVariable(value = "imageName") String imageName) throws IOException {
		Path root = Paths.get("src/main/resources/static/images/");

	    File serverFile = new File(root + "/" + path + "/" + imageName);

	    return Files.readAllBytes(serverFile.toPath());
	}

}
