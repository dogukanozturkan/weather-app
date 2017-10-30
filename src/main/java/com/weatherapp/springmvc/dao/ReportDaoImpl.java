package com.weatherapp.springmvc.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.weatherapp.springmvc.model.Report;

@Repository
public class ReportDaoImpl extends AbstractDao<Integer, Report> implements ReportDao {

	public List<Report> findAllReports() {
		Criteria criteria = createEntityCriteria().addOrder(Order.desc("reportTime"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Report> report = (List<Report>) criteria.list();
		return report;
	}

	public void saveReport(Report report) {
		persist(report);
	}

	public List<Report> filterReports(String locationId, String queryStatus, String fromDate, String toDate) {
		Criteria criteria = createEntityCriteria().addOrder(Order.desc("reportTime"));

		if (!locationId.equals("HEPSI")) {
			Criterion restQueryStatus = Restrictions.eq("reportLocationId", Integer.parseInt(locationId));
			criteria.add(Restrictions.and(restQueryStatus));
		}
		if (!queryStatus.equals("HEPSI")) {
			Criterion restQueryStatus = Restrictions.eq("reportStatus", queryStatus);
			criteria.add(Restrictions.and(restQueryStatus));
		}
		if (!fromDate.equals("0") && !toDate.equals("0")) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("fromdate:" + fromDate + " todate: " + toDate);
			try {
				Date minDate = formatter.parse(fromDate);
				Date maxDate = formatter.parse(toDate);

				criteria.add(Restrictions.between("reportTime", minDate, maxDate));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Report> report = (List<Report>) criteria.list();
		return report;
	}

	public List<Integer> getDistinctAllLocationId() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("reportLocationId"));
		criteria.setProjection(Projections.distinct(Projections.property("reportLocationId")));
		List<Integer> report = (List<Integer>) criteria.list();
		return report;
	}

}
