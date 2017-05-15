package com.npb.gp.dao.mysql.support.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpUiTypeConstants;
import com.npb.gp.dao.mysql.GpButtonGroupDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.domain.core.GpButtonGroup;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.services.GpFlowControlService;
import com.npb.gp.services.GpMicroFlowService;

/**
 * 
 * @author Suresh Palanisamy/br> Date Created: 06/05/2015</br>
 * @since .75 </p>
 *
 *        The purpose of this class is to extract the widgets from a screen in
 *        </br> preparation to update the widgets into the DB </p>
 * 
 * 
 *        Modified Date: 14/09/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Added new type under the handle_data_grid based on the New changes
 * 
 *        Modified Date: 31/08/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Added new type under the single_section_container based on the
 *        Henrikh's changes
 * 
 *        Modified Date: 05/08/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Modified the handle_data_grid method to update the column objects
 * 
 *        Modified Date: 10/06/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Removed unwanted codes. Moved the deleting functionality to service
 *        layer while updating a screen
 * 
 *        Modified Date: 12/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Changed the parent to string and added parent_id in all get and set
 *        methods
 * 
 *        Modified Date: 18/10/2015</br> Modified By: Reinaldo Lopez </p>
 * 
 *        Added insert_widget_verb method
 */

@Service("UpdateWidgetParser")
public class UpdateWidgetParser {
	private GpScreenXDao screen_dao;

	private GpVerbsDao verb_dao;
	private GpButtonGroupDao buttonGroupDao;
	private GpFlowControlService flow_control_service;
	private GpMicroFlowService micro_flow_service;

	public GpVerbsDao getVerb_dao() {
		return verb_dao;
	}

	@Resource(name = "GpVerbsDao")
	public void setVerb_dao(GpVerbsDao verb_dao) {
		this.verb_dao = verb_dao;
	}

	public GpButtonGroupDao getButtonGroupDao() {
		return buttonGroupDao;
	}
	
	@Resource(name = "GpButtonGroupDao")
	public void setButtonGroupDao(GpButtonGroupDao buttonGroupDao) {
		this.buttonGroupDao = buttonGroupDao;
	}
	
	public GpFlowControlService getFlow_control_service() {
		return flow_control_service;
	}

	@Resource(name = "GpFlowControlService")
	public void setFlow_control_service(
			GpFlowControlService flow_control_service) {
		this.flow_control_service = flow_control_service;
	}

	public GpMicroFlowService getMicro_flow_service() {
		return micro_flow_service;
	}

	@Resource(name = "GpMicroFlowService")
	public void setMicro_flow_service(GpMicroFlowService micro_flow_service) {
		this.micro_flow_service = micro_flow_service;
	}

	public void update_widgetsfrom_screen(GpScreenX ascreen,
			GpScreenXDao screen_dao, GpUser user) throws Exception {
		this.screen_dao = screen_dao;
		this.handle_screen_uiwidgets(ascreen, user);
	}

