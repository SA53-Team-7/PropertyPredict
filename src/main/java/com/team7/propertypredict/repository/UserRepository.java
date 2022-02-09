package com.team7.propertypredict.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{


	@Query("select u from User u where u.email = :email")
	User findUserByEmail(@Param("email") String email);
	
	@Query("select u from User u where u.email = :email and u.password = :pwd")
	User findUserByEmailAndPassword(@Param("email") String email, @Param("pwd")  String password);
}
