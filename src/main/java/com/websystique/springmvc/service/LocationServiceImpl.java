package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.LocationDao;
import com.websystique.springmvc.model.Location;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDAO;

	public List<Location> findAllLocations() {
		return locationDAO.findAllLocations();
	}

	public void deleteLocation(int locationId) {
		locationDAO.deleteLocation(locationId);
	}

	public void saveLocation(Location location) {
		locationDAO.saveLocation(location);
	}

	public Location findByLocationId(int locationId) {
		Location location = locationDAO.findByLocationId(locationId);
		return location;
	}

	public void updateLocation(Location location) {
		Location entity = locationDAO.findById(location.getId());
		if (entity != null) {
			entity.setLocationName(location.getLocationName());
		}

	}
}
