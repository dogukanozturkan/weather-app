package com.weatherapp.springmvc.service;

import java.util.List;

import com.weatherapp.springmvc.model.Report;

public interface ReportService {

	public List<Report> findAllReports();

	void saveReport(Report report);

	public List<Report> filterReports(String locationId, String queryStatus, String fromDate, String toDate);

	public List<Integer> getDistinctAllLocationId();
}
