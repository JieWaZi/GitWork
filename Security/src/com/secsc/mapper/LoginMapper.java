package com.secsc.mapper;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.User;

public interface LoginMapper {
	
	public User findByPassword(@Param("password")String password);

}
