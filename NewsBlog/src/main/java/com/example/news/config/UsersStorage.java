package com.example.news.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.news.models.User;

import lombok.Getter;

@Component
@Getter
public class UsersStorage {

	private ConcurrentHashMap<String, User> users;

	public UsersStorage() {
		this.users = new ConcurrentHashMap<String, User>() {
			{
				put("admin@admin.com", new User("admin@admin.com", "Vlad", "12345", User.DEFAULT_AVATAR));
				put("ytka@ytka.ru", new User("ytka@ytka.ru", "Utkins", "12345", "alexey.jpg"));
			}
		};
	}

	public boolean checkUserAuth(String login, String password) {
		return this.users.containsKey(login) ? this.users.get(login).getPassword().equals(password) : false;
	}

	public void addUser(User user) {
		this.users.put(user.getEmail(), user);
	}

}
