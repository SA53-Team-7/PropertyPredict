package com.team7.propertypredict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.team7.propertypredict.model.AmenityType;
import com.team7.propertypredict.repository.AmenityTypeRepository;

@Component
public class AmenityTypeServiceImpl implements AmenityTypeService{
	
	@Autowired
	private AmenityTypeRepository atRepo;
	
	@Override
	public List<AmenityType> findAll(){
		return atRepo.findAll();
	}

}
