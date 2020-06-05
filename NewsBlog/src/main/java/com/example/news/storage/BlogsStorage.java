package com.example.news.storage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.example.news.models.BlogItem;
import com.example.news.models.User;

import lombok.Getter;

@Component
@Getter
public class BlogsStorage {
	
	private ArrayList<BlogItem> blogsList;
	
	public BlogsStorage() {
		this.blogsList = new ArrayList<BlogItem>() {
			{
				add(new BlogItem("Было бы неплохо сейчас махнуть куда-нибудь подальше.",
						"demo.jpg",
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
	}
	
	public void addBlogItem(BlogItem blogItem) {
		this.blogsList.add(blogItem);
	}

}
