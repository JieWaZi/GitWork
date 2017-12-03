package com.secsc.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


public interface AuthenticationInfo {

	public boolean isAuthenticated();

	public UserDetails getUserDetails();

	public Authentication getAuthentication();
	
	public String getTopAuthority();

}
