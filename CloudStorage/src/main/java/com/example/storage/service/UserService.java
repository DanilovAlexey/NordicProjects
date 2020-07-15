package com.example.storage.service;

import java.util.List;

import com.example.storage.model.User;

public interface UserService {

	User addUser(User user);
	User getUser (String userEmail);
	List<User> getAllUsers();
}
