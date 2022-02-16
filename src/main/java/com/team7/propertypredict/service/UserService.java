package com.team7.propertypredict.service;

import org.springframework.stereotype.Service;

import com.team7.propertypredict.model.User;

@Service
public interface UserService {
	
	// Verify if it user input the correct email and password
	User authenticate(String email, String password);
	
	User findUserByEmailAndPassword(String email, String password);
	
	User findUserByEmail(String email);
	
	void save(User user);
	
	User findUserById(Integer uid);
}
