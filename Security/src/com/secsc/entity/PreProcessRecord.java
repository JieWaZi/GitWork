package com.secsc.entity;

import java.time.LocalDateTime;




public class PreProcessRecord {
	private String uuid;
	private String preProccessMethod;
	private String datasource;
	private LocalDateTime preProccessDateTime;
	private String userName;
	private String preProccessStatus;
	private String target;

	/**
	 * @Description
	 */
	public PreProcessRecord() {
	}

	/**
	 * @Description
	 * @param uuid
	 * @param preproccessMethod
	 * @param datasource
	 * @param preproccessDateTime
	 * @param username
	 */
	public PreProcessRecord(String uuid, String preProccessMethod, String datasource, LocalDateTime preProccessDateTime,
			String userName, String preProccessStatus, String target) {
		super();
		this.uuid = uuid;
		this.preProccessMethod = preProccessMethod;
		this.datasource = datasource;
		this.preProccessDateTime = preProccessDateTime;
		this.userName = userName;
		this.preProccessStatus = preProccessStatus;
		this.target = target;
	}

	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPreProccessMethod() {
		return preProccessMethod;
	}

	public void setPreProccessMethod(String preProccessMethod) {
		this.preProccessMethod = preProccessMethod;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public LocalDateTime getPreProccessDateTime() {
		return preProccessDateTime;
	}

	public void setPreProccessDateTime(LocalDateTime preProccessDateTime) {
		this.preProccessDateTime = preProccessDateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPreProccessStatus() {
		return preProccessStatus;
	}

	public void setPreProccessStatus(String preProccessStatus) {
		this.preProccessStatus = preProccessStatus;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
