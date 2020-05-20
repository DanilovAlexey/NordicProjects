package com.example.news.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.news.models.NewsItem;

@Controller
public class HomeController {
	
	ArrayList<NewsItem> newsList = new ArrayList<NewsItem>() {{
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

	@GetMapping({ "", "/", "home" })
	public ModelAndView  home() {
		
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/home");
		modelAndView.addObject("newsList", newsList);
		
		return modelAndView;
	}

	@GetMapping("/login")
	public String login() {
		return "views/login";
	}

	@GetMapping("/register")
	public String register() {
		return "views/registration";
	}
	
	@GetMapping("/blogs")
	public String blogs() {
		return "views/blogs";
	}

}
