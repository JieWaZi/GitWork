package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.DataAnalysisRecord;


public interface DataAnalysisMapper {

	public List<DataAnalysisRecord> getDataAnalysisRecords(@Param("method")String method);
	
	public List<DataAnalysisRecord> getDataAnalysisRecords();
	
	
	public void insertDataAnalysisRecord(@Param("record")DataAnalysisRecord record);
	
}
