package com.example.news.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.news.config.UsersStorage;
import com.example.news.dto.RequestFormPassword;
import com.example.news.models.BlogItem;
import com.example.news.models.LentaHeadlines;
import com.example.news.models.NewsItem;
import com.example.news.models.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	private ArrayList<BlogItem> blogsList = new ArrayList<BlogItem>() {
		{
			add(new BlogItem("Было бы неплохо сейчас махнуть куда-нибудь подальше.",
					"https://content.skyscnr.com/m/1b51182679225810/original/GettyImages-147444574_doc.jpg?resize=1800px:1800px&quality=100",
					"Wed, 20 May 2020 16:41:43 +0300", "ytka@ytka.ru"));
			add(new BlogItem(
					"Основатель Telegram Павел Дуров в своем официальном телеграм-канале объявил о прекращении работы над блокчейн-проектом TON."
							+ " Причина — решение суда по иску Комиссии"
							+ " по ценным бумагам и биржам США (SEC), запретившего выпуск"
							+ " криптовалюты Gram. Решение суда Дуров назвал несправедливым и"
							+ " предупредил мир о зависимости от США, которые могут закрыть"
							+ " любой банк или банковский счет в мире.",
					"", "Wed, 20 May 2020 16:41:43 +0300", "ytka@ytka.ru"));
		}
	};

	@Autowired
	UsersStorage usersStorage;

	/*
	 * @GetMapping({ "", "/", "home" }) public ModelAndView home() { var
	 * modelAndView = new ModelAndView(); modelAndView.setViewName("views/home");
	 * modelAndView.addObject("newsList", newsList);
	 * 
	 * return modelAndView; }
	 */
	
	@GetMapping({"", "/", "home" })
	public String homePage(Model model) throws URISyntaxException, IOException, InterruptedException {
		var httpClient = HttpClient.newHttpClient();
		
		var httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(new URI("https://api.lenta.ru/lists/latest"))
				.build();
			
		var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		var objectMapper = new ObjectMapper();
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		
		var lenta = objectMapper.readValue(response.body(), LentaHeadlines.class);
		model.addAttribute("news", lenta.getHeadlines());
		
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
	public String registerHandlerPage(Model model, @ModelAttribute RequestFormPassword form) {
		model.addAttribute("form", new RequestFormPassword());

		User user = new User(form.getEmail(), form.getUserName(), form.getPassword(), "");
		usersStorage.addUser(user);

		return "redirect:/home";
	}

	@GetMapping("/blogs")
	public ModelAndView blogs() {
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/blogs");
		modelAndView.addObject("blogsList", blogsList);
		modelAndView.addObject("usersList", usersStorage.getUsers());

		return modelAndView;
	}

}
