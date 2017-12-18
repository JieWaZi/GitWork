package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.PreProcessRecord;

public interface PreProccessMapper {

	
	public List<PreProcessRecord> getPreProcessRecords();
	
	public void insertPreProcessRecord(@Param("record")PreProcessRecord record);
	
	public void updatePreprocessStatus(@Param("record")PreProcessRecord record);
	
	public PreProcessRecord queryRecordByUUID(@Param("uuid")String uuid);
	
	public List<PreProcessRecord> queryRecordsByMethod(@Param("record")PreProcessRecord record);
	
}
