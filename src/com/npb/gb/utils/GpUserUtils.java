package com.npb.gb.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.npb.gp.domain.core.GpUser;

public class GpUserUtils {

	public static GpUser getLoggedUser() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		GpUser user = null;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof GpUser) {
				user = (GpUser) principal;
				System.out.println("Loggedin user: " + user.getUsername());
			}
		}
		return user;

	}

}
