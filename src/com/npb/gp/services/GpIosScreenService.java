package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.dao.mysql.support.screen.GpScreenWidgetParser;
import com.npb.gp.dao.mysql.support.screen.UpdateWidgetParser;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.services.IGpIosScreenService;

/**
 * @author Dan Castillo</br> Creation Date: 03/14/2014</br>
 * @since version .35 </p>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with Screens that use the IOS OS </p>
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
 *        Modified Date: 08/04/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        <p>
 *        Removed the GpScreenDao
 *        </p>
 * 
 *        Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        <p>
 *        Modified the retrun type to GpScreenX and the Dao changed to
 *        GpScreenXDao for all methods
 *        </p>
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex **
 * 
 *        Modified Date: 19/03/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        modified the create_screen method to insert the screen and children's
 * 
 * 
 *        Modified Date: 26/03/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Modified the parameter and return type to update_screen as GpScreenX
 *        and modified parameter to delete screen as GpScreenX
 * 
 *        Modified Date: 27/03/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        update_screen method modified to, if the person adds or removes the
 *        widget will reflect to the database
 * 
 *        Modified Date: 31/03/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Wrote the new method as get_all_base_verbs for retrieve the all base
 *        verbs from the database
 * 
 *        Modified the create screen method to insert the verbs as well
 * 
 *        Modified Date: 03/04/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        <p>
 *        Added new condition to the delete delete_a_widget
 *        </p>
 * 
 *        Modified Date: 18/10/2015</br> Modified By: Reinaldo Lopez</p>
 * 
 *        added logic to update and delete verbs and microflow for widgets in
 *        the screen
 * 
 */
@Service("GpIosScreenService")
public class GpIosScreenService extends GpBaseService implements
		IGpIosScreenService {

	private GpScreenWidgetParser screen_widget_parser;
	private GpVerbsDao verb_dao;
	private GpScreenXDao screenx_dao;
	private UpdateWidgetParser update_widget_parser;
	private GpMicroFlowService micro_flow_service;

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

	public GpScreenWidgetParser getScreen_widget_parser() {
		return screen_widget_parser;
	}

	@Resource(name = "GpScreenWidgetParser")
	public void setScreen_widget_parser(
			GpScreenWidgetParser screen_widget_parser) {
		this.screen_widget_parser = screen_widget_parser;
	}

	public GpVerbsDao getVerb_dao() {
		return verb_dao;
	}

	@Resource(name = "GpVerbsDao")
	public void setVerb_dao(GpVerbsDao verb_dao) {
		this.verb_dao = verb_dao;
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

		this.screenx_dao.insert(ascreen);

		if (ascreen.getChildren().size() > 0) {
			this.screen_widget_parser.insert_widgetsfrom_screen(ascreen,
					screenx_dao, user);
		}
		return ascreen;
	}

	@Override
	public GpScreenX update_screen(GpScreenX ascreen, GpUser user)
			throws Exception {

		ascreen.setLastmodifiedby(user.getId());

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
		ArrayList<GpScreenX> screens_by_activity = this.screenx_dao
				.find_all_base_by_activity_id(activity_id);
		return screens_by_activity;
	}

	@Override
	public List<GpVerb> get_all_base_verbs() throws Exception {
		return this.verb_dao.get_all_base_verbs();
	}
}
