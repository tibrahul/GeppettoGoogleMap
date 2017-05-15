package com.npb.gp.social;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * @author Rashmi
 * @date 8 Sept/2015
 * This class is the custom filter which is used to cater the spring security for social users
 */
public class GpSocialAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter implements
		ApplicationContextAware {

	public static final String DEFAULT_FILTER_PROCESS_URL = "/j_spring_social_security_check";
	private ApplicationContext ctx;
	private String email = "";
	private String username = "";
	private String socialtype = "";

	@Autowired
	protected GpSocialAuthenticationFilter(String DEFAULT_FILTER_PROCESS_URL) {
		super(DEFAULT_FILTER_PROCESS_URL);
	}

	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException,
			IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		email = request.getParameter("email");
		username = request.getParameter("username");
		socialtype = request.getParameter("socialtype");
		
		
		if(socialtype.equals("fb") && (email==null || email.equals("undefined"))){
			email = username+".facebook.com";
		}
		
		GpSocialAuthenticationToken token = new GpSocialAuthenticationToken(
				email, "geppetto");
		token.setDetails(authenticationDetailsSource.buildDetails(request));

		AuthenticationManager authenticationManager = getAuthenticationManager();
		Authentication authentication = authenticationManager
				.authenticate(token);
		System.out.println("Logging in with "
				+ authentication.getPrincipal());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Create a new session and add the security context.
		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT",
				SecurityContextHolder.getContext());
		return authentication;
	}

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}
/*
	public GpFacebookHelper getFacebookHelper() {
		return facebookHelper;
	}

	public void setFacebookHelper(GpFacebookHelper facebookHelper) {
		this.facebookHelper = facebookHelper;
	}
*/}