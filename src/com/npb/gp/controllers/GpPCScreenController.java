package com.npb.gp.controllers;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.services.GpPcScreenService;

/**
 * @author Dan Castillo</br>
 *         Creation Date: 11/10/2014
 * @since version .75
 *        </p>
 * 
 * 
 *        The purpose of this class is to be the main interaction point
 *        with</br>
 *        components that are used to maintain the meta data representing a</br>
 *        Geppetto PC Screen</br>
 * 
 *        NOTE: a version of this class has existed in Geppetto under various
 *        names</br>
 *        since version .2 of Geppetto, the immediate predecessor of this</br>
 *        class was created on 12/31/2013 and was known as
 *        "GpPcScreenDesignerController"
 *        </p>
 * 
 *        Modified Date: 13/04/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        <p>
 *        Modified the parameter and the return type to GpScreenX in all methods
 * 
 *        And added the new methods as create_screen, update_screen and
 *        delete_screen
 *        </p>
 * 
 */

@Controller("GpPCScreenController")
@RequestMapping("/pc")
public class GpPCScreenController {

	GpPcScreenService pc_service;

	public GpPcScreenService getPc_service() {
		return pc_service;
	}

	@Resource(name = "GpPcScreenService")
	public void setPc_service(GpPcScreenService pc_service) {
		this.pc_service = pc_service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search_for_update/", headers = "Accept=application/json")
	@ResponseBody
	public GpScreenX search_for_update(@RequestParam("screen_id") long screen_id) throws Exception {
		System.out.println("In GpPCScreenController - search_for_update");

		GpScreenX the_screen = this.pc_service.search_for_screen_by_screen_id(screen_id);

		return the_screen;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_screens_by_project_id/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpScreenX> get_all_screens_by_project_id(@RequestParam("project_id") long project_id)
			throws Exception {

		System.out.println("In GpPCScreenController - get_screens");

		ArrayList<GpScreenX> the_screens = this.pc_service.search_for_screens_by_project_id(project_id);

		return the_screens;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_screens_by_activity_id/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpScreenX> get_all_screens_by_activity_id(@RequestParam("activity_id") long activity_id)
			throws Exception {

		System.out.println("In GpAndroidController - get_screens_by_activity_id");

		ArrayList<GpScreenX> the_screens = this.pc_service.search_for_screens_by_activity_id(activity_id);

		return the_screens;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create_screen/", headers = "Accept=application/json")
	@ResponseBody
	public GpScreenX create_screen(@RequestBody GpScreenX screen) throws Exception {
		System.out.println("In create_screen -1 ");
		System.out.println("In create_screen -2 " + screen);
		System.out.println("screen.getName() is: " + screen.getName());
		System.out.println("screen.getType() is: " + screen.getType());

		GpScreenX ascreen = this.pc_service.create_screen(screen, GpUserUtils.getLoggedUser());

		return ascreen;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_screen/", headers = "Accept=application/json")
	@ResponseBody
	public GpScreenX update_screen(@RequestBody GpScreenX ascreen) throws Exception {
		System.out.println("Update screen called!");
		System.out.println("Screen ID: " + ascreen.getId());
		System.out.println("Screen Name: " + ascreen.getName());
		System.out.println("Screen Type: " + ascreen.getType());

		GpScreenX the_screen = this.pc_service.update_screen(ascreen, GpUserUtils.getLoggedUser());

		return the_screen;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete_screen/", headers = "Accept=application/json")
	@ResponseBody
	public void delete_screen(@RequestBody GpScreenX ascreen) throws Exception {
		System.out.println("Delete screen called!");
		System.out.println("Screen ID: " + ascreen.getId());
		System.out.println("Screen Name: " + ascreen.getName());
		System.out.println("Screen Name: " + ascreen.getType());

		this.pc_service.delete_screen(ascreen);
	}
}
