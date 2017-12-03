package com.secsc.entity;

import java.time.LocalDateTime;
import java.util.Date;



public class UploadRecord {
	
	private String uuid;
	
	private String originalName;
	
	private String fileType;
	
	private String filePath;
	
	private long fileSize;
	
	private LocalDateTime uploadDateTime;
	
	private String username;
	
	private String uploadTarget;
	

	/**
	 * @Description TODO
	 */
	public UploadRecord() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Description TODO
	 * @param uuid
	 * @param originalName
	 * @param fileType
	 * @param uploadDateTime
	 * @param username
	 * @param uploadTarget
	 */
	public UploadRecord(String uuid, String originalName, String fileType,
			long fileSize, String filePath, LocalDateTime uploadDateTime,
			String username, String uploadTarget) {
		super();
		this.uuid = uuid;
		this.originalName = originalName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.uploadDateTime = uploadDateTime;
		this.username = username;
		this.uploadTarget = uploadTarget;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public LocalDateTime getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(LocalDateTime uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUploadTarget() {
		return uploadTarget;
	}

	public void setUploadTarget(String uploadTarget) {
		this.uploadTarget = uploadTarget;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
