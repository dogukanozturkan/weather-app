package com.weatherapp.springmvc.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEATHER_REPORT")
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4833426557471210410L;

	public Report() {
	}

	public Report(int reportUserId, Timestamp reportTime, int reportLocationId, String reportUserIpAddress, String reportResult, long reportPassingTime, String reportStatus) {

		this.reportUserId = reportUserId;
		this.reportTime = reportTime;
		this.reportLocationId = reportLocationId;
		this.reportUserIpAddress = reportUserIpAddress;
		this.reportResult = reportResult;
		this.reportPassingTime = reportPassingTime;
		this.reportStatus = reportStatus;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "SORGULAYAN_KULLANICI_ID")
	private int reportUserId;

	@Column(name = "SORGULAMA_ZAMANI")
	private Timestamp reportTime;

	@Column(name = "SORGULAMA_YAPILAN_LOKASYON_ID")
	private int reportLocationId;

	@Column(name = "SORGULAMA_YAPAN_KULLANICI_IP_ADRESI")
	private String reportUserIpAddress;

	@Column(name = "SORGULAMA_SONUCU")
	private String reportResult;

	@Column(name = "SORGULAMA_SONUC_GETIRME_SURESI")
	private long reportPassingTime;

	@Column(name = "SORGULAMA_DURUMU")
	private String reportStatus;

	public int getReportUserId() {
		return reportUserId;
	}

	public void setReportUserId(int reportUserId) {
		this.reportUserId = reportUserId;
	}

	public Timestamp getReportTime() {
		return reportTime;
	}

	public void setReportTime(Timestamp reportTime) {
		this.reportTime = reportTime;
	}

	public int getReportLocationId() {
		return reportLocationId;
	}

	public void setReportLocationId(int reportLocationId) {
		this.reportLocationId = reportLocationId;
	}

	public String getReportUserIpAddress() {
		return reportUserIpAddress;
	}

	public void setReportUserIpAddress(String reportUserIpAddress) {
		this.reportUserIpAddress = reportUserIpAddress;
	}

	public String getReportResult() {
		return reportResult;
	}

	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}

	public long getReportPassingTime() {
		return reportPassingTime;
	}

	public void setReportPassingTime(long reportPassingTime) {
		this.reportPassingTime = reportPassingTime;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

}
