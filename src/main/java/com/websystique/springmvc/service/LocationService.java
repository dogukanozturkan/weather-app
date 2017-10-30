package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.Location;

public interface LocationService {
	
	public List<Location> findAllLocations(); 
	
	void deleteLocation(int locationId);

	void saveLocation(Location location);

	public Location findByLocationId(int locationId);

	public void updateLocation(Location location);

}
