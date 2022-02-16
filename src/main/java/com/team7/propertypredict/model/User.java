package com.team7.propertypredict.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Size(max = 10)
	@Column(name = "name")
	private String username;
	
	@Column(name = "password", nullable = false)
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@Column(name = "email", nullable = false, unique=true)
	@Email
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	private String districtInterest;
	
	@ManyToMany
	@JoinTable(name="projects_users",
	joinColumns = @JoinColumn(name = "users_user_id"),
	inverseJoinColumns = @JoinColumn(name="projects_project_id"))
	private List<Project> projects;

	public User() {
		super();
	}

	public User(Integer userId, @Size(max = 10) @NotBlank(message = "Username is mandatory") String username,
			@NotBlank(message = "Password is mandatory") String password,
			@Email @NotBlank(message = "Email is mandatory") String email, String districtInterest) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.districtInterest = districtInterest;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDistrictInterest() {
		return districtInterest;
	}

	public void setDistrictInterest(String districtInterest) {
		this.districtInterest = districtInterest;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	
} 
