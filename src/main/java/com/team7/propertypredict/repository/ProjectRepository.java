package com.team7.propertypredict.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.Project;

import helper.ProjectHelper;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	//@Query("Select p from projects p where p.street = :street")
	//ArrayList<Project> findProjectsByStreet(@Param ("street") String street);
	
	@Query(value = "SELECT * FROM projects", nativeQuery = true)
	List<Project> findAllProjects();
	
	@Query(value = "SELECT * FROM projects LIMIT 20", nativeQuery = true)
	List<Project> getTop20Projects();

}
