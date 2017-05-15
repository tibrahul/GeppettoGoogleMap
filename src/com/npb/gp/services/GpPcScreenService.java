package com.npb.gp.services;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npb.gp.dao.mysql.GpButtonGroupDao;
import com.npb.gp.dao.mysql.GpMenuBuilderDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.dao.mysql.support.screen.GpScreenWidgetParser;
import com.npb.gp.dao.mysql.support.screen.UpdateWidgetParser;
import com.npb.gp.domain.core.GpButtonGroup;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.domain.core.GpWizard;
import com.npb.gp.interfaces.services.IGpPcScreenService;

/**
 * Creation Date: 03/14/2014
 * 
 * @since version .35 </p>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with Screens that use a desktop OS AKA windows
 *        </p>
 * 
 *        Modified Date: 10/06/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        <p>
 *        Modified the update_screen method to delete the widgets while updating
 *        screen and widgets
 *        </p>
 * 
 *        Modified Date: 12/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        <p>
 *        Changed the create and update screen services to new concept
 *        </p>
 * 
 *        Modified Date: 13/04/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        <p>
 *        Modified the parameter and the return type to GpScreenX in all methods
 * 
 *        And added the new method as delete_a_widget
 *        </p>
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex **
 *
 *        Modified Date: 18/10/2015</br> Modified By: Reinaldo Lopez</p>
 * 
 *        added logic to update and delete verbs and microflow for widgets in
 *        the screen
 */
