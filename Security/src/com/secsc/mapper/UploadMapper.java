package com.secsc.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.UploadRecord;

public interface UploadMapper {

	
	public void insertUploadRecord(@Param("upload")UploadRecord uploadRecord);
	
	public List<UploadRecord> getUploadRecords(@Param("upload")UploadRecord uploadRecord);
	
	public UploadRecord getRecordByID(@Param("uuid")String uuid);
}
