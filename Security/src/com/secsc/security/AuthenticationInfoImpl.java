package com.secsc.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service("authInfo")
public class AuthenticationInfoImpl implements AuthenticationInfo {
	private Object authObject;
	private boolean isAuthenticated = false;
	private UserDetails userDetails;
	private Authentication authentication;
	private String topAuthoritie;

	/**
	 * @Description TODO
	 */
	public AuthenticationInfoImpl() {
	}

	public void updateAuthStatus() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		authObject = authentication.getPrincipal();
		isAuthenticated = authentication.isAuthenticated();
		if (isAuthenticated&&authObject instanceof UserDetails) {
			userDetails=(UserDetails)authObject;
			String authorities=userDetails.getAuthorities().toString();
			if(authorities.indexOf("ROLE_ADMIN")>=0){
				topAuthoritie="政府管理人员";
			}else if (authorities.indexOf("ROLE_USER")>=0) {
				topAuthoritie="企业管理人员";
			}
		}
	}


	@Override
	public boolean isAuthenticated() {
		updateAuthStatus();
		return isAuthenticated;
	}


	@Override
	public UserDetails getUserDetails() {
		updateAuthStatus();
		return userDetails;
	}


	@Override
	public Authentication getAuthentication() {
		updateAuthStatus();
		return authentication;
	}

	@Override
	public String getTopAuthority() {
		updateAuthStatus();
		return topAuthoritie;
	}


}
