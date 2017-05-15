package com.npb.gp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.domain.core.GpApplicationType;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.services.GpApplicationTypeService;
import com.npb.gp.services.GpProjectService;

/**
 * @author Dan Castillo</br>
 *         Creation Date: 11/08/2014</br>
 * @since .75
 *        </p>
 * 
 *        The purpose of this class is to be the main interaction point with
 *        applications</br>
 *        requiring project information
 *        </p>
 *
 *        NOTE: this class is the latest incarnation of the project function in
 *        Geppetto</br>
 *        previous versions are listed below
 *        </p>
 * 
 *        <li>GpProjectManagerServiceController - Geppetto version .35 - Create
 *        Date: 09/16/2012</li>
 *        <li>GpProjectManagerController - Geppetto - version .2 - Create Date:
 *        12/10/2010</li>
 * 
 * 
 *        <b>Modified Date: 16/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Changed the return type to "update_project" method
 *        </p>
 * 
 *        <b>Modified Date: 07/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Wrote the method as "delete_project and update_project"
 *        </p>
 * 
 *        <b>Modified Date: 02/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Wrote the "create_project" to create a new project
 *        </p>
 * 
 * 
 */

@Controller("GpProjectController")
@RequestMapping("/project")
public class GpProjectController {

	private GpProjectService project_service;

	public GpProjectService getProject_service() {
		return project_service;
	}

	@Resource(name = "GpProjectService")
	public void setProject_service(GpProjectService project_service) {
		this.project_service = project_service;
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

	
	@RequestMapping(method = RequestMethod.GET, value = "/search_for_update/", headers = "Accept=application/json")
	@ResponseBody
	public GpProject search_for_update(@RequestParam("project_id") long project_id) throws Exception {

		System.out.println("GpProjectController - search_for_update - 1");
		GpProject the_project = this.project_service.search_for_project(project_id, null);

		return the_project;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_projects_for_user/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpProject> get_projects_for_user() throws Exception {
		System.out.println("GpProjectController - get_projects_for_user - 1");
		GpUser loggedInUser = GpUserUtils.getLoggedUser();
		ArrayList<GpProject> the_list = this.project_service.search_project_by_user_id(loggedInUser);
		return the_list;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create_project/", headers = "Accept=application/json")
	@ResponseBody
	public GpProject create_project(@RequestBody GpProject aProject, GpUser user) throws Exception {
		System.out.println("create project method calling!");

		return this.project_service.create_project(aProject, GpUserUtils.getLoggedUser());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_project/", headers = "Accept=application/json")
	@ResponseBody
	public GpProject update_project(@RequestBody GpProject gpproject, GpUser user) throws Exception {
		System.out.println("The update method has been called");
		System.out.println("The client ID is " + gpproject.getId());

		return this.project_service.update_project(gpproject, GpUserUtils.getLoggedUser());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete_project/", headers = "Accept=application/json")
	@ResponseBody
	public void delete_project(@RequestBody GpProject gpproject) throws Exception {
		System.out.println("The delete_project method has been called");

		this.project_service.delete_project(gpproject);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search_for_exist_project/", headers = "Accept=application/json")
	@ResponseBody
	public boolean search_for_exist_project(@RequestParam("project_name") String project_name) throws Exception {
		System.out.println("The search_for_exist_project method has been called");
		return this.project_service.search_for_exist_project(project_name);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/find_all_Application_type")
	@ResponseBody
	public List<GpApplicationType> find_all_Application_type() throws Exception {
		System.out.println("get_all_predefined_activities called!");
		return this.gpApplicationTypeService.find_all_Application_type();
	}

}