/**  
 * @Title StackBarEntity.java
 * @Package com.secsc.beans.charts
 * @author Arvin (Arvinsc@foxmail.com)
 * 2017年8月14日
 * File Name: StackBarEntity.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.beans.charts;

import java.util.Map;

/**
 * @ClassName StackBarEntity
 * @Description TODO
 * @author Arvin (Arvinsc@foxmail.com)
 * @version 1.0 Build 0000, 2017年8月14日 下午4:57:03, TODO,
 */
// TODO Restructure before production. Modify position of this file and some
// function
public class StackEntity {

	/*
	 * Details of following attributes, reference to @see StackBarChart
	 */

	private Map<String, Number> attributes;
	private String name;
	private String stackName;

	/**
	 * @Description TODO
	 */
	public StackEntity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Description TODO
	 * @param attributes
	 * @param name
	 * @param stackName
	 */
	public StackEntity(Map<String, Number> attributes, String name,
			String stackName) {
		super();
		this.attributes = attributes;
		this.name = name;
		this.stackName = stackName;
	}

	public Map<String, Number> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Number> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStackName() {
		return stackName;
	}

	public void setStackName(String stackName) {
		this.stackName = stackName;
	}

}
