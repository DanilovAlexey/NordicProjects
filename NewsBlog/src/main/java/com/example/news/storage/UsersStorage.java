package com.example.news.storage;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import com.example.news.models.User;

@Component
public class UsersStorage {

	private ConcurrentHashMap<String, User> users;

	public UsersStorage() {
		this.users = new ConcurrentHashMap<String, User>() {
			private static final long serialVersionUID = 8472126213383902103L;

			{
				put("admin@admin.com", new User("admin@admin.com", "Admin", "12345", User.DEFAULT_AVATAR));
			}
		};
	}

	public boolean checkUserAuth(String login, String password) {
		return this.users.containsKey(login) ? this.users.get(login).getPassword().equals(password) : false;
	}

	public void addUser(User user) {
		this.users.put(user.getEmail(), user);
	}

	public ConcurrentHashMap<String, User> getUsers() {
		return users;
	}


}
