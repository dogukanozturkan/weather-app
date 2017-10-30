package com.weatherapp.springmvc.dao;

import java.util.List;

import com.weatherapp.springmvc.model.Report;

public interface ReportDao {

	List<Report> findAllReports();

	void saveReport(Report report);

	List<Report> filterReports(String locationId, String queryStatus, String fromDate, String toDate);

	List<Integer> getDistinctAllLocationId();

}
