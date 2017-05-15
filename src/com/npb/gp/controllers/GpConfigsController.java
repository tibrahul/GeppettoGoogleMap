package com.npb.gp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpConfig;
import com.npb.gp.services.GpConfigsService;

@Controller("GpConfigsController")
@RequestMapping("/gpconfigs")
public class GpConfigsController {
	@Autowired
	private GpConfigsService the_service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/get_by_name", headers = "Accept=application/json")
	@ResponseBody
	public GpConfig get_by_name(@RequestParam("name") String name) throws Exception {
		try {
			return the_service.get_by_name(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