@Service("GpPcScreenService")
public class GpPcScreenService extends GpBaseService implements
		IGpPcScreenService {

	private GpScreenXDao screenx_dao;
	private GpVerbsDao verb_dao;
	private GpMenuBuilderDao gpMenuBuilderDao;
	private GpScreenWidgetParser screen_widget_parser;
	private UpdateWidgetParser update_widget_parser;
	private GpMicroFlowService micro_flow_service;
	private GpActivityService gpActivityService;
	private GpButtonGroupDao buttonGroupDao;

	public GpActivityService getGpActivityService() {
		return gpActivityService;
	}

	@Resource(name = "GpActivityService")
	public void setGpActivityService(GpActivityService gpActivityService) {
		this.gpActivityService = gpActivityService;
	}


	public GpMicroFlowService getMicro_flow_service() {
		return micro_flow_service;
	}

	@Resource(name = "GpMicroFlowService")
	public void setMicro_flow_service(GpMicroFlowService micro_flow_service) {
		this.micro_flow_service = micro_flow_service;
	}

	public GpScreenXDao getScreenx_dao() {
		return screenx_dao;
	}

	@Resource(name = "GpScreenXDao")
	public void setScreenx_dao(GpScreenXDao screenx_dao) {
		this.screenx_dao = screenx_dao;
	}

	public GpVerbsDao getVerb_dao() {
		return verb_dao;
	}

	@Resource(name = "GpVerbsDao")
	public void setVerb_dao(GpVerbsDao verb_dao) {
		this.verb_dao = verb_dao;
	}

	
	public GpMenuBuilderDao getGpMenuBuilderDao() {
		return gpMenuBuilderDao;
	}

	@Resource(name = "GpMenuBuilderDao")
	public void setGpMenuBuilderDao(GpMenuBuilderDao gpMenuBuilderDao) {
		this.gpMenuBuilderDao = gpMenuBuilderDao;
	}
	
	public GpButtonGroupDao getButtonGroupDao() {
		return buttonGroupDao;
	}
	
	@Resource(name = "GpButtonGroupDao")
	public void setButtonGroupDao(GpButtonGroupDao buttonGroupDao) {
		this.buttonGroupDao = buttonGroupDao;
	}


	public GpScreenWidgetParser getScreen_widget_parser() {
		return screen_widget_parser;
	}

	@Resource(name = "GpScreenWidgetParser")
	public void setScreen_widget_parser(
			GpScreenWidgetParser screen_widget_parser) {
		this.screen_widget_parser = screen_widget_parser;
	}

	public UpdateWidgetParser getUpdate_widget_parser() {
		return update_widget_parser;
	}

	@Resource(name = "UpdateWidgetParser")
	public void setUpdate_widget_parser(UpdateWidgetParser update_widget_parser) {
		this.update_widget_parser = update_widget_parser;
	}

	@Override
	public GpScreenX create_screen(GpScreenX ascreen, GpUser user)
			throws Exception {

		ascreen.setCreatedby(user.getId());
		ascreen.setLastmodifiedby(user.getId());

		GpScreenX newscreen = this.screenx_dao.insert(ascreen);

		//before inserting widgets add groups created if any
		if(ascreen.getCheckboxGroupList()!=null && ascreen.getCheckboxGroupList().size()>0){
			//insert groups
			for (Iterator iterator = ascreen.getCheckboxGroupList().iterator(); iterator.hasNext();) {
				GpButtonGroup gpButtonGroup = (GpButtonGroup) iterator.next();
				gpButtonGroup.setScreenId(newscreen.getId());
				gpButtonGroup.setName(gpButtonGroup.getName());
				this.buttonGroupDao.insert(gpButtonGroup);
			}
		}
		if(ascreen.getRadioGroupList()!=null && ascreen.getRadioGroupList().size()>0){
			//insert groups
			for (Iterator iterator = ascreen.getRadioGroupList().iterator(); iterator.hasNext();) {
				GpButtonGroup gpButtonGroup = (GpButtonGroup) iterator.next();
				gpButtonGroup.setScreenId(newscreen.getId());
				gpButtonGroup.setName(gpButtonGroup.getName());
				this.buttonGroupDao.insert(gpButtonGroup);
			}
		}
		
		String custom_object = null;
		String wsdl_operation_id1 = null;
		System.err.println("dkdkldlkllllllllllllll--------------------------------------->"+ascreen.toString());
		ascreen.setLastmodifiedby(user.getId());
		GpScreenX the_screen = this.search_for_screen_by_screen_id(ascreen.getId());
		ascreen.setScreen_wizard_sequence_id(the_screen.getScreen_wizard_sequence_id());
		ascreen.setWizard_id(the_screen.getWizard_id());
		this.screenx_dao.update(ascreen);


		// saving wsdl_operation_id in VERBS table
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(ascreen.getChildren().values());
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(json);

		JSONArray custom_verb = (JSONArray) obj;

		System.out.println("--custom_verb---custom_verb>"+custom_verb.toString().toString());
		for(int i= 0; i<custom_verb.size(); i++) {
			JSONObject jObject = (JSONObject) custom_verb.get(i);
			custom_object = (String) jObject.get("custom_verb_info");	
			if(custom_object!=null) {
				wsdl_operation_id1 = custom_object.substring(19, 21);
				if(StringUtils.isNumeric(wsdl_operation_id1)) {
				} else {
					wsdl_operation_id1 = wsdl_operation_id1.substring(0,1);
				}
			}
		}

		List<GpVerb> wsdl_verb = this.verb_dao.get_verbs_by_base_verb_id(8, ascreen.getActivity_id());
		
		for(GpVerb gpVerb_wsdl : wsdl_verb) {
			String wsdlID = null;
			String newWsdlID = null;
			String newWsdlID1 = null;
			wsdlID = gpVerb_wsdl.getWsdl_operation_id();
			newWsdlID = wsdlID+","+wsdl_operation_id1;
			if (StringUtils.isNotBlank(newWsdlID)) {
				newWsdlID1 = "";
				String[] arr = newWsdlID.split(",");
				for (String arrElm : arr) {
					if (StringUtils.isNotBlank(arrElm.trim())
							&& !"null".equalsIgnoreCase(arrElm.trim())
							&& !"0".equalsIgnoreCase(arrElm.trim())) {
						if (StringUtils.isNotBlank(newWsdlID1)) {
							newWsdlID1 += ",";
						}
						newWsdlID1 += arrElm.trim();
					}
				}
				newWsdlID1 = newWsdlID1.trim();
				if (StringUtils.isNotBlank(newWsdlID1)
						&& newWsdlID1.charAt(0) == ',') {
					newWsdlID1 = newWsdlID1.substring(1);
				}
				if (StringUtils.isNotBlank(newWsdlID1)
						&& newWsdlID1.charAt(newWsdlID1.length() - 1) == ',') {
					newWsdlID1 = newWsdlID1.substring(0,
							newWsdlID1.length() - 1);
				}
				gpVerb_wsdl.setWsdl_operation_id(newWsdlID1);
				this.verb_dao.update_wsdl_operation_id(gpVerb_wsdl);
			}
		}

		
		if (ascreen.getChildren().size() > 0) {
			this.screen_widget_parser.insert_widgetsfrom_screen(ascreen,
					screenx_dao, user);
		}
		
		if(ascreen.getWizard_id()>0){
			GpWizard gpWizard = new GpWizard();
			gpWizard.setActivity_id(ascreen.getActivity_id());
			
			gpActivityService.update_wizard(ascreen.getActivity_id(),ascreen.getWizard_id(),ascreen.getId(),ascreen.getClient_device_type());
		}
		return ascreen;
	}

	@Override
	public GpScreenX update_screen(GpScreenX ascreen, GpUser user)
			throws Exception {
		ascreen.setLastmodifiedby(user.getId());

		this.screenx_dao.update(ascreen);
		
		//update menu too
		/*JSONArray menuarray = new JSONArray();
		JSONObject j = new JSONObject();
		j.put("id",ascreen.getId());
		j.put("name",ascreen.getName());
		j.put("label",ascreen.getLabel());
		j.put("description",ascreen.getDescription());
		j.put("role",ascreen.getRole());
		j.put("auth_id",0);
		j.put("project_id",ascreen.getProjectid());
		j.put("activity_id",ascreen.getActivity_id());

		menuarray.put(j);
		gpMenuBuilderDao.update_menu_tree(ascreen.getActivity_id(),menuarray);*/

		// List<GpVerb> verbs_deleted = this.verb_dao
		// .get_verbs_by_screen_id(ascreen.getId());
		// for (GpVerb gpVerb : verbs_deleted) {
		// this.micro_flow_service.delete_verb_method_implementation(gpVerb
		// .getId());
		// }
		// this.verb_dao.delete_verbs(ascreen.getId());

		List<GpVerb> verbs_deleted = this.verb_dao
				.get_verbs_by_screen_id(ascreen.getId());
		if (verbs_deleted != null) {
			String screenId = null;
			String newScreenId = null;
			for (GpVerb gpVerb : verbs_deleted) {
				screenId = gpVerb.getScreen_id();
				if (StringUtils.isNotBlank(screenId)) {
					newScreenId = "";
					String[] arr = screenId.split(",");
					for (String arrElm : arr) {
						if (StringUtils.isNotBlank(arrElm.trim())
								&& !"null".equalsIgnoreCase(arrElm.trim())
								&& !"0".equalsIgnoreCase(arrElm.trim())
								&& ascreen.getId() != Integer.parseInt(arrElm
										.trim())) {
							if (StringUtils.isNotBlank(newScreenId)) {
								newScreenId += ",";
							}
							newScreenId += arrElm.trim();
						}
					}
					newScreenId = newScreenId.trim();
					if (StringUtils.isNotBlank(newScreenId)
							&& newScreenId.charAt(0) == ',') {
						newScreenId = newScreenId.substring(1);
					}
					if (StringUtils.isNotBlank(newScreenId)
							&& newScreenId.charAt(newScreenId.length() - 1) == ',') {
						newScreenId = newScreenId.substring(0,
								newScreenId.length() - 1);
					}
					gpVerb.setScreen_id(newScreenId);
					this.verb_dao.update_a_verb_screen(gpVerb);
				}
			}
		}

		//update button group
		
		//before inserting widgets add new checkbox groups created if any
		if(ascreen.getCheckboxGroupList()!=null && ascreen.getCheckboxGroupList().size()>0){
			ArrayList<Long> ids = new ArrayList<Long>();
			ArrayList<GpButtonGroup> existingbuttongrp = this.buttonGroupDao.getAllGroupsForScreenAndType(ascreen.getId(),"checkbox");
			for (Iterator iterator = existingbuttongrp.iterator(); iterator.hasNext();) {
				GpButtonGroup gpButtonGroup = (GpButtonGroup) iterator.next();
				ids.add(gpButtonGroup.getId());
			}
			//insert groups
				for (Iterator iterator = ascreen.getCheckboxGroupList().iterator(); iterator.hasNext();) {
					GpButtonGroup gpButtonGroup = (GpButtonGroup) iterator.next();
					if(!ids.contains(gpButtonGroup.getId())){
						System.out.println(gpButtonGroup.getId());
						gpButtonGroup.setName(gpButtonGroup.getName());
						this.buttonGroupDao.insert(gpButtonGroup);
					}
				}
		}
		if(ascreen.getRadioGroupList()!=null && ascreen.getRadioGroupList().size()>0){
			ArrayList<Long> ids = new ArrayList<Long>();
			ArrayList<GpButtonGroup> existingbuttongrp = this.buttonGroupDao.getAllGroupsForScreenAndType(ascreen.getId(),"radiobutton");
			for (Iterator iterator = existingbuttongrp.iterator(); iterator.hasNext();) {
				GpButtonGroup gpButtonGroup = (GpButtonGroup) iterator.next();
				ids.add(gpButtonGroup.getId());
			}
			//insert groups
				for (Iterator iterator = ascreen.getRadioGroupList().iterator(); iterator.hasNext();) {
					GpButtonGroup gpButtonGroup = (GpButtonGroup) iterator.next();
					if(!ids.contains(gpButtonGroup.getId())){
						System.out.println(gpButtonGroup.getId());
						gpButtonGroup.setName(gpButtonGroup.getName());
						this.buttonGroupDao.insert(gpButtonGroup);
					}
				}
		}
		
		if (ascreen.getChildren().size() > 0) {
			this.update_widget_parser.update_widgetsfrom_screen(ascreen,
					screenx_dao, user);
		}

		if (ascreen.getDeleted_widgets().size() > 0) {
			for (Long a_widget : ascreen.getDeleted_widgets()) {
				System.out.println("############# Deleted Widget: " + a_widget
						+ " #############");
				this.screenx_dao.delete_a_widget(a_widget, ascreen.getId());
			}
		}

		if(ascreen.getWizard_id()>0){
			GpWizard gpWizard = new GpWizard();
			gpWizard.setActivity_id(ascreen.getActivity_id());
			
			gpActivityService.update_wizard(ascreen.getActivity_id(),ascreen.getWizard_id(),ascreen.getId(),ascreen.getClient_device_type());
		}
		return ascreen;
	}

	@Override
	public void delete_screen(GpScreenX ascreen) throws Exception {

		Map<String, GpUiWidgetX> widgets_for_screen = this.screenx_dao
				.find_all_widgets_by_screen(ascreen.getId());

		// List<GpVerb> verbs_deleted =
		// this.verb_dao.get_verbs_by_screen_id(ascreen.getId());
		// for (GpVerb gpVerb : verbs_deleted) {
		// this.micro_flow_service.delete_verb_method_implementation(gpVerb.getId());
		// }
		// this.verb_dao.delete_verbs(ascreen.getId());

		List<GpVerb> verbs_deleted = this.verb_dao
				.get_verbs_by_screen_id(ascreen.getId());
		if (verbs_deleted != null) {
			String screenId = null;
			String newScreenId = null;
			for (GpVerb gpVerb : verbs_deleted) {
				screenId = gpVerb.getScreen_id();
				if (StringUtils.isNotBlank(screenId)) {
					newScreenId = "";
					String[] arr = screenId.split(",");
					for (String arrElm : arr) {
						if (StringUtils.isNotBlank(arrElm.trim())
								&& !"null".equalsIgnoreCase(arrElm.trim())
								&& !"0".equalsIgnoreCase(arrElm.trim())
								&& ascreen.getId() != Integer.parseInt(arrElm
										.trim())) {
							if (StringUtils.isNotBlank(newScreenId)) {
								newScreenId += ",";
							}
							newScreenId += arrElm.trim();
						}
					}
					newScreenId = newScreenId.trim();
					if (StringUtils.isNotBlank(newScreenId)
							&& newScreenId.charAt(0) == ',') {
						newScreenId = newScreenId.substring(1);
					}
					if (StringUtils.isNotBlank(newScreenId)
							&& newScreenId.charAt(newScreenId.length() - 1) == ',') {
						newScreenId = newScreenId.substring(0,
								newScreenId.length() - 1);
					}
					gpVerb.setScreen_id(newScreenId);
					this.verb_dao.update_a_verb_screen(gpVerb);
				}
			}
		}

		if (widgets_for_screen != null && widgets_for_screen.size() > 0) {

			System.out.println("Child Size: " + widgets_for_screen.size());

			for (Entry<String, GpUiWidgetX> entry : widgets_for_screen
					.entrySet()) {
				this.screenx_dao.delete_a_widget(entry.getValue().getId(),
						ascreen.getId());
			}
		}
		//delete button groups associated with screen
		this.buttonGroupDao.delete(ascreen.getId());
		
		this.screenx_dao.delete(ascreen.getId());
	}

	@Override
	public GpScreenX search_for_screen_by_screen_id(long screen_id)
			throws Exception {
		GpScreenX the_screen = this.screenx_dao.find_by_id(screen_id);
		ArrayList<GpButtonGroup> checkboxGroupList = (ArrayList<GpButtonGroup>) buttonGroupDao.getAllGroupsForScreenAndType(screen_id,"checkbox");
		the_screen.setCheckboxGroupList(checkboxGroupList);
		
		ArrayList<GpButtonGroup> radioGroupList = (ArrayList<GpButtonGroup>) buttonGroupDao.getAllGroupsForScreenAndType(screen_id,"radiobutton");
		the_screen.setRadioGroupList(radioGroupList);
		return the_screen;
	}

	@Override
	public ArrayList<GpScreenX> search_for_screens_by_project_id(long project_id)
			throws Exception {

		ArrayList<GpScreenX> screen_list = (ArrayList<GpScreenX>) this.screenx_dao
				.find_all_base_by_projectid(project_id);
		return screen_list;
	}

	@Override
	public ArrayList<GpScreenX> search_for_screens_by_activity_id(
			long activity_id) throws Exception {
		ArrayList<GpScreenX> screen_list = (ArrayList<GpScreenX>) this.screenx_dao
				.find_all_base_by_activity_id(activity_id);
		return screen_list;
	}
}
