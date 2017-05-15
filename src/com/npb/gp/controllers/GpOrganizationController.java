package com.npb.gp.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.domain.core.GpOrganization;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.services.GpOrganizationService;
import com.npb.gp.services.GpProjectService;

@Controller("GpOrganizationController")
@RequestMapping("/organization")
public class GpOrganizationController {

	
	private GpOrganizationService organization_service;
	
	@RequestMapping(value = "/create_organization/", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public GpOrganization registerUser(@RequestBody GpOrganization new_organization,GpUser user) throws Exception{
		
		GpOrganization organization = null;
		
		System.out.println("--- in controlller -> "+new_organization.toString());
		
		return organization_service.create_project(new_organization, GpUserUtils.getLoggedUser());
	}

	
	@RequestMapping(value = "/getOrganization_by_user_id/{userid}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public GpOrganization getOrganization_by_user_id(@PathVariable("userid") Long userid) throws Exception{
		
		return organization_service.getOrganization_by_user_id(userid);
	}

	
	public GpOrganizationService getOrganization_service() {
		return organization_service;
	}

	@Resource(name="GpOrganizationService")
	public void setOrganization_service(GpOrganizationService organization_service) {
		this.organization_service = organization_service;
	}

}
