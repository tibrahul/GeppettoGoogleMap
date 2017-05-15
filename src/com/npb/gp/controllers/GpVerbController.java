package com.npb.gp.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.services.GpVerbService;

/**
 * 
 * @author Suresh Palanisamy</br>
 *         Date Created: 16/10/2015</br>
 * @since 1.0
 *        </p>
 * 
 * 
 */

@Controller("GpVerbController")
@RequestMapping("/verb/")
public class GpVerbController {

	private GpVerbService verbService;

	public GpVerbService getVerbService() {
		return verbService;
	}

	@Resource(name = "GpVerbService")
	public void setVerbService(GpVerbService verbService) {
		this.verbService = verbService;
	}
	
	@RequestMapping(value = "/get_all_verbs_by_activity/", method = RequestMethod.GET)
	@ResponseBody
	public List<GpVerb> get_all_verbs_by_activity_id(@RequestParam("activity_id") long activity_id) throws Exception {
		System.out.println("Get all verbs called by activity!");
		return this.verbService.get_all_verbs_by_activity_id(activity_id);
	}

	@RequestMapping(value = "/get_all_base_verbs/", method = RequestMethod.GET)
	@ResponseBody
	public List<GpVerb> get_all_base_verbs() throws Exception {
		System.out.println("Get all base verbs called!");
		return this.verbService.get_all_base_verbs();
	}
	
	@RequestMapping(value = "/get_all_verbs_by_base_verb_id/{activity_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<GpVerb> get_all_verbs_by_base_verb_id(@PathVariable(value="activity_id") long activity_id) throws Exception {
		return this.verbService.get_all_verbs_by_base_verb_id(0,activity_id);
	}
}