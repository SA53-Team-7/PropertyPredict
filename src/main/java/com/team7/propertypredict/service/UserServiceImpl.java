package com.team7.propertypredict.service;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.team7.propertypredict.model.User;
import com.team7.propertypredict.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository uRepo;
	
	@Override
	public User authenticate(String email, String password) {
		User user =  findUserByEmail(email);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (bCryptPasswordEncoder.matches(password, user.getPassword())){		
			return user;
		}
		else {
			return null;
		}
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
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		uRepo.saveAndFlush(user);
	}
	
	@Override
	public User findUserById(Integer uid) {
		return uRepo.findUserById(uid);
	}

	@Override
	public ArrayList<User> findUserHaveShortlist() {
		return uRepo.findByProjectsIsNotNull();
	}

	@Override
	public ArrayList<User> findAllUser() {

		return uRepo.findByUserIdIsNotNull();
	}

}
