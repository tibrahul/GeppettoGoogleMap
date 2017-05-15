package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.dao.mysql.GpActivityDao;
import com.npb.gp.dao.mysql.GpButtonGroupDao;
import com.npb.gp.dao.mysql.GpProjectDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.dao.mysql.GpWizardDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpButtonGroup;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpWizard;
import com.npb.gp.interfaces.services.IGpActivityService;

/**
 * Creation Date: 02/06/2014
 * 
 * @since version .35
 *        </p>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with Activities
 *        </p>
 * 
 *        <b>Modified Date: 07/08/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Added menu builder codes in insert and delete methods
 *        </p>
 * 
 *        <b>Modified Date: 06/05/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Changed the parameter in get_module_default_id method
 *        </p>
 * 
 *        Modified Date: 15/04/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Changed the GpScreen to GpScreenX in all methods and declarations
 * 
 *        Modified Date: 10/22/2014</br>
 *        Modified By: Dan Castillo
 *        </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Added new method as delete to delete the activities
 *        </p>
 * 
 * 
 * 
 *        <b>Modified Date: 05/31/2016<br>
 *        Modified By: Kumaresan Perumal<b><br>
 *        <p>
 *       I wrote some code create_activity method to check up the validation. here we find activity name exist or not
 *        </p>
 */
@Service("GpActivityService")
public class GpActivityService extends GpBaseService implements IGpActivityService {
	private int i = 0;
	private GpActivityDao activity_dao;
	private GpWizardDao wizard_Dao;
	private GpScreenXDao screen_dao;
	
	private GpNounService noun_service;
	private GpFlowControlService flow_control_service;
	private GpProjectDao project_dao;
	private GpMenuBuilderService menu_builder_service;

	public GpActivityDao getActivity_dao() {
		return activity_dao;
	}

	@Resource(name = "GpActivityDao")
	public void setActivity_dao(GpActivityDao activity_dao) {
		this.activity_dao = activity_dao;
	}

	public GpScreenXDao getScreen_dao() {
		return screen_dao;
	}

	@Resource(name = "GpScreenXDao")
	public void setScreen_dao(GpScreenXDao screen_dao) {
		this.screen_dao = screen_dao;
	}

	
	public GpWizardDao getWizard_Dao() {
		return wizard_Dao;
	}

	@Resource(name = "GpWizardDao")
	public void setWizard_Dao(GpWizardDao wizard_Dao) {
		this.wizard_Dao = wizard_Dao;
	}
	public GpNounService getNoun_service() {
		return noun_service;
	}

	@Resource(name = "GpNounService")
	public void setNoun_service(GpNounService noun_service) {
		this.noun_service = noun_service;
	}

	public GpFlowControlService getFlow_control_service() {
		return flow_control_service;
	}

	@Resource(name = "GpFlowControlService")
	public void setFlow_control_service(GpFlowControlService flow_control_service) {
		this.flow_control_service = flow_control_service;
	}

	public GpProjectDao getProject_dao() {
		return project_dao;
	}

	@Resource(name = "GpProjectDao")
	public void setProject_dao(GpProjectDao project_dao) {
		this.project_dao = project_dao;
	}

	public GpMenuBuilderService getMenu_builder_service() {
		return menu_builder_service;
	}

	@Resource(name = "GpMenuBuilderService")
	public void setMenu_builder_service(GpMenuBuilderService menu_builder_service) {
		this.menu_builder_service = menu_builder_service;
	}

	@Override
	public GpActivity search_for_update(long activity_id) throws Exception {

		GpActivity the_activity = this.activity_dao.find_by_id(activity_id);
		return the_activity;
	}

