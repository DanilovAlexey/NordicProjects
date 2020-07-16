package com.example.storage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.storage.model.User;
import com.example.storage.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
	@Override
	public User addUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User getUser(String userEmail) {
		return userRepository.findByUserEmail(userEmail);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
