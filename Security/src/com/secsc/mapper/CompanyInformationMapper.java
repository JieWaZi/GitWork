package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.CompanyDetail;
import com.secsc.entity.CompanyInformation;

public interface CompanyInformationMapper {

	public void insertCompanyInformation(@Param("list")List<CompanyInformation> list);
	
	public String getCompanyName(@Param("uuid")String uuid);
	
	public String getIndustrySort(@Param("companyname")String companyname);
	
	public void insertCompanyDetail(@Param("list")List<CompanyDetail> list);
}
