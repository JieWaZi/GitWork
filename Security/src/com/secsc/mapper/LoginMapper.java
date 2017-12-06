package com.secsc.mapper;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.myUser;

public interface LoginMapper {
	
	public myUser findByPassword(@Param("password")String password);

}
