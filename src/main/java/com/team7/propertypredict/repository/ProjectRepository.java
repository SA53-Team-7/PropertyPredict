package com.team7.propertypredict.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team7.propertypredict.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Query(value = "SELECT * FROM projects", nativeQuery = true)
	List<Project> findAllProjects();
	
	@Query(value = "SELECT * FROM projects LIMIT 20", nativeQuery = true)
	List<Project> getTop20Projects();

	@Query(value = "Select * FROM projects WHERE name LIKE %:searchString% OR street LIKE %:searchString%", nativeQuery = true)
	ArrayList<Project> searchProjects(@Param ("searchString") String searchString);
	
	@Query(value = "SELECT project_id, name, segment, street, x, y FROM projects INNER JOIN transactions ON project_id = project_project_id WHERE district = :district ORDER BY RAND() LIMIT 2", nativeQuery = true)
	ArrayList<Project> getMobileRecommendationsByDistrict(@Param ("district") String district);

	@Query("Select p from Project p where p.street like %:street%")
	ArrayList<Project> findProjectsByStreet(@Param ("street") String street);
	
	@Query("Select p from Project p where p.projectId = :pid")
	Project findProjectById(@Param ("pid") Integer pid);
	
	@Query(value = "SELECT * from projects p where p.name = :name", nativeQuery = true)
	Project findProjectByName(@Param ("name") String name);
	
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
	
	@Query("Select distinct p.segment from Project p")
	ArrayList<String> findDistinctSegments();
	
	@Query(value ="SELECT distinct p.* FROM projects p INNER JOIN transactions t ON p.project_id = t.project_project_id "
			+ "WHERE t.district LIKE :district% AND t.prop_type LIKE :type% AND p.segment LIKE :segment% AND (p.name LIKE %:searchStr% OR p.street LIKE %:searchStr%)", nativeQuery = true) 
	ArrayList<Project> searchProjectsWeb(@Param ("searchStr") String searchStr, @Param("segment") String segment, @Param("district") String district, @Param("type") String type);
	
	@Query(value ="SELECT distinct t.prop_type FROM projects p INNER JOIN transactions t ON p.project_id = t.project_project_id "
			+ "WHERE t.district LIKE :district% AND t.prop_type LIKE :type% AND p.segment LIKE :segment% AND (p.name LIKE %:searchStr% OR p.street LIKE %:searchStr%)", nativeQuery = true) 
	ArrayList<String> findDistinctTypeByPara(@Param ("searchStr") String searchStr, @Param("segment") String segment, @Param("district") String district, @Param("type") String type);
	
	@Query(value ="SELECT distinct t.tenure FROM projects p INNER JOIN transactions t ON p.project_id = t.project_project_id "
			+ "WHERE t.district LIKE :district% AND t.prop_type LIKE :type% AND p.segment LIKE :segment% AND (p.name LIKE %:searchStr% OR p.street LIKE %:searchStr%)", nativeQuery = true) 
	ArrayList<String> findDistinctTenureByPara(@Param ("searchStr") String searchStr, @Param("segment") String segment, @Param("district") String district, @Param("type") String type);

	@Query("Select p.x from Project p where p.projectId = :pid")
	String findXById(@Param ("pid") Integer pid);
	
	@Query("Select p.y from Project p where p.projectId = :pid")
	String findYById(@Param ("pid") Integer pid);
	
	@Query(value = "SELECT * FROM projects p join projects_users pu ON p.project_id = pu.projects_project_id WHERE pu.users_user_id = :uid", nativeQuery = true)
	List<Project> findAllShortlistProjects(@Param ("uid") Integer uid);
	
	@Query(value = "SELECT p.street FROM projects p where p.name = :name", nativeQuery = true)
	List<String> findAllStreetFromProjectName(@Param ("name") String name);
	
	@Query(value = "SELECT * from projects p where p.name = :name and p.street = :street", nativeQuery = true)
	Project findProjectByNameAndStreet(@Param ("name") String name, @Param ("street") String street);
}
