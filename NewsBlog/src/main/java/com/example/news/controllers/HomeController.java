package com.example.news.controllers;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.news.dto.RequestFormPassword;
import com.example.news.models.BlogItem;
import com.example.news.models.NewsItem;
import com.example.news.models.User;

@Controller
public class HomeController {
	
	private ArrayList<NewsItem> newsList = new ArrayList<NewsItem>() {{
		add(new NewsItem(
				"Курорты после пандемии: без «шведских столов» и ключей от номера, но с масками и бассейнами по записи", 
				"Сегодня курорты всего мира изобретают все новые и новые способы, как обеспечить безопасность гостей и не допустить распространения вируса на своих территориях. К сожалению, большинство из нововведений превращают отдых практически в нахождение в обсерваторе. Туристам предписывают регулярное тестирование, бесконечные проверки температуры и отказ от многих развлечений и активностей.&nbsp; Попробуем представить, что может нас ожидать уже в следующем отеле, где мы остановимся. &nbsp; РЕГИСТРАЦИЯ&nbsp; На всех входах в отель установят дезинфицирующие коврики. Это нужно, чтобы обеспечить чистоту обуви и не допустить проникновение микробов внутрь.&nbsp; Пол в лобби и других помещениях разрисуют специальными знаками (прощай, работа дизайнера) &mdash; они укажут гостям, где можно стоять, соблюдая правильную социальную дистанцию.&nbsp;",
				"Wed, 20 May 2020 16:41:43 +0300",
				"https://www.turizm.ru/news/uk/kurorty_posle_pandemii_bez_shvedskikh_stolov_i_klyuchei_ot_nomera_no_s_maskami_i_basseinami_po_zapisi/",
				"https://image2.turizm.ru/news/gallery/65912/15899827007110.jpg"
				));
		add(new NewsItem(
				"Чем заменят двухнедельный карантин по прибытию? Эти меры вам вряд ли понравятся", 
				"69% туристов никуда не полетят, если не снимут обязательный карантин для всех приезжающих. Такие данные приводит Международная ассоциация воздушного транспорта (IATA). Напомним, что многие страны ввели для всех иностранцев правило обязательной изоляции на 2 недели. &nbsp;По мнению экспертов, есть другие возможности, которые обеспечивают безопасность и в то же время позволят реально перезапустить авиаперелеты. Проанализируем основные из них. ПЕРЕД ПОЛЕТОМ. Компетентные ведомства страны, куда отправляется турист, должны предварительно собрать всю необходимую и исчерпывающую информацию о последнем. Прежде всего, конечно, о состоянии здоровья, вакцинациях, эпидемиологическом окр",
				"Wed, 20 May 2020 16:41:43 +0300",
				"https://www.turizm.ru/news/uk/kurorty_posle_pandemii_bez_shvedskikh_stolov_i_klyuchei_ot_nomera_no_s_maskami_i_basseinami_po_zapisi/",
				"https://image2.turizm.ru/news/gallery/65909/15899774616891.jpg"
				));
	}};
	
	private ArrayList<BlogItem> blogsList = new ArrayList<BlogItem>() {{
		add(new BlogItem("Было бы неплохо сейчас махнуть куда-нибудь подальше.", 
				"https://content.skyscnr.com/m/1b51182679225810/original/GettyImages-147444574_doc.jpg?resize=1800px:1800px&quality=100",
				"Wed, 20 May 2020 16:41:43 +0300",
				new User("Alexey Utkins", "testpass", 30, "data:image/jpeg;base64,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")
				));
		add(new BlogItem("Основатель Telegram Павел Дуров в своем официальном телеграм-канале объявил о прекращении работы над блокчейн-проектом TON."
				+" Причина — решение суда по иску Комиссии" 
				+" по ценным бумагам и биржам США (SEC), запретившего выпуск"
				+" криптовалюты Gram. Решение суда Дуров назвал несправедливым и"
				+" предупредил мир о зависимости от США, которые могут закрыть"
				+" любой банк или банковский счет в мире.", 
				"",
				"Wed, 20 May 2020 16:41:43 +0300",
				new User("Alexey Utkins", "testpass", 30, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQqG2UN8kh8z6xn3BBKHS-MU9W0F1PEbU8qHzIJ2xS-jSq_BfRZ&usqp=CAU")
				));
	}}; 
	
	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>() {
		{
			put("Vlad", new User("Vlad", "testpass", 20, ""));
			put("Alexey", new User("Alexey Utkins", "testpass", 30, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQqG2UN8kh8z6xn3BBKHS-MU9W0F1PEbU8qHzIJ2xS-jSq_BfRZ&usqp=CAU"));
		}
	};

	@GetMapping({ "", "/", "home" })
	public ModelAndView  home() {		
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/home");
		modelAndView.addObject("newsList", newsList);
		
		return modelAndView;
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("form", new RequestFormPassword());
		return "views/login";
	}

	@PostMapping("/login")
	public String loginHandlerPage(Model model, @ModelAttribute RequestFormPassword form) {
		if (!users.containsKey(form.getLogin())) {
			model.addAttribute("error", true);
			model.addAttribute("form", new RequestFormPassword());
			return "views/login";
		} else if (!users.get(form.getLogin()).getPassword().equals(form.getPassword())) {
			model.addAttribute("error", true);
			model.addAttribute("form", new RequestFormPassword());
			return "views/login";
		} else {
			model.addAttribute("user", users.get(form.getLogin()));
			model.addAttribute("form", new RequestFormPassword());
			return "views/login";
		}
	}

	@GetMapping("/register")
	public String register() {
		return "views/registration";
	}
	
	@GetMapping("/blogs")
	public ModelAndView blogs() {
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/blogs");
		modelAndView.addObject("blogsList", blogsList);
		modelAndView.addObject("usersList", users.values());
		
		return modelAndView;
	}

}
