package com.npb.gp.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.services.GpMongoNounService;


@Controller("GpMongoNounController")
@RequestMapping("/mongo/")
public class GpMongoNounController {
	
	
    private GpMongoNounService mongo_noun_service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/import_noun/{project_id}", headers = "Accept=application/json")
	@ResponseBody
	public void nounmigration(@PathVariable("project_id")long project_id) throws Exception{
		System.out.println("Importing noun from mongo");
		this.mongo_noun_service.create_noun(project_id,GpUserUtils.getLoggedUser());
	
	}

	public GpMongoNounService getMongo_noun_service() {
		return mongo_noun_service;
	}

	@Resource(name = "GpMongoNounService")
	public void setGpMongo_noun_service(GpMongoNounService mongo_noun_service) {
		this.mongo_noun_service = mongo_noun_service;
	}
	
	

}
