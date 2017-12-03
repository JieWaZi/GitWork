

package com.secsc.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;


@Entity
public class DataAnalysisRecord {
	
	private String uuid;
	
	private LocalDateTime jobDateTime;
	
	private String analysisMethod;
	
	private String arithmetic;
	
	private String preprocessUUID;
	
	private String industrySort;
	
	private String username;

	public DataAnalysisRecord() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DataAnalysisRecord(String uuid, LocalDateTime jobDateTime, String analysisMethod, String arithmetic,
			String preprocessUUID, String industrySort, String username) {
		super();
		this.uuid = uuid;
		this.jobDateTime = jobDateTime;
		this.analysisMethod = analysisMethod;
		this.arithmetic = arithmetic;
		this.preprocessUUID = preprocessUUID;
		this.industrySort = industrySort;
		this.username = username;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public LocalDateTime getJobDateTime() {
		return jobDateTime;
	}


	public void setJobDateTime(LocalDateTime jobDateTime) {
		this.jobDateTime = jobDateTime;
	}


	public String getAnalysisMethod() {
		return analysisMethod;
	}


	public void setAnalysisMethod(String analysisMethod) {
		this.analysisMethod = analysisMethod;
	}


	public String getArithmetic() {
		return arithmetic;
	}


	public void setArithmetic(String arithmetic) {
		this.arithmetic = arithmetic;
	}


	public String getPreprocessUUID() {
		return preprocessUUID;
	}


	public void setPreprocessUUID(String preprocessUUID) {
		this.preprocessUUID = preprocessUUID;
	}


	public String getIndustrySort() {
		return industrySort;
	}


	public void setIndustrySort(String industrySort) {
		this.industrySort = industrySort;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


}
