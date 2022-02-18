package com.team7.propertypredict.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "select * from users u where u.email = :email", nativeQuery = true)
	User findUserByEmail(@Param("email") String email);
	
	@Query(value = "select * from users u where u.email = :email and u.password = :pwd", nativeQuery = true)
	User findUserByEmailAndPassword(@Param("email") String email, @Param("pwd")  String password);
	
	@Query(value = "select * from users u where u.user_id = :uid", nativeQuery = true)
	User findUserById(@Param("uid") Integer uid);

	ArrayList<User> findByProjectsIsNotNull();

	ArrayList<User> findByUserIdIsNotNull();
}