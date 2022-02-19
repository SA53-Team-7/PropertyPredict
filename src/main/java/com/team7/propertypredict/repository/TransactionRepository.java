package com.team7.propertypredict.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.team7.propertypredict.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	@Query(value = "SELECT * FROM transactions WHERE project_project_id = :id", nativeQuery = true)
	List<Transaction> findTransactionsByProjectId(@Param ("id") Integer id);
	
	@Query(value = "SELECT * FROM transactions WHERE project_project_id =:id ORDER BY substring(contract_date, 3, 2), substring(contract_date, 1, 2)", nativeQuery=true)
	List<Transaction> findAllTransactionsByProject(Integer id);
	
	@Query(value = "SELECT distinct floor_area FROM transactions WHERE project_project_id = :id ORDER BY floor_area ASC", nativeQuery=true)
	List<String> findDistinctFloorArea(Integer id);
	
	@Query(value = "SELECT distinct floor_range FROM transactions WHERE project_project_id = :id ORDER BY floor_range ASC", nativeQuery=true)
	List<String> findDistinctFloorRange(Integer id);
	
	@Query(value = "SELECT distinct district FROM transactions WHERE project_project_id = :id ORDER BY district ASC", nativeQuery=true)
	List<String> findDistinctDistrict(Integer id);

	@Query(value = "SELECT distinct tenure FROM transactions WHERE project_project_id = :id ORDER BY tenure ASC", nativeQuery=true)
	List<String> findDistinctTenure(Integer id);
	
	@Query(value = "SELECT distinct prop_type FROM transactions WHERE project_project_id = :id ORDER BY prop_type ASC", nativeQuery=true)
	List<String> findDistinctPropertyTypeByID(Integer id);

	@Query(value = "SELECT distinct district FROM transactions ORDER BY district ASC", nativeQuery=true)
	List<String> findDistinctDistrict();
	
	@Query(value = "SELECT distinct prop_type FROM transactions ORDER BY prop_type ASC", nativeQuery=true)
	List<String> findDistinctPropertyType();
	
	@Query(value = "SELECT project_project_id FROM transactions WHERE contract_date IN (:d1,:d2,:d3,:d4,:d5,:d6) " 
			+ "GROUP BY project_project_id ORDER BY COUNT(*) DESC LIMIT 6", nativeQuery=true)
	List<String> findPopularProjectsByTxn(String d1, String d2, String d3, String d4, String d5, String d6);
	
	@Query(value = "SELECT distinct t.district FROM projects_users s INNER JOIN transactions t ON s.projects_project_id = t.project_project_id "
			+ "WHERE users_user_id = :uid", nativeQuery=true)
	List<String> findInterestedDistricts(Integer uid);
	
	@Query(value = "SELECT distinct project_project_id FROM transactions WHERE district =:district AND price BETWEEN :lower AND :upper", nativeQuery=true)
	List<String> findSimilarProjects(String district, Double lower, Double upper);
	
	@Query(value = "SELECT district FROM transactions t INNER JOIN projects ON project_id = project_project_id WHERE project_id = :id LIMIT 1", nativeQuery = true)
	ArrayList<Transaction> getMobileRecommendationsDistrict(@Param ("id") Integer id);

	@Query(value = "SELECT * FROM transactions WHERE project_project_id = :projectId LIMIT 1;", nativeQuery=true)
	List<Transaction> getTopTransactionByProjectId(int projectId);

}
