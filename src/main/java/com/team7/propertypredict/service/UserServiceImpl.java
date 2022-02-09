package com.team7.propertypredict.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.team7.propertypredict.model.User;
import com.team7.propertypredict.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository uRepo;
	
	@Transactional
	public User authenticate(String username, String password) {
		
		User user =  uRepo.findUserByEmailAndPassword(username, password);
		return user;
	}

}
