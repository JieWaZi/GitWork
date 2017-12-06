package com.secsc.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.secsc.entity.myUser;
import com.secsc.mapper.LoginMapper;
import com.secsc.security.AuthenticationInfo;
import com.secsc.security.AuthenticationInfoImpl;

@RestController
@RequestMapping("/login")
public class LoginOperationController {

	@Resource
	private LoginMapper loginMapper;
	
	@Resource(name="authInfo")
	private AuthenticationInfo authenticationInfo;
	
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
	
	
}
