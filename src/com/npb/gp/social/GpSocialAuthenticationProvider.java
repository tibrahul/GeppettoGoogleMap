package com.npb.gp.social;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.npb.gp.dao.mysql.GpAuthorityDao;
import com.npb.gp.dao.mysql.GpUserDao;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.services.GpUserRegistrationService;

/**
 * @author Rashmi
 * @date 8 Sept/2015
 * This class is the authentication provider for social users
 */
@SuppressWarnings("deprecation")
public class GpSocialAuthenticationProvider implements AuthenticationProvider {

	private String[] roles;

	private GpUser gpuser = null;

	private GpAuthorityDao gpAuthorityDao;

	public GpAuthorityDao getGpAuthorityDao() {
		return gpAuthorityDao;
	}
	
	@Resource(name = "GpAuthorityDao")
	public void setGpAuthorityDao(GpAuthorityDao gpAuthorityDao) {
		this.gpAuthorityDao = gpAuthorityDao;
	}
	private GpUserDao gpuser_Dao;

	public GpUserDao getGpuser_Dao() {
		return gpuser_Dao;
	}

	@Resource(name = "GpUserDao")
	public void setGpuser_dao(GpUserDao gpuser_Dao) {
		this.gpuser_Dao = gpuser_Dao;
	}

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		GpSocialAuthenticationToken facebookAuthentication = (GpSocialAuthenticationToken) authentication;
		String username = facebookAuthentication.getUsername();

		gpuser = gpuser_Dao.findUser(username);
		if (gpuser == null) {
			// create a new user in DB
			GpUser newgpUser = new GpUser();
			newgpUser.setUsername(username);
			newgpUser.setPassword("gepetto");
			gpuser = newgpUser;

			GpUserRegistrationService gpUserRegistrationService = new GpUserRegistrationService();
			try {
				gpuser = gpUserRegistrationService.lightRegisterUser(newgpUser,gpuser_Dao,gpAuthorityDao);
				
			} catch (Exception e) {
				System.out.print("CANNOT REGISTER USER");
			}
		} 
		
		if (facebookAuthentication.getUsername() == null)
			throw new BadCredentialsException(
					"User not authenticated through facebook");

		if (roles == null) {
			roles = new String[] {};
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new GrantedAuthorityImpl(role));
		}

		GpSocialAuthenticationToken succeedToken = new GpSocialAuthenticationToken(
				facebookAuthentication.getUsername(),
				facebookAuthentication.getPassword(), gpuser, authorities);
		succeedToken.setDetails(authentication.getDetails());

		return succeedToken;
	}

	public boolean supports(Class<? extends Object> authentication) {
		boolean supports = GpSocialAuthenticationToken.class
				.isAssignableFrom(authentication);
		return supports;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String[] getRoles() {
		return roles;
	}
}