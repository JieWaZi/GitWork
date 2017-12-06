package com.secsc.security.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.secsc.entity.myUser;
import com.secsc.mapper.UserMapper;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Resource
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetails> users = loadUsersByUsername(username);
		if (users.size() == 0) {
			throw new UsernameNotFoundException("Username is not found");
		}
		UserDetails user = users.get(0);
		if (user.getAuthorities().size() == 0) {
			throw new UsernameNotFoundException("There is no GrantedAuthority");
		}
		return user;
	}

	
	protected List<UserDetails> loadUsersByUsername(String username) {
		List<myUser> lists= userMapper.getUsers(username);
		List<UserDetails> userDetailsList=new ArrayList<UserDetails>();
		for (myUser myuser : lists) {
			User user=new User(myuser.getUsername(),myuser.getPassword(),
					myuser.isEnabled(),myuser.isAccountNonExpired(),
					myuser.isCredentialsNonExpired(),myuser.isAccountNonLocked(),
					loadUserAuthorities(username));
			userDetailsList.add(user);
		}
		return userDetailsList;
	}
	
	protected List<SimpleGrantedAuthority> loadUserAuthorities(String username) {
		List<String> authoritiesList=userMapper.getAuthorities(username);
		List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
		for (String role : authoritiesList) {
			list.add(new SimpleGrantedAuthority(role));
		}
		return list;
	}
	

}
