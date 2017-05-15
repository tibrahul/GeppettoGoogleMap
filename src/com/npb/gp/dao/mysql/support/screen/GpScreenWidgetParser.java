package com.npb.gp.dao.mysql.support.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
 * @author Dan Castillo</br> Date Created: 03/31/2014</br>
 * @since .35 </p>
 *
 *        The purpose of this class is to extract the widgets from a screen in
 *        </br> preparation to insert the widgets into the DB </p>
 * 
 *        Modified Date: 14/09/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Added new type under the handle_data_grid based on the New changes
 * 
 *        Modified By: Reinaldo Lopez </p>
 * 
 *        Added logic to insert the micro flow control of verbs Created the
 *        following methods get_verb_in_combo, get_base_verb
 * 
 * 
 *        Modified Date: 31/08/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Added new type under the single_section_container based on the
 *        Henrikh's changes
 * 
 * 
 *        Modified Date: 05/08/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Modified the handle_data_grid method to insert the column objects
 * 
 *        Modified Date: 12/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Changed the parent to String and added new variable as parent_id all
 *        get and set methods
 * 
 *        Added new concept for insert the verbs while inserting each widgets
 * 
 * 
 *        Modified Date: 06/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Added the values to the createdby and the lastmodifiedby
 * 
 *        Modified Date: 05/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Modified the ArrayList<GpUiWidgetX> to Map<String, GpUiWidgetX> in all
 *        methods and modified the Iteration for Map in all methods.
 * 
 * 
 *        Modified Date: 30/04/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Changed GpScreen to GpScreenX and GpUiWidget to GpUiWidgetX
 * 
 *
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 */

@Service("GpScreenWidgetParser")
public class GpScreenWidgetParser {

	Map<String, GpUiWidgetX> screen_widgets;
	private GpScreenXDao screen_dao;
	private GpVerbsDao verb_dao;
	private GpButtonGroupDao buttonGroupDao;

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

	private GpFlowControlService flow_control_service;

	public GpFlowControlService getFlow_control_service() {
		return flow_control_service;
	}

	@Resource(name = "GpFlowControlService")
	public void setFlow_control_service(
			GpFlowControlService flow_control_service) {
		this.flow_control_service = flow_control_service;
	}

	private GpMicroFlowService micro_flow_service;

	public GpMicroFlowService getMicro_flow_service() {
		return micro_flow_service;
	}

	@Resource(name = "GpMicroFlowService")
	public void setMicro_flow_service(GpMicroFlowService micro_flow_service) {
		this.micro_flow_service = micro_flow_service;
	}

	public Map<String, GpUiWidgetX> get_widgets_from_screen(GpScreenX ascreen,
			GpUser user) throws Exception {

		screen_widgets = new HashMap<String, GpUiWidgetX>();
		this.handle_screen_uiwidgets(ascreen, user);

		return screen_widgets;
	}

	public void insert_widgetsfrom_screen(GpScreenX ascreen,
			GpScreenXDao screen_dao, GpUser user) throws Exception {
		this.screen_dao = screen_dao;
		this.handle_screen_uiwidgets(ascreen, user);
	}

	public void insert_default_activity_verbs(long activity_id)
			throws Exception {
		List<GpVerb> base_verbs = this.verb_dao.get_all_base_verbs();

		if (base_verbs != null) {
			GpVerb the_verb = null;

			for (GpVerb baseVerb : base_verbs) {
				if (baseVerb.getCreateWithDefaultActivity() == 1) {
					the_verb = get_base_verb(baseVerb.getName());
					the_verb.setActivity_id(activity_id);

					// create default verbs without screen id
					long verb_id = this.verb_dao.insert_a_verb(the_verb)
							.getId();

					micro_flow_service.insert_verb_method_implementation(
							activity_id, verb_id, the_verb);
				}
			}
		}
	}

