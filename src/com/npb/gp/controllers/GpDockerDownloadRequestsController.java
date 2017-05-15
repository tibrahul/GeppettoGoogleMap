package com.npb.gp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.npb.gp.services.GpDockerDownloadRequestsService;

@Controller("GpDockerDownloadRequestsController")
@RequestMapping("/docker/downloadrequests")
public class GpDockerDownloadRequestsController {
	@Autowired
	private GpDockerDownloadRequestsService the_service;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/start_generation", headers = "Accept=application/json", produces = "text/plain")
	@ResponseBody
	public String request_download(@RequestParam("user_id") long user_id) throws Exception {
		return this.the_service.request_download(user_id);
	}
}
