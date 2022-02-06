package com.team7.propertypredict.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.Project;

import helper.ProjectHelper;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Query(value = "SELECT * FROM projects", nativeQuery = true)
	List<Project> findAllProjects();
	
	@Query(value = "SELECT * FROM projects LIMIT 20", nativeQuery = true)
	List<Project> getTop20Projects();

	@Query("Select p from Project p where p.street like %:street%")
	ArrayList<Project> findProjectsByStreet(@Param ("street") String street);
	
	@Query("Select p from Project p where p.projectId = :pid")
	Project findProjectById(@Param ("pid") Integer pid);
	
	@Query("Select distinct t.tenure from Project p join p.transactions t where p.projectId = :pid")
	String findTenureByProjectId(@Param ("pid") Integer pid);
	
	@Query("Select distinct s.type from Project p join p.transactions t join t.saleType s where p.projectId = :pid")
	String findSaleTypeByProjectId(@Param ("pid") Integer pid);


	@Query("Select AVG(t.price) from Project p join p.transactions t where p.projectId = :pid")
	Double findAveragePriceByProjectId(@Param ("pid") Integer pid);
	
	@Query("Select SUM(t.noOfUnits) from Project p join p.transactions t where p.projectId = :pid")
	Integer findTotalUnitsByProjectId(@Param ("pid") Integer pid);
	
	@Query("Select MIN(t.floorArea) from Project p join p.transactions t where p.projectId = :pid")
	Double findMinAreaByProjectId(@Param ("pid") Integer pid);
	
	@Query("Select MAX(t.floorArea) from Project p join p.transactions t where p.projectId = :pid")
	Double findMaxAreaByProjectId(@Param ("pid") Integer pid);
	
	@Query("Select t.floorRange from Project p join p.transactions t where p.projectId = :pid")
	ArrayList<String> findfloorRangeByProjectId(@Param ("pid") Integer pid);
	
}
