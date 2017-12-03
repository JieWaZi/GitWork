package com.secsc.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;


@Entity
public class Report {
	private String uuid;
	private String title;
	private String texts;
	private LocalDateTime generateDateTime;
	private String industrySort;
	private String username;
	private String diagramUuid;
	
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Report(String uuid, String title, String texts, LocalDateTime generateDateTime, String industrySort,
			String username, String diagramUuid) {
		super();
		this.uuid = uuid;
		this.title = title;
		this.texts = texts;
		this.generateDateTime = generateDateTime;
		this.industrySort = industrySort;
		this.username = username;
		this.diagramUuid = diagramUuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public LocalDateTime getGenerateDateTime() {
		return generateDateTime;
	}

	public void setGenerateDateTime(LocalDateTime generateDateTime) {
		this.generateDateTime = generateDateTime;
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

	public String getDiagramUuid() {
		return diagramUuid;
	}

	public void setDiagramUuid(String diagramUuid) {
		this.diagramUuid = diagramUuid;
	}


	
}
