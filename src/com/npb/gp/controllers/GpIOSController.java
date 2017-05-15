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
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.services.GpIosScreenService;

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
 *        since version .35 of Geppetto, the immediate predecessor of this</br>
 *        class was created on 01/28/2014 and was known as
 *        "GpIosScreenDesignerController"
 *        </p>
 * 
 *        Modified Date: 07/04/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        <p>
 *        Modified the retrun type to GpScreenX the
 *        get_all_screens_by_project_id method
 *        </p>
 * 
 *        Modified Date: 19/03/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Modified the create screen method to insert the screen and its
 *        children's values
 * 
 *        Modified Date: 26/03/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Wrote the new method as update_screen, delete screen for update and
 *        delete the screens
 * 
 *        Modified Date: 31/03/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Wrote the new method as get_all_base_verbs for retrieve the all base
 *        verbs from the database
 * 
 */

@Controller("GpIOSController")
@RequestMapping("/ios")
public class GpIOSController {

	private GpIosScreenService ios_service;

	public GpIosScreenService getIos_service() {
		return ios_service;
	}

	@Resource(name = "GpIosScreenService")
	public void setIos_service(GpIosScreenService ios_service) {
		this.ios_service = ios_service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search_for_update/", headers = "Accept=application/json")
	@ResponseBody
	public GpScreenX search_for_update(@RequestParam("screen_id") long screen_id) throws Exception {
		System.out.println("In GpIOSController - search_for_update");

		GpScreenX the_screen = this.ios_service.search_for_screen_by_screen_id(screen_id);

		return the_screen;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_screens_by_project_id/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpScreenX> get_all_screens_by_project_id(@RequestParam("project_id") long project_id)
			throws Exception {

		System.out.println("In GpIOSController - get_all_screens_by_project_id");

		ArrayList<GpScreenX> the_screens = this.ios_service.search_for_screens_by_project_id(project_id);

		return the_screens;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_screens_by_activity_id/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpScreenX> get_all_screens_by_activity_id(@RequestParam("activity_id") long activity_id)
			throws Exception {

		System.out.println("In GpAndroidController - get_screens_by_activity_id");

		ArrayList<GpScreenX> the_screens = this.ios_service.search_for_screens_by_activity_id(activity_id);

		return the_screens;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create_screen/", headers = "Accept=application/json")
	@ResponseBody
	public GpScreenX create_screen(@RequestBody GpScreenX screen) throws Exception {
		System.out.println("In create_screen -1 ");
		System.out.println("In create_screen -2 " + screen);
		System.out.println("screen.getName() is: " + screen.getName());
		System.out.println("screen.getType() is: " + screen.getType());

		GpScreenX ascreen = this.ios_service.create_screen(screen, GpUserUtils.getLoggedUser());

		return ascreen;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_screen/", headers = "Accept=application/json")
	@ResponseBody
	public GpScreenX update_screen(@RequestBody GpScreenX ascreen) throws Exception {
		System.out.println("Update screen called!");
		System.out.println("Screen ID: " + ascreen.getId());
		System.out.println("Screen Name: " + ascreen.getName());
		System.out.println("Screen Type: " + ascreen.getType());

		GpScreenX the_screen = this.ios_service.update_screen(ascreen, GpUserUtils.getLoggedUser());

		return the_screen;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete_screen/", headers = "Accept=application/json")
	@ResponseBody
	public void delete_screen(@RequestBody GpScreenX ascreen) throws Exception {
		System.out.println("Delete screen called!");
		System.out.println("Screen ID: " + ascreen.getId());
		System.out.println("Screen Name: " + ascreen.getName());
		System.out.println("Screen Name: " + ascreen.getType());

		this.ios_service.delete_screen(ascreen);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_base_verbs/")
	@ResponseBody
	public ArrayList<GpVerb> get_all_verbs() throws Exception {
		return (ArrayList<GpVerb>) this.ios_service.get_all_base_verbs();
	}
}
