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
	
	@Override
	public User authenticate(String email, String password) {
		
		User user =  findUserByEmailAndPassword(email, password);
		return user;
	}
	
	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		
		User user = uRepo.findUserByEmailAndPassword(email, password);
		return user;
	}
	
	@Override
	public User findUserByEmail(String email) {
		
		User user =  uRepo.findUserByEmail(email);
		return user;
	}
	
	@Override
	@Transactional
	public void save(User user) {
		uRepo.saveAndFlush(user);
	}
	
	@Override
	public User findUserById(Integer uid) {
		return uRepo.findUserById(uid);
	}

}
