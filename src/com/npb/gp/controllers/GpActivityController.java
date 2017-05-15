package com.npb.gp.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.dao.mysql.GpMicroServiceDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpApplicationType;
import com.npb.gp.domain.core.GpMicroService;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpWizard;
import com.npb.gp.services.GpActivityService;
import com.npb.gp.services.GpApplicationTypeService;
import com.npb.gp.services.GpLanguageService;
import com.npb.gp.services.GpMicroActivityService;

/**
 * 
 * @author Dan Castillo</br> Creation Date: 11/09/2014
 * @since version .75</p>
 * 
 * 
 *        The purpose of this class is to be the main interaction point
 *        with</br> components that are used to maintain a logical grouping
 *        called an activity</br> The class is used as the entry point into the
 *        services available on the server</p>
 * 
 *        NOTE: a version of this class has existed in Geppetto under various
 *        names</br> since version .2 of Geppetto, back then the function served
 *        by Activities was called</br> an Operation and the class was the
 *        GpOperation, the immediate predecessor of this</br> class as created
 *        on 02/11/2014 and was known as "GpActivityManagerController"</p>
 * 
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 *        <p>
 *        Wrote the "create_activity" to create the new activities, and the
 *        "update_activity" uses to update the actvities, and the
 *        "delete_activity" uses to delete the activities
 *        </p>
 * 		<b> Modified Date : 15/09/2015 <br>
 * 			Modified By Rashmi <b><br>
 * 		
 * 		<p> 1.Modified search_for_update method , added data in human_language_name field 
 * 			2. Added GpLanguageService obj, to fetch language name based on language id
 * 		</p>
 */

@Controller("GpActivityController")
@RequestMapping("/activity/")
public class GpActivityController {

	GpActivityService activity_service;

	public GpActivityService getActivity_service() {
		return activity_service;
	}

	@Resource(name = "GpActivityService")
	public void setActivity_service(GpActivityService activity_service) {
		this.activity_service = activity_service;
	}

	GpLanguageService gplanguageService;
	
	public GpLanguageService getGplanguageService() {
		return gplanguageService;
	}

	@Resource(name = "GpLanguageService")
	public void setGplanguageService(GpLanguageService gplanguageService) {
		this.gplanguageService = gplanguageService;
	}

	GpApplicationTypeService gpApplicationTypeService;
	
	public GpApplicationTypeService getGpApplicationTypeService() {
		return gpApplicationTypeService;
	}

	@Resource(name = "GpApplicationTypeService")
	public void setGpApplicationTypeService(
			GpApplicationTypeService gpApplicationTypeService) {
		this.gpApplicationTypeService = gpApplicationTypeService;
	}

	GpMicroActivityService gpMicroActivityService;
	
	public GpMicroActivityService getGpMicroActivityService() {
		return gpMicroActivityService;
	}
	
