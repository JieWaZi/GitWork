package com.secsc.mapper;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.UserAttempts;

public interface UserAttemptsMapper {

	 public  void updateFailAttempts(@Param("userAttempts")UserAttempts userAttempts);
	 
	 public   void resetFailAttempts(@Param("userAttempts")UserAttempts userAttempts);
	 
	 public   UserAttempts getUserAttempts(String username);
	 
	 public void insertUserAttempts(@Param("userAttempts")UserAttempts userAttempts);
	 
	
}
