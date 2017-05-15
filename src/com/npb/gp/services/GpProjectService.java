package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.dao.mysql.GpActivityDao;
import com.npb.gp.dao.mysql.GpMicroServiceDao;
import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.dao.mysql.GpProjectDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroService;
import com.npb.gp.domain.core.GpModule;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.services.IGpProjectService;

/**
 * @author Dan Castillo</br>
 *         Creation Date: 04/14/2014</br>
 * @since .35
 *        </p>
 * 
 *        handles all things about projects
 *        </p>
 *        please not a version of this class has existed in Geppetto since
 *        version .1
 *        </p>
 * 
 * 
 *        <b>Modified Date: 16/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Changed the return type to "update_project" method
 *        </p>
 * 
 *        Modified Date: 16/04/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Modified the set_defaultmodule_id in create project method
 * 
 *        Modified Date: 15/04/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Changed the GpScreen to GpScreenX in all methods and declarations
 * 
 * 
 *        <b>Modified Date: 07/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        created delete_method and update_method
 *        </p>
 * 
 *        Modified Date: 10/22/2014</br>
 *        Modified By: Dan Castillo
 *        </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        <b>Modified Date: 02/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Modified the "create_project" to create a new project and changed the
 *        return type to return project values
 *        </p>
 * 
 * 
 * 
 */
@Service("GpProjectService")
public class GpProjectService extends GpBaseService implements IGpProjectService {

	GpProjectDao project_dao;
	GpActivityDao activity_dao;
	GpScreenXDao screen_dao;
	GpNounDao noun_dao;
	private GpActivityService activity_service;
	private GpFlowControlService flow_control_service;
	private GpVerbService verb_service;
	private GpMicroFlowService micro_flow_service;
	private GpMicroServiceDao gpMicroService;
	
	public GpMicroFlowService getMicro_flow_service() {
		return micro_flow_service;
	}
	
	@Resource(name = "GpMicroFlowService")
	public void setMicro_flow_service(GpMicroFlowService micro_flow_service) {
		this.micro_flow_service = micro_flow_service;
	}
	
	public GpVerbService getVerb_service() {
		return verb_service;
	}
	
	@Resource(name = "GpVerbService")
	public void setVerb_service(GpVerbService verb_service) {
		this.verb_service = verb_service;
	}
	
	public GpFlowControlService getFlow_control_service() {
		return flow_control_service;
	}
	
	@Resource(name = "GpFlowControlService")
	public void setFlow_control_service(GpFlowControlService flow_control_service) {
		this.flow_control_service = flow_control_service;
	}
	
	public GpActivityService getActivity_service() {
		return activity_service;
	}
	
	@Resource(name = "GpActivityService")
	public void setActivity_service(GpActivityService activity_service) {
		this.activity_service = activity_service;
	}

	public GpActivityDao getActivity_dao() {
		return activity_dao;
	}

	@Resource(name = "GpActivityDao")
	public void setActivity_dao(GpActivityDao activity_dao) {
		this.activity_dao = activity_dao;
	}

	public GpProjectDao getProject_dao() {
		return project_dao;
	}

	@Resource(name = "GpProjectDao")
	public void setProject_dao(GpProjectDao project_dao) {
		this.project_dao = project_dao;
	}

	public GpScreenXDao getScreen_dao() {
		return screen_dao;
	}

	@Resource(name = "GpScreenXDao")
	public void setScreen_dao(GpScreenXDao screen_dao) {
		this.screen_dao = screen_dao;
	}

	public GpNounDao getNoun_dao() {
		return noun_dao;
	}

