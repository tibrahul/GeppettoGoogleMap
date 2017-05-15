package com.npb.gp.controllers;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.services.GpTechPropertyService;

/**
 * 
 * @author Dan Castillo </br> Date Created: 11/08/2014</p>
 * 
 *         The purpose of this class is to search operations for tech
 *         properties</p>
 *
 *         <b>Modified Date: 14/04/2015<br>
 *         Modified By: Kumaresan Perumal<b><br>
 * 
 *         <p>
 *         Wrote the following methods create_techproperties,
 *         update_techproperties, delete_techproperties, and
 *         get_default_properties.
 *         </p>
 */
@Controller("GpTechPropertiesController")
@RequestMapping("/techproperties")
public class GpTechPropertiesController {

	private GpTechPropertyService prop_service;

	public GpTechPropertyService getProp_service() {
		return prop_service;
	}

	@Resource(name = "GpTechPropertyService")
	public void setProp_service(GpTechPropertyService prop_service) {
		this.prop_service = prop_service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_tech_properties/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpTechProperties> get_all_tech_properties()
			throws Exception {

		System.out
				.println("In GpTechPropertiesController - get_all_tech_properties");

		ArrayList<GpTechProperties> the_props = this.prop_service
				.get_all_tech_properties(null);

		return the_props;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_default_properties/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpTechProperties> get_default_properties(
			GpTechProperties aproperty, GpUser user) throws Exception {
		System.out.println("I just got controller");

		ArrayList<GpTechProperties> the_default_props = this.prop_service
				.get_default_properties(aproperty, user);
		return the_default_props;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create_tech_property/", headers = "Accept=application/json")
	@ResponseBody
	public GpTechProperties create_tech_property(
			@RequestBody GpTechProperties aproperty) throws Exception {
		System.out.println("We are in create controller");

		return this.prop_service.create_tech_property(aproperty);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_tech_property/", headers = "Accept=application/json")
	@ResponseBody
	public GpTechProperties update_tech_property(
			@RequestBody GpTechProperties aproperty) throws Exception {
		System.out.println("update has been reached");

		return this.prop_service.update_tech_property(aproperty);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete_tech_property/")
	@ResponseBody
	public void delete_tech_property(
			@RequestParam("property_id") long property_id) throws Exception {
		System.out.println("We have reached the delete method");
		System.out.println("property_id" + property_id);

		this.prop_service.delete_tech_property(property_id);
	}
}
