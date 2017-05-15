package com.npb.gp.services;

import java.util.ArrayList;
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
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.dao.mysql.GpWizardDao;
import com.npb.gp.dao.mysql.support.screen.GpScreenWidgetParser;
import com.npb.gp.dao.mysql.support.screen.UpdateWidgetParser;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.domain.core.GpWizard;
import com.npb.gp.interfaces.services.IGpAndroidScreenService;
//import com.npb.gp.controllers.flex.test.Widget_Ui_Widget_Data_Structure_Check;
//import com.npb.gp.controllers.flex.test.data.ios.trep.main_screen.Iphone5s_english;

/**
 * Creation Date: 03/14/2014
 * 
 * @since version .35</p>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with Screens that use the Android OS</p>
 * 
 *        Modified Date: 10/06/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        <p>
 *        Modified the update_screen method to delete the widgets while updating
 *        screen and widgets
 *        </p>
 * 
 *        Modified Date: 05/05/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        <p>
 *        Modified the create_screen methods to old codes
 *        </p>
 * 
 *        Modified Date: 13/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        <p>
 *        Modified the parameter and the return type to GpScreenX in all methods
 * 
 *        And added the new method as delete_a_widget
 *        </p>
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex **
 * 
 *        Modified Date: 18/10/2015</br> Modified By: Reinaldo Lopez</p>
 * 
 *        added logic to update and delete verbs and microflow for widgets in
 *        the screen
 * 
 */
@Service("GpAndroidScreenService")
public class GpAndroidScreenService extends GpBaseService implements
IGpAndroidScreenService {

	private GpScreenXDao screenx_dao;
	private GpVerbsDao verb_dao;
	private GpScreenWidgetParser screen_widget_parser;
	private UpdateWidgetParser update_widget_parser;
	private GpMicroFlowService micro_flow_service;
	private GpActivityService gpActivityService;
	private GpWizardDao wizard_Dao;

	private GpButtonGroupDao buttonGroupDao;

	public GpButtonGroupDao getButtonGroupDao() {
		return buttonGroupDao;
	}

	@Resource(name = "GpButtonGroupDao")
	public void setButtonGroupDao(GpButtonGroupDao buttonGroupDao) {
		this.buttonGroupDao = buttonGroupDao;
	}
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

	public GpWizardDao getWizard_Dao() {
		return wizard_Dao;
	}

	@Resource(name = "GpWizardDao")
	public void setWizard_Dao(GpWizardDao wizard_Dao) {
		this.wizard_Dao = wizard_Dao;
	}

	@Override
	public GpScreenX create_screen(GpScreenX ascreen, GpUser user)
			throws Exception {

		ascreen.setCreatedby(user.getId());
		ascreen.setLastmodifiedby(user.getId());


		ascreen.setScreen_wizard_sequence_id(0L);
		ascreen.setWizard_id(0L);
		/*List<GpScreenX> screenList= this.screenx_dao.find_all_base_by_activity_id(ascreen.getActivity_id());
		Long sequence_id = 0L;
		for (GpScreenX gpScreenX : screenList) {
			if(gpScreenX.getScreen_wizard_sequence_id()>sequence_id){
				sequence_id=gpScreenX.getScreen_wizard_sequence_id();
			}	
		}
		ascreen.setScreen_wizard_sequence_id(sequence_id+1L);

		List<GpWizard> wizardList = this.wizard_Dao.find_all_by_activityid(ascreen.getActivity_id());
		ascreen.setWizard_id(wizardList.get(0).getId());*/

		this.screenx_dao.insert(ascreen);

		if (ascreen.getChildren().size() > 0) {
			this.screen_widget_parser.insert_widgetsfrom_screen(ascreen,
					screenx_dao, user);
		}

		if(ascreen.getWizard_id()>0){
			GpWizard gpWizard = new GpWizard();
			gpWizard.setActivity_id(ascreen.getActivity_id());

			// TODO set the sequence id also
			//gpActivityService.update_wizard(ascreen.getActivity_id(),ascreen.getWizard_id(),ascreen.getId());
		}
		return ascreen;
	}

	@Override
	public GpScreenX update_screen(GpScreenX ascreen, GpUser user)
			throws Exception {
		String custom_object = null;
		String wsdl_operation_id1 = null;
		System.err.println("dkdkldlkllllllllllllll--------------------------------------->"+ascreen.toString());
		ascreen.setLastmodifiedby(user.getId());
		GpScreenX the_screen = this.search_for_screen_by_screen_id(ascreen.getId());
		ascreen.setScreen_wizard_sequence_id(the_screen.getScreen_wizard_sequence_id());
		ascreen.setWizard_id(the_screen.getWizard_id());
		this.screenx_dao.update(ascreen);

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

		
		// saving wsdl_operation_id in VERBS table
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(ascreen.getChildren().values());
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(json);

		JSONArray custom_verb = (JSONArray) obj;

		System.out.println("--custom_verb---custom_verb>"+custom_verb.toString().toString());
		for(int i= 0; i<custom_verb.size(); i++) {
			JSONObject jObject = (JSONObject) custom_verb.get(i);
			System.out.println("------------------------------------------------>"+custom_verb.get(i));
			custom_object = (String) jObject.get("custom_verb_info");	
			if(custom_object!=null) {
				wsdl_operation_id1 = custom_object.substring(19, 21);
				if(StringUtils.isNumeric(wsdl_operation_id1)) {
					System.err.println("-=-=-=-=-THSI SI A NUM<BER "+wsdl_operation_id1);
				} else {
					wsdl_operation_id1 = wsdl_operation_id1.substring(0,1);
					System.err.println("-=-=-=-=-THSI SI AN ALTERED --------===--=---- NUM<BER---------->>>>>>>>>>>>>>>> "+wsdl_operation_id1);
				}
			}
		}

		List<GpVerb> wsdl_verb = this.verb_dao
				.get_verbs_by_base_verb_id(8, ascreen.getActivity_id());
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

		/*if(ascreen.getWizard_id()>0){
			GpWizard gpWizard = new GpWizard();
			gpWizard.setActivity_id(ascreen.getActivity_id());

			gpActivityService.update_wizard(ascreen.getActivity_id(),ascreen.getWizard_id(),ascreen.getId());
		}*/

		return ascreen;
	}

	@Override
	public void delete_screen(GpScreenX ascreen) throws Exception {

		Map<String, GpUiWidgetX> widgets_for_screen = this.screenx_dao
				.find_all_widgets_by_screen(ascreen.getId());

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

		if (widgets_for_screen != null && widgets_for_screen.size() > 0) {

			System.out.println("Child Size: " + widgets_for_screen.size());

			for (Entry<String, GpUiWidgetX> entry : widgets_for_screen
					.entrySet()) {
				this.screenx_dao.delete_a_widget(entry.getValue().getId(),
						ascreen.getId());
			}
		}

		this.wizard_Dao.deleteWizard_screen(ascreen);

		this.buttonGroupDao.delete(ascreen.getId());

		this.screenx_dao.delete(ascreen.getId());
	}

	@Override
	public GpScreenX search_for_screen_by_screen_id(long screen_id)
			throws Exception {
		GpScreenX the_screen = this.screenx_dao.find_by_id(screen_id);
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
