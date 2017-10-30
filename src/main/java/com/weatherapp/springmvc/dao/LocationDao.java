package com.weatherapp.springmvc.dao;

import java.util.List;

import com.weatherapp.springmvc.model.Location;

public interface LocationDao {

	public List<Location> findAllLocations();

	public void deleteLocation(int locationId);

	public void saveLocation(Location location);

	public Location findByLocationId(int locationId);

	public Location findById(Integer id);

}
