package com.npb.gp.social;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.npb.gp.domain.core.GpUser;

/**
 * @author Rashmi
 * @date 8 Sept/2015
 * This class represents custom authentication token which is used to cater the spring security for social users
 */
public class GpSocialAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String sessionkey = null;

	private GpUser gpUser=null;
	
	public GpSocialAuthenticationToken() {
		this(null, null, null,null);
	}

	public GpSocialAuthenticationToken(String username, String password) {
		this(username,password, null,null);
	}

	public GpSocialAuthenticationToken(String username, String password, GpUser gpUser,
			List<GrantedAuthority> authorities) {
		super(authorities);
		this.username = username;
		this.password = password;
		this.gpUser = gpUser;
		super.setAuthenticated(true);
	}

	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
		throw new IllegalArgumentException(
				"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
	}

	public Object getCredentials() {
		return String.valueOf(username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

	@Override
	public Object getPrincipal() {
		return gpUser;
	}
}