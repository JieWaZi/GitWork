package com.secsc.entity;

import javax.persistence.Entity;

@Entity
public class Diagram {
	private String uuid;
	private String title;
	private String diagramType;
	private String datasourceUuid;

	/**
	 * @Description TODO
	 */
	public Diagram() {
		// TODO Auto-generated constructor stub
	}

	public Diagram(String uuid, String title, String diagramType, String datasourceUuid) {
		super();
		this.uuid = uuid;
		this.title = title;
		this.diagramType = diagramType;
		this.datasourceUuid = datasourceUuid;
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


	public String getDiagramType() {
		return diagramType;
	}

	public void setDiagramType(String diagramType) {
		this.diagramType = diagramType;
	}

	public String getDatasourceUuid() {
		return datasourceUuid;
	}

	public void setDatasourceUuid(String datasourceUuid) {
		this.datasourceUuid = datasourceUuid;
	}


	

}
