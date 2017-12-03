/**  
 * @Title PreProccessRecord.java
 * @Package com.secsc.beans.dataPreProccess
 * @author Arvin (Arvinsc@foxmail.com)
 * 2017年7月19日
 * File Name: PreProccessRecord.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.entity;

import java.time.LocalDateTime;


/**
 * @ClassName PreProccessRecord
 * @author Arvin (Arvinsc@foxmail.com)
 * @version 1.0 Build 0000, 2017年7月19日 上午10:31:12,
 */

public class PreProcessRecord {
	private String uuid;
	private String preProccessMethod;
	private String datasource;
	private LocalDateTime preProccessDateTime;
	private String userName;
	private String preProccessStatus;

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
	public PreProcessRecord(String uuid, String preProccessMethod,
			String datasource, LocalDateTime preProccessDateTime,
			String userName, String status) {
		super();
		this.uuid = uuid;
		this.preProccessMethod = preProccessMethod;
		this.datasource = datasource;
		this.preProccessDateTime = preProccessDateTime;
		this.userName = userName;
		this.preProccessStatus = status;
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

	
}
