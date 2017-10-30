package com.weatherapp.springmvc.service;

import java.util.List;

import com.weatherapp.springmvc.model.UserProfile;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();
	
}
