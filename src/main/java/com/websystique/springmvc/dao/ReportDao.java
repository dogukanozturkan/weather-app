package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.Report;

public interface ReportDao {

	List<Report> findAllReports();

	void saveReport(Report report);

	List<Report> filterReports(String locationId, String queryStatus, String fromDate, String toDate);

	List<Integer> getDistinctAllLocationId();

}
