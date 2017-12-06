package com.secsc.security.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Data;
import com.secsc.entity.UserAttempts;
import com.secsc.entity.myUser;
import com.secsc.mapper.UserAttemptsMapper;
import com.secsc.mapper.UserMapper;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	private int MAX_ATTEMPTS=5;
	
	@Resource
	MyUserDetailsService myUserDetailsService;
	
	@Resource
	UserAttemptsMapper userAttemptsMapper;
	
	@Resource
	UserMapper userMapper;
	
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		// TODO Auto-generated method stub
		super.setUserDetailsService(myUserDetailsService);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username=authentication.getName();
		UserAttempts user = userAttemptsMapper.getUserAttempts(username);
		try {
	            //调用上层验证逻辑
	            Authentication auth = super.authenticate(authentication);
	            //如果验证通过登录成功则重置尝试次数， 否则抛出异常
	            user.setLastModified(new Date());
	            userAttemptsMapper.resetFailAttempts(user);
	            return auth;
	        } catch (BadCredentialsException e) {
	            //如果验证不通过，则更新尝试次数，当超过次数以后抛出账号锁定异常
	        	
	            if (user == null) {
	                if (isUserExists(username)) {
	                    // 如果之前没有记录，添加一条
	                	UserAttempts userAttempts=new UserAttempts();
	                	userAttempts.setUsername(username);
	                	userAttempts.setLastModified(new Date());
	                    userAttemptsMapper.insertUserAttempts(userAttempts);
	                }
	            } else {
	                if (isUserExists(username)) {
	                    // 存在用户则失败一次增加一次尝试次数
	                	user.setLastModified(new Date());
	                	userAttemptsMapper.updateFailAttempts(user);
	                }
	                if (user.getAttempts() + 1 >= MAX_ATTEMPTS) {
	                    // 大于尝试次数则锁定
	                    userMapper.updateAccountNonLocked(username);
	                    // 并且抛出账号锁定异常
	                    throw new LockedException("用户账号已被锁定，请联系管理员解锁");
	                }
	            }
	            throw e;
	        } catch (LockedException e){
	            //该用户已经被锁定，则进入这个异常
	            String error;
	            UserAttempts userAttempts =
	            		userAttemptsMapper.getUserAttempts(authentication.getName());
	            if(userAttempts != null){
	                Date lastAttempts = userAttempts.getLastModified();
	                error = "用户已经被锁定，用户名 : "
	                        + authentication.getName() + "最后尝试登陆时间 : " + lastAttempts;
	            }else{
	                error = e.getMessage();
	            }
	            throw new LockedException(error);
	        }
		
	}
	
    private boolean isUserExists(String username) {
        boolean result = false;
        List<myUser>  users= userMapper.getUsers(username);
        if (users.size() > 0) {
            result = true;
        }
        return result;
    }
}