	private void handle_screen_uiwidgets(GpScreenX ascreen, GpUser user)
			throws Exception {
		
		Map<String, GpUiWidgetX> widgets = ascreen.getChildren();
		System.out.println("ActivityId " + ascreen.getActivity_id());
		
		for (Entry<String, GpUiWidgetX> widget : widgets.entrySet()) {

			widget.getValue().setCreatedby(user.getId());
			widget.getValue().setLastmodifiedby(user.getId());
			widget.getValue().setScreen_id(ascreen.getId());
			widget.getValue().setParent_id(ascreen.getId());
			widget.getValue().setWizard_id(ascreen.getWizard_id());
			widget.getValue().setScreen_wizard_sequence_id(ascreen.getScreen_wizard_sequence_id());
			
			if(widget.getValue().getType().equals("radio") && (widget.getValue().getGroup_values()!=null && !widget.getValue().getGroup_values().isEmpty())){
				setJsonForCheckboxRadioButtons(widget.getValue(),ascreen.getId());
			}
			
			if (widget.getValue().getType()
					.equals(GpUiTypeConstants.GpBorderContainer)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpPanel)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpCard)) {
				this.handle_single_section_container(widget.getValue(),
						ascreen.getId(), ascreen.getActivity_id(), user.getId());
				continue;
			}
			if (widget.getValue().getType()
					.equals(GpUiTypeConstants.GpAccordionContainer)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpTabBarContainer)) {
				this.handle_multi_section_container(widget.getValue(),
						ascreen.getId(), ascreen.getActivity_id(), user.getId());
				continue;
			}
			if (widget.getValue().getType()
					.equals(GpUiTypeConstants.GpDataGrid)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpList)
					|| widget.getValue().getType()
							.equals(GpUiTypeConstants.GpRadioButton)) {
				this.handle_data_grid(widget.getValue(), ascreen.getId(),
						ascreen.getActivity_id(), user.getId());
				continue;
			}
			if (!widget.getValue().getType()
					.equals(GpUiTypeConstants.GpDataGrid)) {
				this.handle_common_properties(widget.getValue(),
						ascreen.getId(), ascreen.getActivity_id());
				continue;
			}

		}
		System.out.println("$$$$$$ END WIDGET PROCESSING $$$$$$");

	}

	// private void handle_standard_widget(GpUiWidgetXBacking awidget, long
	// screen_id){

	// System.out.println("###### START PROCESSING STANDARD WIDGET ######");
	// this.handle_common_properties(awidget, screen_id);

	// System.out.println("###### END PROCESSING STANDARD WIDGET ######\n");

	// }

	private void handle_data_grid(GpUiWidgetX a_grid, long screen_id,
			long activity_id, long user_id) throws Exception {

		System.out.println("###### START PROCESSING A GRID WIDGET ######");
		this.handle_common_properties(a_grid, screen_id, activity_id);

		System.out.println("%%%%% START PROCESSING GRID COLUMNS %%%%%%%");

		if (a_grid.getColumns() != null) {
			ArrayList<GpUiWidgetX> columns = a_grid.getColumns();

			if (columns != null) {
				for (GpUiWidgetX a_column : columns) {
					System.out.println("%%%%% PROCESSING GRID COLUMN %%%%%%%");

					// a_column.setType(GpUiTypeConstants.GpGridColumn);
					a_column.setCreatedby(user_id);
					a_column.setLastmodifiedby(user_id);
					a_column.setScreen_id(a_grid.getScreen_id());
					a_column.setParent_id(a_grid.getId());

					this.handle_common_properties(a_column, screen_id,
							activity_id);

					System.out.println("%%%%% PROCESSED GRID COLUMN %%%%%%%");
				}
			}
		}

		/*
		 * if (a_grid.getChildren() != null) { Map<String, GpUiWidgetX> columns
		 * = a_grid.getChildren(); for (Entry<String, GpUiWidgetX> acolumn :
		 * columns.entrySet()) {
		 * 
		 * acolumn.getValue().setCreatedby(user_id);
		 * acolumn.getValue().setLastmodifiedby(user_id);
		 * acolumn.getValue().setScreen_id(a_grid.getScreen_id());
		 * acolumn.getValue().setParent_id(a_grid.getId());
		 * 
		 * this.handle_common_properties(acolumn.getValue(), screen_id,
		 * activity_id); } }
		 */

		System.out.println(" %%%%% END PROCESSING GRID COLUMNS %%%%%%%");

		System.out.println("###### END PROCESSING A GRID WIDGET ######\n");

	}

	private void handle_multi_section_container(GpUiWidgetX parent,
			long screen_id, long activity_id, long user_id) throws Exception {

		System.out
				.println("###### START PROCESSING MULTI-SECTION CONTAINER  ######");
		System.out.println("The container has: " + parent.getChildren().size()
				+ " sections");
		this.handle_common_properties(parent, screen_id, activity_id);
		System.out
				.println("%%%%% START PROCESSING SECTIONS BASE INFO  %%%%%%%");
		Map<String, GpUiWidgetX> sections = parent.getChildren();
		for (Entry<String, GpUiWidgetX> asection : sections.entrySet()) {

			asection.getValue().setCreatedby(user_id);
			asection.getValue().setLastmodifiedby(user_id);
			asection.getValue().setScreen_id(parent.getScreen_id());
			asection.getValue().setParent_id(parent.getId());

			this.handle_common_properties(asection.getValue(), screen_id,
					activity_id);

			System.out
					.println("$$$$$$ START PROCESSING A SECTIONS CHILDREN  $$$$$$");
			Map<String, GpUiWidgetX> sections_children = asection.getValue()
					.getChildren();
			for (Entry<String, GpUiWidgetX> awidget : sections_children
					.entrySet()) {

				awidget.getValue().setCreatedby(user_id);
				awidget.getValue().setLastmodifiedby(user_id);
				awidget.getValue().setScreen_id(
						asection.getValue().getScreen_id());
				awidget.getValue().setParent_id(asection.getValue().getId());

				if(awidget.getValue().getType().equals("checkbox") || awidget.getValue().getType().equals("radio") && (awidget.getValue().getGroup_values()!=null && !awidget.getValue().getGroup_values().isEmpty())){
					setJsonForCheckboxRadioButtons(awidget.getValue(),screen_id);
				}
				
				System.out
						.println("In the childrend loop - awidget.getType() is: "
								+ awidget.getValue().getType());
				if (awidget.getValue().getType()
						.equals(GpUiTypeConstants.GpDataGrid)
						|| awidget.getValue().getType()
						.equals(GpUiTypeConstants.GpList) || awidget.getValue().getType()
				.equals(GpUiTypeConstants.GpRadioButton)) {
					this.handle_data_grid(awidget.getValue(), screen_id,
							user_id, activity_id);
					continue;
				}
				this.handle_common_properties(awidget.getValue(), screen_id,
						activity_id);
			}
			System.out
					.println("$$$$$$ END PROCESSING A SECTIONS CHILDREN  $$$$$$");
		}
		System.out.println("%%%%% END PROCESSING SECTIONS BASE INFO %%%%%%%");

		System.out
				.println("###### END PROCESSING MULTI-SECTION CONTAINER  ######");
	}

	private void handle_single_section_container(GpUiWidgetX parent,
			long screen_id, long activity_id, long user_id) throws Exception {
		System.out.println("ActivityId " + activity_id);
		System.out
				.println("###### START PROCESSING SINGLE-SECTION CONTAINER  ######");
		System.out
				.println("Number of Widgets attached to border contianer is: "
						+ parent.getChildren().size());
		this.handle_common_properties(parent, screen_id, activity_id);// this
																		// should
																		// lead
																		// to
		// an insert or
		// update

		System.out
				.println("%%%%% START PROCESSING SINGLE-SECTION CHILDREN %%%%%%%");
		Map<String, GpUiWidgetX> children = parent.getChildren();
		for (Entry<String, GpUiWidgetX> widget : children.entrySet()) {

			widget.getValue().setCreatedby(user_id);
			widget.getValue().setLastmodifiedby(user_id);
			widget.getValue().setParent_id(parent.getId());
			widget.getValue().setScreen_id(parent.getScreen_id());

			if(widget.getValue().getType().equals("checkbox") || widget.getValue().getType().equals("radio") && (widget.getValue().getGroup_values()!=null && !widget.getValue().getGroup_values().isEmpty())){
				setJsonForCheckboxRadioButtons(widget.getValue(),screen_id);
			}
			
			if (widget.getValue().getType()
						.equals(GpUiTypeConstants.GpDataGrid)
						|| widget.getValue().getType()
								.equals(GpUiTypeConstants.GpList)
						|| widget.getValue().getType()
								.equals(GpUiTypeConstants.GpRadioButton)) {
				this.handle_data_grid(widget.getValue(), screen_id, user_id,
						activity_id);
				continue;
			}
			this.handle_common_properties(widget.getValue(), screen_id,
					activity_id);
		}
		System.out
				.println(" %%%%% END PROCESSING SINGLE-SECTION CHILDREN %%%%%%%");

		System.out
				.println("###### END PROCESSING SINGLE-SECTION CONTAINER  ######\n");
	}

	private void handle_common_properties(GpUiWidgetX awidget, long screen_id,
			long activity_id) throws Exception {

		// here do the insert
		awidget.setUi_technology("flex");

		System.out.println("*** Is it container: " + awidget.getIs_container());

		this.screen_dao.insert_a_widget(awidget, screen_id);

		if (!awidget.getEvent_verb_combo().isEmpty()) {

			GpVerb the_verb = get_verb_in_combo(awidget);
			System.out.println("ActivityId " + activity_id);
			List<GpVerb> verbs = this.verb_dao
					.find_all_verbs_by_activity_id(activity_id);
			verbs.addAll(this.verb_dao.get_verbs_by_base_verb_id(0,activity_id));
			verbs.removeAll(Collections.singleton(null));  
			boolean found = false;
			for (GpVerb verbs_in : verbs) {
				if (verbs_in.getAction_on_data().equals(
						the_verb.getAction_on_data())) {
					found = true;
					the_verb = verbs_in;
					break;
				}
			}

			if (found) { // update screen id
				System.out.println("Existing verb");
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

	private void setJsonForCheckboxRadioButtons(GpUiWidgetX widget, Long ascreenId) throws Exception{
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