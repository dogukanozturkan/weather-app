package com.weatherapp.springmvc.dao;

import java.util.List;

import com.weatherapp.springmvc.model.UserProfile;


public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