	private void handle_screen_uiwidgets(GpScreenX ascreen, GpUser user)
			throws Exception {

		Map<String, GpUiWidgetX> widgets = ascreen.getChildren();

		for (Entry<String, GpUiWidgetX> widget : widgets.entrySet()) {

			widget.getValue().setScreen_id(ascreen.getId());
			widget.getValue().setParent_id(ascreen.getId());

			if(widget.getValue().getType().equals("radio") && (widget.getValue().getGroup_values()!=null && !widget.getValue().getGroup_values().isEmpty())){
				setJsonForCheckboxRadiobuttons(widget.getValue(),ascreen.getId());
			}
			
			if (widget.getValue().getType()
					.equals(GpUiTypeConstants.GpBorderContainer)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpPanel)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpCard)) {
				this.handle_single_section_container(widget.getValue(),
						ascreen.getId(), user.getId(), ascreen.getActivity_id());
				continue;
			}
			if (widget.getValue().getType()
					.equals(GpUiTypeConstants.GpAccordionContainer)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpTabBarContainer)) {
				this.handle_multi_section_container(widget.getValue(),
						ascreen.getId(), user.getId(), ascreen.getActivity_id());
				continue;
			}
			if (widget.getValue().getType()
					.equals(GpUiTypeConstants.GpDataGrid)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpList)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpRadioButton)) {
				this.handle_data_grid(widget.getValue(), ascreen.getId(),
						user.getId(), ascreen.getActivity_id());
				continue;
			}
			if (!widget.getValue().getType()
					.equals(GpUiTypeConstants.GpDataGrid)) {
				this.handle_common_properties(widget.getValue(),
						ascreen.getId(), 0, ascreen.getActivity_id());
				continue;
			}
		}
	}

	private void handle_data_grid(GpUiWidgetX a_grid, long screen_id,
			long user_id, long activity_id) throws Exception {

		this.handle_common_properties(a_grid, screen_id, user_id, activity_id);

		if (a_grid.getColumns() != null) {
			ArrayList<GpUiWidgetX> columns = a_grid.getColumns();

			if (columns != null) {
				for (GpUiWidgetX gpUiWidgetX : columns) {
					System.out.println("%%%%% PROCESSING GRID COLUMN %%%%%%%");

					gpUiWidgetX.setScreen_id(a_grid.getScreen_id());
					gpUiWidgetX.setParent_id(a_grid.getId());

					this.handle_common_properties(gpUiWidgetX, screen_id,
							user_id, activity_id);

					System.out.println("%%%%% PROCESSED GRID COLUMN %%%%%%%");
				}
			}
		}

		/*
		 * Map<String, GpUiWidgetX> columns = a_grid.getChildren(); for
		 * (Entry<String, GpUiWidgetX> acolumn : columns.entrySet()) {
		 * acolumn.getValue().setScreen_id(a_grid.getScreen_id());
		 * acolumn.getValue().setParent_id(a_grid.getId());
		 * this.handle_common_properties(acolumn.getValue(), screen_id,
		 * user_id); }
		 */
	}

	private void handle_multi_section_container(GpUiWidgetX parent,
			long screen_id, long user_id, long activity_id) throws Exception {

		this.handle_common_properties(parent, screen_id, user_id, activity_id);

		Map<String, GpUiWidgetX> sections = parent.getChildren();
		for (Entry<String, GpUiWidgetX> asection : sections.entrySet()) {
			asection.getValue().setScreen_id(parent.getScreen_id());
			asection.getValue().setParent_id(parent.getId());

			this.handle_common_properties(asection.getValue(), screen_id,
					user_id, activity_id);

			Map<String, GpUiWidgetX> sections_children = asection.getValue()
					.getChildren();
			for (Entry<String, GpUiWidgetX> awidget : sections_children
					.entrySet()) {
				awidget.getValue().setScreen_id(
						asection.getValue().getScreen_id());
				awidget.getValue().setParent_id(asection.getValue().getId());
				
				if(awidget.getValue().getType().equals("radio") && (awidget.getValue().getGroup_values()!=null && !awidget.getValue().getGroup_values().isEmpty())){
					setJsonForCheckboxRadiobuttons(awidget.getValue(),screen_id);
				}
				
				if (awidget.getValue().getType()
						.equals(GpUiTypeConstants.GpDataGrid)|| awidget.getValue().getType()
						.equals(GpUiTypeConstants.GpList) || awidget.getValue().getType()
				.equals(GpUiTypeConstants.GpRadioButton)){
					this.handle_data_grid(awidget.getValue(), screen_id,
							user_id, activity_id);
					continue;
				}
				this.handle_common_properties(awidget.getValue(), screen_id,
						user_id, activity_id);
			}
		}
	}

	private void handle_single_section_container(GpUiWidgetX parent,
			long screen_id, long user_id, long activity_id) throws Exception {

		this.handle_common_properties(parent, screen_id, user_id, activity_id);

		Map<String, GpUiWidgetX> children = parent.getChildren();
		for (Entry<String, GpUiWidgetX> widget : children.entrySet()) {
			widget.getValue().setParent_id(parent.getId());
			widget.getValue().setScreen_id(parent.getScreen_id());

			if(widget.getValue().getType().equals("radio") && (widget.getValue().getGroup_values()!=null && !widget.getValue().getGroup_values().isEmpty())){
				setJsonForCheckboxRadiobuttons(widget.getValue(),screen_id);
			}
			if (widget.getValue().getType()
					.equals(GpUiTypeConstants.GpDataGrid)|| widget.getValue().getType()
					.equals(GpUiTypeConstants.GpList)
			|| widget.getValue().getType()
					.equals(GpUiTypeConstants.GpRadioButton)) {
				this.handle_data_grid(widget.getValue(), screen_id, user_id,
						activity_id);
				continue;
			}
			this.handle_common_properties(widget.getValue(), screen_id,
					user_id, activity_id);
		}
	}

	private void handle_common_properties(GpUiWidgetX awidget, long screen_id,
			long user_id, long activity_id) throws Exception {

		awidget.setLastmodifiedby(user_id);
		if (awidget.getId() == 0L) {
			awidget.setCreatedby(user_id);
			System.err.println("awidget---->>>>>>>"+awidget.toString());
			this.screen_dao.insert_a_widget(awidget, screen_id);
			System.out.println("*************** Inserted new Widget: "
					+ awidget.getId() + " ***************");
		} else {
			this.screen_dao.update_a_widget(awidget, screen_id);

			System.out.println("~~~~~~~~~~~~~~ Updated Widget: "
					+ awidget.getId() + " ~~~~~~~~~~~~~~");
		}

		insert_widget_verb(awidget, screen_id, activity_id);
	}

	private void insert_widget_verb(GpUiWidgetX awidget, long screen_id,
			long activity_id) throws Exception {
		if (!awidget.getEvent_verb_combo().isEmpty()) {

			GpVerb the_verb = get_verb_in_combo(awidget);

			List<GpVerb> verbs = this.verb_dao
					.find_all_verbs_by_activity_id(activity_id);
			verbs.addAll(this.verb_dao.get_verbs_by_base_verb_id(0,activity_id));
			boolean found = false;
			for (GpVerb verbs_in : verbs) {
				if(verbs_in!=null && verbs_in.getAction_on_data().equals(
						the_verb.getAction_on_data())) {
					found = true;
					the_verb = verbs_in;
					break;
				}
			}

			if (found) { // update screen id
				String newScreenId = String.valueOf(screen_id);
				if (StringUtils.isNotBlank(the_verb.getScreen_id())
						&& !"null".equalsIgnoreCase(the_verb.getScreen_id()
								.trim())
						&& !"0".equalsIgnoreCase(the_verb.getScreen_id().trim())) {
					newScreenId = the_verb.getScreen_id() + "," + newScreenId;
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
				// remove null values
				String[] arr = newScreenId.split(",");
				newScreenId = "";
				for (String arrElm : arr) {
					if (StringUtils.isNotBlank(arrElm.trim())
							&& !"null".equalsIgnoreCase(arrElm.trim())
							&& !"0".equalsIgnoreCase(arrElm.trim())) {
						if (StringUtils.isNotBlank(newScreenId)) {
							newScreenId += ",";
						}
						newScreenId += arrElm.trim();
					}
				}
				the_verb.setScreen_id(newScreenId);
				this.verb_dao.update_a_verb_screen(the_verb);

			} else { // insert
				the_verb.setScreen_id(String.valueOf(screen_id));
				the_verb.setActivity_id(activity_id);
				long verb_id = this.verb_dao.insert_a_verb(the_verb).getId();

				micro_flow_service.insert_verb_method_implementation(
						activity_id, verb_id, the_verb);
			}
		}
	}

	private GpVerb get_verb_in_combo(GpUiWidgetX awidget) throws Exception {
		GpVerb the_verb;

		String event = awidget.getEvent_verb_combo()
				.substring(0, awidget.getEvent_verb_combo().length() - 1)
				.split(",")[1];

		switch (event) {
		case "GpCreate":
			the_verb = get_base_verb("GpCreate");
			break;

		case "GpUpdate":
			the_verb = get_base_verb("GpUpdate");
			break;

		case "GpDelete":
			the_verb = get_base_verb("GpDelete");
			break;

		case "GpSearch":
			the_verb = get_base_verb("GpSearch");
			break;

		case "GpSearchForUpdate":
			the_verb = get_base_verb("GpSearchForUpdate");
			break;
			
		case "GpSearchDetail":
			the_verb = get_base_verb("GpSearchDetail");
			break;
			
		case "GpGetAllValues":
			the_verb = get_base_verb("GpGetAllValues");
			break;

		case "GpGetNounById":
			the_verb = get_base_verb("GpGetNounById");
			break;

		case "GpCustom":
			the_verb = get_base_verb("GpCustom");
			break;
		case "GpTakePhoto":
			the_verb = get_base_verb("GpTakePhoto");
			break;	
			
		case "GpRecordSound":
			the_verb = get_base_verb("GpRecordSound");
			break;
			
		case "GpRecordVideo":
			the_verb = get_base_verb("GpRecordVideo");
			break;
		case "GpGetNounByParentId":
			the_verb = get_base_verb("GpGetNounByParentId");
			break;
			
		case "GpDeleteByParentId":
			the_verb = get_base_verb("GpDeleteByParentId");
			break;
			
		case "GpCancel":
			the_verb = get_base_verb("GpCancel");
			break;
		default:
			the_verb = new GpVerb();
			the_verb.setAction_on_data(event);
		}

		return the_verb;
	}

	private GpVerb get_base_verb(String action) throws Exception {
		GpVerb the_verb = new GpVerb();

		List<GpVerb> verbs = this.verb_dao.get_all_base_verbs();
		for (GpVerb gpVerb : verbs) {
			if (gpVerb.getAction_on_data().equals(action)) {
				the_verb = this.verb_dao.find_base_verbs_by_id(gpVerb.getId());
			}
		}

		return the_verb;
	}

	private void setJsonForCheckboxRadiobuttons(GpUiWidgetX widget, Long ascreenId) throws Exception{
		//if widget is a checkbox and grpValue column is not null then fetch group id on the basis of groupname and set for it
			 List<GpButtonGroup> grplist = this.buttonGroupDao.getAllGroupsForScreen(ascreenId);
			 for (Iterator iterator = grplist.iterator(); iterator.hasNext();) {
				GpButtonGroup gpButtonGroup = (GpButtonGroup) iterator.next();
				List list = Arrays.asList(widget.getGroup_values().split(":"));
				String str = (String) list.get(2);
				boolean found = Arrays.asList(str.split(",")).contains(gpButtonGroup.getName());

				if(found){
					String group_values = "{group_id:"+gpButtonGroup.getId()+",group_name:"+gpButtonGroup.getName()+",isChecked:"+widget.isChecked()+"}";
					widget.setGroup_values(group_values);
				}
			}
	}
}