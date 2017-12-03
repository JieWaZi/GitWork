package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.Report;

public interface ReportMapper {
	
	public List<Report> getReports();
	
	public List<Report> getReportByIndustrySort(@Param("industrySort")String industrySort);
	
	public Report getReportByUuid(@Param("uuid")String uuid);
	
	public void insertReport(@Param("report")Report report);

}
