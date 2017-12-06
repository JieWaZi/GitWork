package com.secsc.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service("authenticationFailureHandler")
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
		if (isAjax) {
			if (exception instanceof UsernameNotFoundException) {
				response.getWriter().print("user not found");
			}else if (exception instanceof LockedException) {
				response.getWriter().print("user is locked");
			}else {
				response.getWriter().print("failure");
			}
			
			response.getWriter().flush();
		} else {
			super.onAuthenticationFailure(request, response, exception);
		} 
	}
}
