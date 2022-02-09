package com.team7.propertypredict.service;

import org.springframework.stereotype.Service;

import com.team7.propertypredict.model.User;

@Service
public interface UserService {
	
	User authenticate(String email, String password);
	User findUserByEmail(String email);
	void save(User user);
}
