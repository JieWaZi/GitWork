package com.secsc.security.handler;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;


@Service("authenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements Serializable{

	private static final long serialVersionUID = -7868794891110645691L;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
					throws IOException, ServletException {
		String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
		if (isAjax) {
			response.getWriter().print("success");
			response.getWriter().flush();
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		} 
	}
}
