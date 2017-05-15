package com.npb.gp.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.services.GpCouchService;

@Controller("GpCouchNounController")
@RequestMapping("/couch/")
public class GpCouchNounController {
	
	private GpCouchService couch_noun_service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/import_noun/{project_id}", headers = "Accept=application/json")
	@ResponseBody
	public void nounmigration(@PathVariable("project_id")long project_id) throws Exception{
		System.out.println("Importing noun from Couch");
		this.couch_noun_service.create_noun(project_id,GpUserUtils.getLoggedUser());
	
	}

	public GpCouchService getCouch_noun_service() {
		return couch_noun_service;
	}
	
	@Resource(name = "GpCouchService")
	public void setCouch_noun_service(GpCouchService couch_noun_service) {
		this.couch_noun_service = couch_noun_service;
	}
	
	

}
