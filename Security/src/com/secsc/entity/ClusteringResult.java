
package com.secsc.entity;



public class ClusteringResult {
	
	private String uuid;
	
	private float x2D;
	
	private float y2D;
	
	private String clustertag;
	
	private String clustertagalias;
	
	private String clusteringbatchID;
	
	private String companyName;

	/**
	 * @Description TODO
	 */
	public ClusteringResult() {
		// TODO Auto-generated constructor stub
	}

	public ClusteringResult(String uuid, float x2d, float y2d, String clustertag, String clustertagalias,
			String clusteringbatchID, String companyName) {
		super();
		this.uuid = uuid;
		x2D = x2d;
		y2D = y2d;
		this.clustertag = clustertag;
		this.clustertagalias = clustertagalias;
		this.clusteringbatchID = clusteringbatchID;
		this.companyName = companyName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public float getX2D() {
		return x2D;
	}

	public void setX2D(float x2d) {
		x2D = x2d;
	}

	public float getY2D() {
		return y2D;
	}

	public void setY2D(float y2d) {
		y2D = y2d;
	}

	public String getClustertag() {
		return clustertag;
	}

	public void setClustertag(String clustertag) {
		this.clustertag = clustertag;
	}

	public String getClustertagalias() {
		return clustertagalias;
	}

	public void setClustertagalias(String clustertagalias) {
		this.clustertagalias = clustertagalias;
	}

	public String getClusteringbatchID() {
		return clusteringbatchID;
	}

	public void setClusteringbatchID(String clusteringbatchID) {
		this.clusteringbatchID = clusteringbatchID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	

}