	@Override
	public void update_activity(GpActivity activity, GpUser the_user) {

		if (activity == null) {
			GpActivity new_activity = new GpActivity();
			new_activity.setId(40L);
			new_activity.setName("da_name");
			new_activity.setLabel("da_label");
			new_activity.setDescription("da description - this is the update test " + i++);
			new_activity.setProjectid(9999L);
			new_activity.setModuleid(888888L);
			// new_activity.setPrimary_noun_id(2L);
			new_activity.setNotes(
					"<!--@ this is my test note for the crazynes this notes orginates from the create_activity method of the GpActivityService        -->");

			ArrayList<String> the_types = new ArrayList<String>();
			the_types.add("mobile");
			the_types.add("pc");
			new_activity.setActivity_types(the_types);

			ArrayList<GpNoun> secondary_nouns = new ArrayList<GpNoun>();
			GpNoun noun_one = new GpNoun();
			noun_one.setId(1L);
			secondary_nouns.add(noun_one);

			GpNoun noun_three = new GpNoun();
			noun_three.setId(3L);
			secondary_nouns.add(noun_three);

			new_activity.setSecondary_nouns(secondary_nouns);
			new_activity.setCreatedby(the_user.getId());
			new_activity.setLastmodifiedby(the_user.getId());

			this.activity_dao.update(new_activity);

		} else {
			this.activity_dao.update(activity);
		}

	}

	@Override
	public GpActivity create_activity(GpActivity activity, GpUser the_user) throws Exception {

		activity.setCreatedby(the_user.getId());
		activity.setLastmodifiedby(the_user.getId());
		// activity.setModuleid(activity.getProjectid()); // this is temporary
		// -Dan
		// 4/19/14

		if (activity.getModule_type() == null || activity.getModule_type().isEmpty() || !activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
			activity.setModuleid(this.get_module_default_id(activity.getProjectid()));
		}
		/** This code is used to check up the validation. here we find name exist or not*/
		ArrayList<GpActivity> the_acts = (ArrayList<GpActivity>) this.activity_dao
				.find_all_base_by_projectid(activity.getProjectid());
		for (GpActivity gpActivity : the_acts) {
			if(gpActivity.getName().equals(activity.getName())){
				return null;
			}
		}
		
		this.activity_dao.insert(activity);

		System.out.println("Generated key=" + activity.getId());
		this.flow_control_service.get_records_from_flowcontrol_base(activity.getProjectid(), activity.getId());

		// Code for insert a activity in menu builder
		GpMenuDetail menudetail = new GpMenuDetail();
		menudetail.setName(activity.getName());
		menudetail.setLabel(activity.getLabel());
		menudetail.setDescription(activity.getDescription());
		menudetail.setActivity_id(activity.getId());
		menudetail.setAuth_id(0); // assigned as a default value
		menudetail.setProject_id(activity.getProjectid());
		this.menu_builder_service.create_menudetail_activity(menudetail);

		return activity;

	}

	@Override
	public void delete(GpActivity activity) throws Exception {

		// Code for delete a activity in menu builder
		this.menu_builder_service.delete_menudetail_activity(activity);

		this.activity_dao.delete(activity.getId());
	}

