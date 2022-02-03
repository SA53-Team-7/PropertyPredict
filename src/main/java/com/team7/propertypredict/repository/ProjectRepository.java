package com.team7.propertypredict.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Query("Select p from Project p where p.street like %:street%")
	ArrayList<Project> findProjectsByStreet(@Param ("street") String street);

	@Query("Select AVG(t.price) from Project p join p.transactions t where p.projectId = :pid ")
	Double findAveragePriceByProjectId(@Param ("pid") Integer pid);
}
