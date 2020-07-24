package com.example.storage.service;

import java.util.List;

import com.example.storage.model.User;

public interface UserService {

	User addUser(User user);
	User getUser (String userEmail);
	User getUserById (Integer id);
	List<User> getAllUsers();
	User updateUser(User user);
	void deleteUser(User user);
	
}
