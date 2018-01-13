package com.secsc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.myUser;

public interface UserMapper {

	public List<myUser> getUsers(@Param("username")String username);
	
	public List<myUser> getUsersById(@Param("username")String username);
	
	public List<myUser> getUsersById();
	
	public List<String> getAuthorities(@Param("username")String username);
	
	public void updateAccountNonLocked(@Param("username")String username);
	
	public void updateunlock(@Param("username")String username);
	
	public void updatePassword(@Param("user")myUser user);
	
	public int getUsersCount(@Param("username")String username);
	
	public int getUsersCount();
	
	public void deleteUser(@Param("username")String username);
	
	public void insertUser(@Param("user")myUser user);
	
	public void insertAuthoritie(@Param("map")Map<String, String> map);
}