	@Resource(name = "GpMicroActivityService")
	public void setGpMicroActivityService(
			GpMicroActivityService gpMicroActivityService) {
		this.gpMicroActivityService = gpMicroActivityService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search_for_update/", headers = "Accept=application/json")
	@ResponseBody
	public GpActivity search_for_update(
			@RequestParam("activity_id") long activity_id) throws Exception {

		System.out.println("GpActivityController - search_for_update - 1");
		GpActivity the_activity = this.activity_service
				.search_for_update(activity_id);
		
		//convert human_language_id to ref_name
		ArrayList<GpScreenX> tablet_screens = the_activity.getTablet_screens();
		ArrayList<GpScreenX> phone_screens = the_activity.getPhone_screens();
		ArrayList<GpScreenX> pc_screens = the_activity.getPc_screens();

		
		// replace language name for language id
		String name ="";
		//pc_screens
		for (Iterator iterator = pc_screens.iterator(); iterator.hasNext();) {
			GpScreenX pcScreenX = (GpScreenX) iterator.next();
			if(pcScreenX.getHuman_language_id()!= 0){
				if(gplanguageService.get_language_by_id(pcScreenX.getHuman_language_id())!=null){
					name = gplanguageService.get_language_by_id(pcScreenX.getHuman_language_id()).getRef_name();
					pcScreenX.setHuman_language_name(name);
				}
			}
			
		}
		
		//tablet_screens
		for (Iterator iterator = tablet_screens.iterator(); iterator.hasNext();) {
			GpScreenX tabScreenX = (GpScreenX) iterator.next();
			if(tabScreenX.getHuman_language_id()!= 0){
				if(gplanguageService.get_language_by_id(tabScreenX.getHuman_language_id())!=null){
					name = gplanguageService.get_language_by_id(tabScreenX.getHuman_language_id()).getRef_name();
					tabScreenX.setHuman_language_name(name);
				}
			}
			
		}

		//phone_screens
		for (Iterator iterator = phone_screens.iterator(); iterator.hasNext();) {
			GpScreenX phScreenX = (GpScreenX) iterator.next();
			if(phScreenX.getHuman_language_id()!= 0){
				if(gplanguageService.get_language_by_id(phScreenX.getHuman_language_id())!=null){
					name = gplanguageService.get_language_by_id(phScreenX.getHuman_language_id()).getRef_name();
					phScreenX.setHuman_language_name(name);
				}
			}
					
		}
		return the_activity;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_activities_by_project_id/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpActivity> get_all_activities_by_project_id(
			@RequestParam("project_id") long project_id) throws Exception {

		System.out
				.println("GpActivityController - get_all_activities_by_project_id - 1");
		ArrayList<GpActivity> the_acts = this.activity_service
				.get_all_activities_for_project(project_id, null);

		return the_acts;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/create_activity/", headers = "Accept=application/json")
	@ResponseBody
	public GpActivity create_activity(@RequestBody GpActivity anActivity)
			throws Exception {
		System.out.println("Activity calling!");
		System.out.println(anActivity);
		System.out.println("anActivity.getId()" + anActivity.getId());
		System.out.println("anActivity.getName()" + anActivity.getName());
		System.out.println("User ID: " + GpUserUtils.getLoggedUser().getId());

		return this.activity_service.create_activity(anActivity,
				GpUserUtils.getLoggedUser());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete_activity/", headers = "Accept=application/json")
	@ResponseBody
	public void delete_activity(@RequestBody GpActivity anActivity)
			throws Exception {
		System.out.println("Delete activity is called!");
		System.out.println("anActivity.getId()" + anActivity.getId());
		this.activity_service.delete(anActivity);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_activity", headers = "Accept=application/json")
	@ResponseBody
	public void update_activity(@RequestBody GpActivity activity)
			throws Exception {
		System.out.println("Update is called ");
		System.out.println("" + activity.getId());
		this.activity_service.update_activity(activity,
				GpUserUtils.getLoggedUser());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get_all_predefined_activities")
	@ResponseBody
	public ArrayList<GpActivity> get_all_predefined_activities() throws Exception {
		System.out.println("get_all_predefined_activities called!");
		return this.activity_service.get_all_predefined_activities();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/create_wizard/", headers = "Accept=application/json")
	@ResponseBody
	public GpWizard create_wizard(@RequestBody GpWizard gpWizard)
			throws Exception {
		System.out.println("gpWizard" + gpWizard.getName());

		return this.activity_service.create_wizard(gpWizard);
				
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/create_microservice_wizard/", headers = "Accept=application/json")
	@ResponseBody
	public GpMicroService create_microservice_wizard(@RequestBody GpMicroService gpWizard)
			throws Exception {
		System.out.println("gpWizard" + gpWizard.getMicroservice_name());

		return this.gpMicroActivityService.create_microservice_wizard(gpWizard);
				
	}


	/*@RequestMapping(method = RequestMethod.GET, value = "/get_wizards/{activity_id}", headers = "Accept=application/json")
	@ResponseBody

	public List<GpWizard> get_wizards_by_id(@PathVariable(value="activity_id") long activity_id) throws Exception {
		System.out.println("activity id= "+activity_id);
	    List<GpWizard> wizards=this.activity_service.get_wizards_by_id(activity_id);
	    return wizards;
	}*/
	
	@RequestMapping(method = RequestMethod.POST, value = "/deleteWizard_screen/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteWizard_screen(@RequestBody GpScreenX ascreen) throws Exception {
		this.activity_service.deleteWizard_screen(ascreen);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateWizard_screen/", headers = "Accept=application/json")
	@ResponseBody
	public void updateWizard_screen(@RequestBody GpScreenX ascreen) throws Exception {
		this.activity_service.updateWizard_screen(ascreen);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addWizard_screen/", headers = "Accept=application/json")
	@ResponseBody
	public void addWizard_screen(@RequestBody GpScreenX ascreen) throws Exception {
		this.activity_service.addWizard_screen(ascreen);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/search_for_update_microservice/", headers = "Accept=application/json")
	@ResponseBody
	public void updateWizard_screen(@RequestBody GpMicroService wizarddata) throws Exception {
		 this.gpMicroActivityService.updateWizard_screen(wizarddata);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getData_for_update_microservice/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpMicroService> getDataForUpdateMicroservice(@RequestParam("serviceId") Long serviceId) throws Exception {
		return this.gpMicroActivityService.getMicroserviceByServiceId(serviceId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getData_for_microservice/", headers = "Accept=application/json")
	@ResponseBody
	public GpMicroService getDataForMicroservice(@RequestParam("serviceId") Long serviceId) throws Exception {
		return this.gpMicroActivityService.getDataForMicroservice(serviceId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/delete_Wizard_From_Activity/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteWizardFromActivity(@RequestBody GpMicroService wizarddata) throws Exception {
		 this.gpMicroActivityService.deleteWizardFromActivity(wizarddata);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get_all_micro_service/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpMicroService> get_all_micro_service(@RequestParam("projectId")Long projectId)
			throws Exception {
		 List<GpMicroService> microServiceList=this.gpMicroActivityService.get_all_micro_service(projectId);
		 return microServiceList;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update_microservice_wizard/", headers = "Accept=application/json")
	@ResponseBody
	public void updateMicroserviceWizard(@RequestBody GpMicroService wizarddata) throws Exception {
		 this.gpMicroActivityService.updateMicroserviceWizard(wizarddata);
	}

}