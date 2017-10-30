package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Location;

@Repository
public class LocationDaoImpl extends AbstractDao<Integer, Location> implements LocationDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Location> findAllLocations() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Location> locations = (List<Location>) criteria.list();
		return locations;
	}

	@Override
	public void deleteLocation(int locationId) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", locationId));
		Location location = (Location) crit.uniqueResult();
		delete(location);
	}

	@Override
	public void saveLocation(Location location) {
		persist(location);
	}

	@Override
	public Location findByLocationId(int locationId) {

		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", locationId));
		Location location = (Location) crit.uniqueResult();

		return location;
	}

	@Override
	public Location findById(Integer id) {
		Location location = getByKey(id);
		return location;
	}

}
