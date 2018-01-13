package com.secsc.controllers;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.secsc.entity.myUser;
import com.secsc.mapper.LoginMapper;
import com.secsc.mapper.UserMapper;
import com.secsc.security.AuthenticationInfo;
import com.secsc.security.AuthenticationInfoImpl;

@RestController
@RequestMapping("/login")
public class LoginOperationController {

	@Resource
	private LoginMapper loginMapper;
	
	@Resource(name="authInfo")
	private AuthenticationInfo authenticationInfo;
	
	@Resource
	private UserMapper userMapper;
	
	/**
	 * 判断登陆状态
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/status",method = RequestMethod.GET)
	public Map loginStatus(){
		String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean isAuthenticated=authenticationInfo.isAuthenticated();
		UserDetails userDetails=authenticationInfo.getUserDetails();
		Map<String, String> map=new HashMap<String, String>();
		if (isAuthenticated&&userDetails!=null&&!authentication.equals("anonymousUser")) {
			map.put("status", "true");
			map.put("authorities", authenticationInfo.getTopAuthority());
			map.put("user", userDetails.getUsername());
			return map;
		}else {
			map.put("status", "unlogin");
			return map;
		}
		
	}
	
	
	@RequestMapping(value="/modify",method = RequestMethod.PUT)
	public Map<String, String> modifyPassword(myUser user){
		Map<String, String> map=new HashMap<String, String>();
		String password=user.getPassword();
		Md5PasswordEncoder md5=new Md5PasswordEncoder();
		String newpassword=md5.encodePassword(password, user.getUsername());
		user.setPassword(newpassword);
		userMapper.updatePassword(user);
		return map;
	}
	
	@RequestMapping(value="/create",method = RequestMethod.POST)
	public Map<String, String> create(myUser user,String authoritie){
		Map<String, String> map=new HashMap<String, String>();
		String password=user.getPassword();
		Md5PasswordEncoder md5=new Md5PasswordEncoder();
		String newpassword=md5.encodePassword(password, user.getUsername());
		user.setPassword(newpassword);
		if (authoritie.equals("超级管理员")) {
			map.put(user.getUsername(), "ROLE_ADMIN");
		}else {
			map.put(user.getUsername(), "ROLE_USER");
		}
		
		userMapper.insertUser(user);
		userMapper.insertAuthoritie(map);
		return map;
	}
	
	
	@RequestMapping(value="/unlock",method = RequestMethod.PUT)
	public Map<String, String> unlock(String username){
		Map<String, String> map=new HashMap<String, String>();
		userMapper.updateunlock(username);
		return map;
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	public Map<String, String> delete(String username){
		Map<String, String> map=new HashMap<String, String>();
		userMapper.deleteUser(username);
		return map;
	}
	
	
}