	@Override
	public ArrayList<GpActivity> get_all_activities_for_project(long project_id, GpUser the_user) throws Exception {

		System.out.println("In GpActivityService - get_all_activities_for_project -1 ");
		ArrayList<GpActivity> the_acts = (ArrayList<GpActivity>) this.activity_dao
				.find_all_base_by_projectid(project_id);

		ArrayList<GpNoun> noun_list = this.noun_service.search_for_nouns_by_project_id(project_id, null);

		// set primary noun info for each activity
		for (GpActivity an_act : the_acts) {
			// an_act.getPrimary_noun().getId()
			for (GpNoun noun : noun_list) {
				if (noun.getId() == an_act.getPrimary_noun().getId()) {
					an_act.getPrimary_noun().setName(noun.getName());
					an_act.getPrimary_noun().setDescription(noun.getDescription());
					an_act.getPrimary_noun().setLabel(noun.getLabel());
				}

			}
		}
		// handle secondary nouns
		for (GpActivity an_act : the_acts) {
			for (GpNoun sec_noun : an_act.getSecondary_nouns()) {
				for (GpNoun noun : noun_list) {
					if (noun.getId() == sec_noun.getId()) {
						sec_noun.setName(noun.getName());
						sec_noun.setDescription(noun.getDescription());
						sec_noun.setLabel(noun.getLabel());
					}

				}
			}
		}

		// handle the screens
		for (GpActivity an_act : the_acts) {
			ArrayList<GpScreenX> the_screens = this.screen_dao.find_all_base_by_activity_id(an_act.getId());
			for (GpScreenX a_screen : the_screens) {
				if (a_screen.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Pc)) {
					if (an_act.getPc_screens() == null) {
						an_act.setPc_screens(new ArrayList<GpScreenX>());
					}
					an_act.getPc_screens().add(a_screen);
				} else if (a_screen.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Tablet)) {
					if (an_act.getTablet_screens() == null) {
						an_act.setTablet_screens(new ArrayList<GpScreenX>());
					}
					an_act.getTablet_screens().add(a_screen);
				} else if (a_screen.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Phone)) {
					if (an_act.getPhone_screens() == null) {
						an_act.setPhone_screens(new ArrayList<GpScreenX>());
					}
					an_act.getPhone_screens().add(a_screen);
				}
			}
		}
		return the_acts;
	}

	@Override
	public long get_module_default_id(long project_id) throws Exception {
		long default_module_id = this.project_dao.get_default_module_id(project_id);
		return default_module_id;
	}

	@Override
	public ArrayList<GpActivity> get_all_predefined_activities() throws Exception {
		
		return this.activity_dao.get_all_predefined_activities();
	}
	
	@Override
	public List<GpActivity> get_activities_by_name(String name) throws Exception {
		
		return this.activity_dao.get_activity_by_name(name);
	}

	@Override
	public GpWizard create_wizard(GpWizard gpWizard) throws Exception {

		gpWizard = this.wizard_Dao.insert(gpWizard);

		System.out.println("Generated key=" + gpWizard.getId());
		
		return gpWizard;

	}
	
	@Override
	public List<GpWizard> get_wizards_by_id(long activity_id,String wizard_type) throws Exception {
		
		List<GpWizard> wizardList = this.wizard_Dao.find_all_by_activityid(activity_id,wizard_type);

		return wizardList;
	}
	
	@Override
	public void update_wizard(long activityid,long wizardid,long screenid,String wizard_type) throws Exception {
		this.wizard_Dao.update(activityid,wizardid,screenid,wizard_type);
	}
	
	@Override
	public void deleteWizard_screen(GpScreenX ascreen) throws Exception {
		this.wizard_Dao.deleteWizard_screen(ascreen);
	}
	
	@Override
	public void updateWizard_screen(GpScreenX ascreen) throws Exception {
		this.wizard_Dao.updateWizard_screen(ascreen);
	}
	
	@Override
	public void addWizard_screen(GpScreenX ascreen) throws Exception {
		this.wizard_Dao.addWizard_screen(ascreen);
	}
	
	public void deleteByNounId(long activityId) throws Exception {
		this.activity_dao.deleteByNounId(activityId);
	}

	public GpActivity create_wsdl_activity(GpActivity activity, GpUser the_user) throws Exception {

		activity.setCreatedby(the_user.getId());
		activity.setLastmodifiedby(the_user.getId());
		// activity.setModuleid(activity.getProjectid()); // this is temporary

		if (activity.getModule_type() == null || activity.getModule_type().isEmpty() || !activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
			activity.setModuleid(this.get_module_default_id(activity.getProjectid()));
		}
		/** This code is used to check up the validation. here we find name exist or not*/ 
		ArrayList<GpActivity> the_acts = (ArrayList<GpActivity>) this.activity_dao
				.find_all_base_by_projectid(activity.getProjectid());
		for (GpActivity gpActivity : the_acts) {
			if(gpActivity.getName().equals(activity.getName())){
				return null;
			}
		}
		
		this.activity_dao.insert(activity);

		System.out.println("Generated key=" + activity.getId());
		this.flow_control_service.get_records_from_flowcontrol_base(activity.getProjectid(), activity.getId());

		// Code for insert a activity in menu builder
		GpMenuDetail menudetail = new GpMenuDetail();
		menudetail.setName(activity.getName());
		menudetail.setLabel(activity.getLabel());
		menudetail.setDescription(activity.getDescription());
		menudetail.setActivity_id(activity.getId());
		menudetail.setAuth_id(0); // assigned as a default value
		menudetail.setProject_id(activity.getProjectid());
		this.menu_builder_service.create_menudetail_activity(menudetail);

		return activity;

	}

}