	@Resource(name = "GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}
	
	public GpMicroServiceDao getGpMicroService() {
		return gpMicroService;
	}

	@Resource(name = "GpMicroServiceDao")
	public void setGpMicroService(GpMicroServiceDao gpMicroService) {
		this.gpMicroService = gpMicroService;
	}

	@Override
	public GpProject create_project(GpProject aproject, GpUser user) throws Exception {
		System.out.println("GpProjectService - create_project");

		GpModule aModule = new GpModule();
		//aModule.setId(this.get_widget_count());
		aModule.setId(0);
		if(aModule.getLabel() == "" || aModule.getLabel() == null) {
			aModule.setLabel("default module");
		}
		
		aproject.setDefault_module_id(aModule.getId());
		aproject.setDefault_module_label(aModule.getLabel());
		aproject.setWidget_count(aModule.getId());

		// aProject.setName("");
		// aProject.setDescription("");
		// aProject.setLabel("");
		// aProject.setDefault_module_id(99901);

		aproject.setDefault_module(aModule);
		aproject.setCreatedby(user.getId());
		aproject.setLastmodifiedby(user.getId());
		aproject.setLabel(aproject.getName());

		aproject.setGeneration_type("mixed"); // It's temporarily Hard coded(
												// Did By Suresh Palanisamy)
		aproject.setApplication_type(1);//value for monolithic application
		return this.project_dao.insert(aproject);
	}

	@Override
	public GpProject update_project(GpProject aproject, GpUser user) throws Exception {
		aproject.setLastmodifiedby(user.getId());

		aproject.setGeneration_type("mixed"); // It's temporarily Hard coded(Did
												// By Suresh Palanisamy)
		GpProject the_project = this.project_dao.update(aproject);
		
		//update all the flows and microflow
		List<GpActivity> activities = activity_service.get_all_activities_for_project(the_project.getId(), null);
		
		for(GpActivity activity : activities){
			this.flow_control_service.delete_flows_by_activity_id(activity.getId());
			this.flow_control_service.get_records_from_flowcontrol_base(activity.getProjectid(), activity.getId());
			List<GpVerb> the_verbs = this.verb_service.get_all_verbs_by_activity_id(activity.getId());
			for(GpVerb the_verb : the_verbs){
				this.micro_flow_service.insert_verb_method_implementation(activity.getId(), the_verb.getId(), the_verb);
			}
		}
		
		
		return the_project;
		
	}

	@Override
	public void delete_project(GpProject aproject) throws Exception {
		this.project_dao.delete(aproject);
	}

	@Override
	public GpProject search_for_update(long project_id, GpUser user) throws Exception {
		return null;
	}

	@Override
	public GpProject search_for_project(long project_id, GpUser user) throws Exception {

		GpProject the_project = new GpProject();
		try {

			the_project = this.project_dao.find_by_id(project_id);

			GpModule thedefaultmod = new GpModule();
			the_project.setDefault_module(thedefaultmod);

			thedefaultmod.setLabel("dat not nite huga!");
			thedefaultmod.setName("defaultmod");
			thedefaultmod.setId(12368127368261L);
			thedefaultmod.setDescription("Default Mod testing");
			thedefaultmod.setMulti_user_status("inuse");
			thedefaultmod.setMulti_user_info("Locked by:\t Grace Castillo\n" + "Contact number:\t 212-580-6680");

			ArrayList<GpActivity> activity_list;

			activity_list = (ArrayList<GpActivity>) this.activity_dao.find_all_base_by_projectid(project_id);

			ArrayList<GpNoun> noun_list = (ArrayList<GpNoun>) this.noun_dao.find_all_base_by_projectid(project_id);

			ArrayList<GpScreenX> screen_list = (ArrayList<GpScreenX>) this.screen_dao
					.find_all_base_by_projectid(project_id);
			
			List<GpMicroService> gpMicroService = this.gpMicroService.getMicroservicebyId(project_id);
			
			the_project.setGpMicroService(gpMicroService);

			// the_project.setProject_screens((
			// ArrayList<GpScreenBacking>) this.screen_dao
			// .find_all_base_by_projectid(project_id));
			this.merge_project_components(the_project, activity_list, noun_list, screen_list);

			thedefaultmod.setTheactivities(activity_list);

			return the_project;

		} catch (Exception e) {
			System.out.println("In the catch - e.getCause() is:  " + e.getCause());
			if (e.getCause().toString().contains("99")) {
				return the_project;
			}
		}
		return null;
	}

	private void merge_project_components(GpProject the_project, ArrayList<GpActivity> activity_list,
			ArrayList<GpNoun> noun_list, ArrayList<GpScreenX> screen_list) {

		for (GpActivity activity : activity_list) {
			ArrayList<GpNoun> sec_noun_list = activity.getSecondary_nouns();
			// activity.getPrimary_noun() -- OJO you should deal with the
			// primary noun as well
			for (GpNoun sec_noun : sec_noun_list) {
				for (GpNoun noun : noun_list) {
					if (noun.getId() == sec_noun.getId()) {
						sec_noun.setName(noun.getName());
						sec_noun.setDescription(noun.getDescription());
						sec_noun.setLabel(noun.getLabel());

					}
				}
			}
		}

		for (GpActivity activity : activity_list) {
			activity.setPc_screens(new ArrayList<GpScreenX>());
			activity.setTablet_screens(new ArrayList<GpScreenX>());
			activity.setPhone_screens(new ArrayList<GpScreenX>());
			for (GpScreenX screen : screen_list) {
				if (screen.getActivity_id() == activity.getId()) {
					if (screen.getType().equals(GpBaseConstants.GpDeviceType_Pc)) {
						activity.getPc_screens().add(screen);
					} else if (screen.getType().equals(GpBaseConstants.GpDeviceType_Tablet)) {
						activity.getTablet_screens().add(screen);
					} else if (screen.getType().equals(GpBaseConstants.GpDeviceType_Phone)) {
						activity.getPhone_screens().add(screen);
					}
				}
			}
		}
		the_project.setProject_nouns(noun_list);
		the_project.setProject_screens(screen_list);

	}

	@Override
	public ArrayList<GpProject> search_project_by_user_id(GpUser user) throws Exception {
		System.out.println("In GpProjectService - search_project_by_user_id -1 ");

		ArrayList<GpProject> the_list = this.project_dao.find_by_user_id(user.getId());

		return the_list;
	}

	@Override
	public long get_widget_count() throws Exception {
		long widget_count = this.screen_dao.get_widget_count();
		return widget_count + 1;
	}

	@Override
	public boolean search_for_exist_project(String project_name) throws Exception {
		return this.project_dao.search_for_exist_project(project_name);
	}
		
}