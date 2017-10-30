package com.weatherapp.springmvc.dao;

import java.util.List;

import com.weatherapp.springmvc.model.User;

public interface UserDao {

	User findById(int id);

	User findBySSO(String sso);

	void save(User user);

	void deleteBySSO(String sso);

	List<User> findAllUsers();

	User findByUserName(String username);

}
