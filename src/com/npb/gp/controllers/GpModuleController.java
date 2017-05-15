package com.npb.gp.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.domain.core.GpModule;
import com.npb.gp.services.GpModuleService;
/**
 * 
 * @author Reinaldo</br>
 *         Date Created: 20/09/2015</br>
 *         
 * <p>
 * Controller created to manage the insertion of modules
 * </p>
 *
 */
@Controller("GpModuleController")
@RequestMapping("/module")
public class GpModuleController {
	
	
	GpModuleService module_service;
				
	public GpModuleService getModule_service() {
		return module_service;
	}

	@Resource(name = "GpModuleService")
	public void setModule_service(GpModuleService module_service) {
		this.module_service = module_service;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add_module/", headers = "Accept=application/json")
	@ResponseBody
	public GpModule insert_module(@RequestBody GpModule aModule) throws Exception {
		System.out.println("insert module method calling! "+aModule.getName());

		return this.module_service.insert_module(aModule, GpUserUtils.getLoggedUser());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get_gcd_file/{predefined_activity_id}", headers = "Accept=application/json")
	@ResponseBody
	public String get_gcd_json(@PathVariable(value="predefined_activity_id") long predefined_activity_id) throws Exception {

		return this.module_service.get_gcd_json(predefined_activity_id);
	}

}
