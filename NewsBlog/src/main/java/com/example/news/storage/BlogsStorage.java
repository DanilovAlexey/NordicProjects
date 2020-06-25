package com.example.news.storage;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import com.example.news.models.BlogItem;

@Component
public class BlogsStorage {

	private ArrayList<BlogItem> blogsList;

	public BlogsStorage() {
		this.blogsList = new ArrayList<BlogItem>() {
			private static final long serialVersionUID = 2793971434965506969L;

			{
				add(new BlogItem("Было бы неплохо сейчас махнуть куда-нибудь подальше.", "demo.jpg", 1591641908L,
						"admin@admin.com"));
				add(new BlogItem(
						"Основатель Telegram Павел Дуров в своем официальном телеграм-канале объявил о прекращении работы над блокчейн-проектом TON."
								+ " Причина — решение суда по иску Комиссии"
								+ " по ценным бумагам и биржам США (SEC), запретившего выпуск"
								+ " криптовалюты Gram. Решение суда Дуров назвал несправедливым и"
								+ " предупредил мир о зависимости от США, которые могут закрыть"
								+ " любой банк или банковский счет в мире.",
						null, 1591641908L, "admin@admin.com"));
			}
		};
	}

	public void addBlogItem(BlogItem blogItem) {
		this.blogsList.add(blogItem);
	}

	public ArrayList<BlogItem> getBlogsList() {
		return blogsList;
	}

}
