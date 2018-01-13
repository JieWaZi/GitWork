package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.AnalysisCategory;

public interface AnalysisCategoryMapper {

	public List<AnalysisCategory> getAnalysisCategory(@Param("analysistype")String analysistype);
	
	public void insertAnalysisCategory(@Param("analysis") AnalysisCategory analysisCategory);
	
	public List<AnalysisCategory> getAnalysisCategorys();
	
	public int selCount();
	
	public void deleteAnalysisCategory(@Param("arithmetic") String arithmetic);
	
	public String getArithmeticJar(@Param("arithmetic") String arithmetic);
}
