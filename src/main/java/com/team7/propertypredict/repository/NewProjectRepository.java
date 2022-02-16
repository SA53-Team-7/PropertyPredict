package com.team7.propertypredict.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.team7.propertypredict.model.NewProject;

@Repository
public class NewProjectRepository {
	
	public static final String HASH_KEY = "NewProject";
	
	@Autowired
	private RedisTemplate template;
	
	
    public NewProject save(NewProject newProject){
    	template.opsForHash().put(HASH_KEY,String.valueOf(newProject.getId()), JSON.toJSONString(newProject));
        return newProject;
    }

    public List<NewProject> findAll(){
    	List<String> project_str = template.opsForHash().values(HASH_KEY);
    	List<NewProject> projects = new ArrayList<NewProject>();
    	for (String p: project_str) {
    		projects.add(JSON.parseObject(p, NewProject.class)); 
    	}
    	return projects;
//    	User user = JSON.parseObject(objectToJson, User.class);
    }
    
    public NewProject findProductById(int id){
        return (NewProject) template.opsForHash().get(HASH_KEY,id);
    }
}