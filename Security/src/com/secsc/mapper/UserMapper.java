package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.myUser;

public interface UserMapper {

	public List<myUser> getUsers(@Param("username")String username);
	
	public List<String> getAuthorities(@Param("username")String username);
	
	public void updateAccountNonLocked(@Param("username")String username);
}
