package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.ReportDao;
import com.websystique.springmvc.model.Report;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDao reportDao;

	public List<Report> findAllReports() {
		return reportDao.findAllReports();
	}

	public void saveReport(Report report) {
		reportDao.saveReport(report);
	}

	public List<Report> filterReports(String locationId, String queryStatus, String fromDate, String toDate) {
		return reportDao.filterReports(locationId, queryStatus, fromDate, toDate);
	}

	public List<Integer> getDistinctAllLocationId() {
		return reportDao.getDistinctAllLocationId();
	}

}
