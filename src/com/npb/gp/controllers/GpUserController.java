package com.npb.gp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.dao.mysql.GpAuthorityDao;
import com.npb.gp.dao.mysql.GpUserDao;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.services.GpUserRegistrationService;
import com.npb.gp.services.GpUserService;

@Controller("GpUserController")
@RequestMapping("/updateuser")
public class GpUserController {

	private GpUserService userService;

	public GpUserService getUserService() {
		return userService;
	}

	@Resource(name = "GpUserService")
	public void setVerbService(GpUserService userService) {
		this.userService = userService;
	}

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
	
	private GpUserRegistrationService userRegistrationService;

	public GpUserRegistrationService getUserRegistrationService() {
		return userRegistrationService;
	}

	@Resource(name = "GpUserRegistrationService")
	public void setUserRegistrationService(GpUserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}

		
	@RequestMapping(value = "/update_user/", method = RequestMethod.PUT, headers = "Accept=application/json")
	@ResponseBody
	public void updateUser(@RequestBody GpUser user) throws Exception{
			
		 userService.Update_docker_json(user);	
	}
	
	@RequestMapping(value = "/update_containers_status/", method = RequestMethod.PUT, headers = "Accept=application/json")
	@ResponseBody
	public void update_containers_status(@RequestBody GpUser user) throws Exception{
			
		 userService.Update_docker_json_status(user);	
	}
	
	@RequestMapping(value = "/get_all_users/", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<GpUser> get_all_users() throws Exception{
		return  userService.findAllUsers();	
		 
	}
	
	@RequestMapping(value = "/register_user/", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public GpUser registerUser(@RequestBody GpUser newuser) throws Exception{
		
		GpUser createduser = null;
		
		createduser = userRegistrationService.lightRegisterUser(newuser,gpuser_Dao,gpAuthorityDao);
		return createduser;
	}

}
