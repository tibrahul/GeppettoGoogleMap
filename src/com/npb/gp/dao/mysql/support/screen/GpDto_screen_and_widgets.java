package com.npb.gp.dao.mysql.support.screen;

import java.util.Date;

/**
 * 
 * @author
 * 
 *         Modified Date: 09/10/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Added new variables as image_src to save the image sources
 * 
 *         Modified Date: 10/09/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Added new variables as header and footer for the component as card in
 *         under the widgets
 * 
 *         Modified Date: 04/09/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Added new variable as target_url for the component as link in under
 *         the widgets
 * 
 *         Modified Date: 05/08/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Removed the variable as columns
 * 
 *         Modified Date: 01/07/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Added the new variables as rows and columns inside widgets part
 * 
 *         Modified Date: 19/06/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Removed the variables X and Y
 * 
 *         Modified Date: 04/06/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Added new variable as css_class
 * 
 *         Modified Date: 25/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Changed the is_container to Boolean and modified the getter and
 *         setter methods and removed parent variable
 * 
 *         Added new variables as portraitOffsetX, portraitOffsetY,
 *         landscapeOffsetX and landscapeOffsetY
 * 
 *         Modified Date: 12/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Changed the parent to String and added new variables as parent_id and
 *         the widget_events
 *
 *         Modified Date: 11/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Added some more new variables as client devices
 *
 *         Modified Date: 04/05/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Added new variable as widget_ui_technology and added getter and
 *         setter methods for the screen_is_orientation_locked,
 *         widget_is_container and the widget_ui_technology
 *
 *         Modified Date: 26/03/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *         Modified type to String for the screen_is_orientation_locked and the
 *         widget_is_container variables
 *
 */

/**
 * @author rdpm
 *
 */
public class GpDto_screen_and_widgets {

	/* SCREEN ATTRIBUTES START */
	private long screen_id;
	private String screen_name;
	private String screen_description;
	private String screen_label;
	// Created role in GpDto_screen_and_widgets
	private String screen_role;
	private String screen_notes;
	private long screen_width;
	private long screen_height;
	private long screen_client_device_type_id;
	private String screen_client_device_type;
	private String screen_client_device_type_name;
	private String screen_client_device_type_label;
	private String screen_client_device_type_description;
	private String screen_client_device_type_os_name;
	private String screen_client_device_screen_size;
	private String screen_client_device_resolution;
	private String screen_client_device_ppcm;
	private String screen_client_device_type_os_version;
	private String screen_client_language_type;
	private String screen_client_library_type;
	private long screen_human_language_id;
	private String screen_type;
	private long screen_projectid;
	private long screen_activity_id;
	private String screen_current_orientation;
	private String screen_is_orientation_locked;
	private String screen_landscape_image_name;
	private String screen_portrait_image_name;
	private long screen_primary_noun_id;
	private String screen_secondary_noun_ids;

	private Date screen_createdate;
	private long screen_createdby;
	private Date screen_lastmodifieddate;
	private long screen_lastmodifiedby;
	private String screen_event_verb_combo;
	private long screen_wizard_id;
	private long screen_wizard_sequence_id;
	/* SCREEN ATTRIBUTES END */

	/* WIDGETS ATTRIBUTES START */

	private long widget_id;
	private String widget_name;
	private String widget_description;
	private String widget_label;
	private String widget_supports_label;
	private Boolean widget_is_container;
	private String widget_ui_technology;
	private long widget_parent_id;
	private long widget_screen_id;
	private String widget_parent_name;
	private String widget_type;
	private long widget_width;
	private long widget_height;
	// private long widget_x;
	// private long widget_y;
	public long widget_landscape_x;
	public long widget_landscape_y;
	public long widget_landscape_width;
	public long widget_landscape_height;
	public long widget_portrait_x;
	public long widget_portrait_y;
	public long widget_portrait_width;
	public long widget_portrait_height;
	private String widget_data_binding_context;
	private long widget_noun_id;
	private long widget_noun_attribute_id;
	private String widget_verb_binding_context;
	private String widget_verb_target;
	private long widget_number_of_children;
	private String widget_extended_attributes;
	private String widget_event_verb_combo;
	private String widget_events;
	private String widget_notes;
	private Date widget_createdate;
	private long widget_createdby;
	private Date widget_lastmodifieddate;
	private long widget_lastmodifiedby;
	private long widget_portraitOffsetX;
	private long widget_portraitOffsetY;
	private long widget_landscapeOffsetX;
	private long widget_landscapeOffsetY;
	private String widget_css_class;
	private long widget_rows;
	// private long widget_columns;
	private String widget_target_url;
	private String widget_header;
	private String widget_footer;
	private String widget_image_src;
	private String widget_extra_verb_info;
	private String widget_custom_verb_info;
	public String getWidget_custom_verb_info() {
		return widget_custom_verb_info;
	}

	public void setWidget_custom_verb_info(String widget_custom_verb_info) {
		this.widget_custom_verb_info = widget_custom_verb_info;
	}

	private long widget_data_binding_target_noun_id;
	private long widget_data_binding_target_noun_attr_id;
	private long widget_section_position;
	private String group_values;

	private boolean already_added;

	public String getScreen_event_verb_combo() {
		return screen_event_verb_combo;
	}

	public void setScreen_event_verb_combo(String screen_event_verb_combo) {
		this.screen_event_verb_combo = screen_event_verb_combo;
	}

	public boolean isAlready_added() {
		return already_added;
	}

	public void setAlready_added(boolean already_added) {
		this.already_added = already_added;
	}

	public long getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(long screen_id) {
		this.screen_id = screen_id;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getScreen_description() {
		return screen_description;
	}

	public void setScreen_description(String screen_description) {
		this.screen_description = screen_description;
	}

	public String getScreen_client_device_type_name() {
		return screen_client_device_type_name;
	}

	public void setScreen_client_device_type_name(
			String screen_client_device_type_name) {
		this.screen_client_device_type_name = screen_client_device_type_name;
	}

	public String getScreen_label() {
		return screen_label;
	}

	public void setScreen_label(String screen_label) {
		this.screen_label = screen_label;
	}

	public String getScreen_notes() {
		return screen_notes;
	}

	public void setScreen_notes(String screen_notes) {
		this.screen_notes = screen_notes;
	}

	public long getScreen_width() {
		return screen_width;
	}

	public void setScreen_width(long screen_width) {
		this.screen_width = screen_width;
	}

	public long getScreen_height() {
		return screen_height;
	}

	public void setScreen_height(long screen_height) {
		this.screen_height = screen_height;
	}

	public long getScreen_client_device_type_id() {
		return screen_client_device_type_id;
	}

	public void setScreen_client_device_type_id(
			long screen_client_device_type_id) {
		this.screen_client_device_type_id = screen_client_device_type_id;
	}

	public String getScreen_client_device_type() {
		return screen_client_device_type;
	}

	public void setScreen_client_device_type(String screen_client_device_type) {
		this.screen_client_device_type = screen_client_device_type;
	}

	public String getScreen_client_device_type_label() {
		return screen_client_device_type_label;
	}

	public void setScreen_client_device_type_label(
			String screen_client_device_type_label) {
		this.screen_client_device_type_label = screen_client_device_type_label;
	}

	public String getScreen_client_device_type_os_name() {
		return screen_client_device_type_os_name;
	}

	public void setScreen_client_device_type_os_name(
			String screen_client_device_type_os_name) {
		this.screen_client_device_type_os_name = screen_client_device_type_os_name;
	}

	public String getScreen_client_device_screen_size() {
		return screen_client_device_screen_size;
	}

	public void setScreen_client_device_screen_size(
			String screen_client_device_screen_size) {
		this.screen_client_device_screen_size = screen_client_device_screen_size;
	}

	public String getScreen_client_device_resolution() {
		return screen_client_device_resolution;
	}

	public void setScreen_client_device_resolution(
			String screen_client_device_resolution) {
		this.screen_client_device_resolution = screen_client_device_resolution;
	}

	public String getScreen_client_device_ppcm() {
		return screen_client_device_ppcm;
	}

	public void setScreen_client_device_ppcm(String screen_client_device_ppcm) {
		this.screen_client_device_ppcm = screen_client_device_ppcm;
	}

	public String getScreen_client_device_type_os_version() {
		return screen_client_device_type_os_version;
	}

	public void setScreen_client_device_type_os_version(
			String screen_client_device_type_os_version) {
		this.screen_client_device_type_os_version = screen_client_device_type_os_version;
	}

	public String getScreen_client_language_type() {
		return screen_client_language_type;
	}

	public void setScreen_client_language_type(
			String screen_client_language_type) {
		this.screen_client_language_type = screen_client_language_type;
	}

	public String getScreen_client_library_type() {
		return screen_client_library_type;
	}

	public void setScreen_client_library_type(String screen_client_library_type) {
		this.screen_client_library_type = screen_client_library_type;
	}

	public String getScreen_client_device_type_description() {
		return screen_client_device_type_description;
	}

	public void setScreen_client_device_type_description(
			String screen_client_device_type_description) {
		this.screen_client_device_type_description = screen_client_device_type_description;
	}

	public long getScreen_human_language_id() {
		return screen_human_language_id;
	}

	public void setScreen_human_language_id(long screen_human_language_id) {
		this.screen_human_language_id = screen_human_language_id;
	}

	public String getScreen_type() {
		return screen_type;
	}

	public void setScreen_type(String screen_type) {
		this.screen_type = screen_type;
	}

	public long getScreen_projectid() {
		return screen_projectid;
	}

	public void setScreen_projectid(long screen_projectid) {
		this.screen_projectid = screen_projectid;
	}

	public long getScreen_activity_id() {
		return screen_activity_id;
	}

	public void setScreen_activity_id(long screen_activity_id) {
		this.screen_activity_id = screen_activity_id;
	}

	public String getScreen_current_orientation() {
		return screen_current_orientation;
	}

	public void setScreen_current_orientation(String screen_current_orientation) {
		this.screen_current_orientation = screen_current_orientation;
	}

	public String isScreen_is_orientation_locked() {
		return screen_is_orientation_locked;
	}

	public void setScreen_is_orientation_locked(
			String screen_is_orientation_locked) {
		this.screen_is_orientation_locked = screen_is_orientation_locked;
	}

	public String getScreen_landscape_image_name() {
		return screen_landscape_image_name;
	}

	public void setScreen_landscape_image_name(
			String screen_landscape_image_name) {
		this.screen_landscape_image_name = screen_landscape_image_name;
	}

	public String getScreen_portrait_image_name() {
		return screen_portrait_image_name;
	}

	public void setScreen_portrait_image_name(String screen_portrait_image_name) {
		this.screen_portrait_image_name = screen_portrait_image_name;
	}

	public long getScreen_primary_noun_id() {
		return screen_primary_noun_id;
	}

	public void setScreen_primary_noun_id(long screen_primary_noun_id) {
		this.screen_primary_noun_id = screen_primary_noun_id;
	}

	public String getScreen_secondary_noun_ids() {
		return screen_secondary_noun_ids;
	}

	public void setScreen_secondary_noun_ids(String screen_secondary_noun_ids) {
		this.screen_secondary_noun_ids = screen_secondary_noun_ids;
	}

	public Date getScreen_createdate() {
		return screen_createdate;
	}

	public void setScreen_createdate(Date screen_createdate) {
		this.screen_createdate = screen_createdate;
	}

	public long getScreen_createdby() {
		return screen_createdby;
	}

	public void setScreen_createdby(long screen_createdby) {
		this.screen_createdby = screen_createdby;
	}

	public Date getScreen_lastmodifieddate() {
		return screen_lastmodifieddate;
	}

	public void setScreen_lastmodifieddate(Date screen_lastmodifieddate) {
		this.screen_lastmodifieddate = screen_lastmodifieddate;
	}

	public long getScreen_lastmodifiedby() {
		return screen_lastmodifiedby;
	}

	public void setScreen_lastmodifiedby(long screen_lastmodifiedby) {
		this.screen_lastmodifiedby = screen_lastmodifiedby;
	}

	public long getWidget_id() {
		return widget_id;
	}

	public void setWidget_id(long widget_id) {
		this.widget_id = widget_id;
	}

	public String getWidget_name() {
		return widget_name;
	}

	public void setWidget_name(String widget_name) {
		this.widget_name = widget_name;
	}

	public String getWidget_description() {
		return widget_description;
	}

	public void setWidget_description(String widget_description) {
		this.widget_description = widget_description;
	}

	public String getWidget_label() {
		return widget_label;
	}

	public void setWidget_label(String widget_label) {
		this.widget_label = widget_label;
	}

	public String getWidget_supports_label() {
		return widget_supports_label;
	}

	public void setWidget_supports_label(String widget_supports_label) {
		this.widget_supports_label = widget_supports_label;
	}

	public String getWidget_parent_name() {
		return widget_parent_name;
	}

	public void setWidget_parent_name(String widget_parent_name) {
		this.widget_parent_name = widget_parent_name;
	}

	public String getWidget_type() {
		return widget_type;
	}

	public void setWidget_type(String widget_type) {
		this.widget_type = widget_type;
	}

	public long getWidget_width() {
		return widget_width;
	}

	public void setWidget_width(long widget_width) {
		this.widget_width = widget_width;
	}

	public long getWidget_height() {
		return widget_height;
	}

	public void setWidget_height(long widget_height) {
		this.widget_height = widget_height;
	}

	/*
	 * public long getWidget_x() { return widget_x; }
	 * 
	 * public void setWidget_x(long widget_x) { this.widget_x = widget_x; }
	 * 
	 * public long getWidget_y() { return widget_y; }
	 * 
	 * public void setWidget_y(long widget_y) { this.widget_y = widget_y; }
	 */

	public long getWidget_landscape_x() {
		return widget_landscape_x;
	}

	public void setWidget_landscape_x(long widget_landscape_x) {
		this.widget_landscape_x = widget_landscape_x;
	}

	public long getWidget_landscape_y() {
		return widget_landscape_y;
	}

	public void setWidget_landscape_y(long widget_landscape_y) {
		this.widget_landscape_y = widget_landscape_y;
	}

	public long getWidget_landscape_width() {
		return widget_landscape_width;
	}

	public void setWidget_landscape_width(long widget_landscape_width) {
		this.widget_landscape_width = widget_landscape_width;
	}

	public long getWidget_landscape_height() {
		return widget_landscape_height;
	}

	public void setWidget_landscape_height(long widget_landscape_height) {
		this.widget_landscape_height = widget_landscape_height;
	}

	public long getWidget_portrait_x() {
		return widget_portrait_x;
	}

	public void setWidget_portrait_x(long widget_portrait_x) {
		this.widget_portrait_x = widget_portrait_x;
	}

	public long getWidget_portrait_y() {
		return widget_portrait_y;
	}

	public void setWidget_portrait_y(long widget_portrait_y) {
		this.widget_portrait_y = widget_portrait_y;
	}

	public long getWidget_portrait_width() {
		return widget_portrait_width;
	}

	public void setWidget_portrait_width(long widget_portrait_width) {
		this.widget_portrait_width = widget_portrait_width;
	}

	public long getWidget_portrait_height() {
		return widget_portrait_height;
	}

	public void setWidget_portrait_height(long widget_portrait_height) {
		this.widget_portrait_height = widget_portrait_height;
	}

	public String getWidget_data_binding_context() {
		return widget_data_binding_context;
	}

	public void setWidget_data_binding_context(
			String widget_data_binding_context) {
		this.widget_data_binding_context = widget_data_binding_context;
	}

	public long getWidget_noun_id() {
		return widget_noun_id;
	}

	public void setWidget_noun_id(long widget_noun_id) {
		this.widget_noun_id = widget_noun_id;
	}

	public long getWidget_noun_attribute_id() {
		return widget_noun_attribute_id;
	}

	public void setWidget_noun_attribute_id(long widget_noun_attribute_id) {
		this.widget_noun_attribute_id = widget_noun_attribute_id;
	}

	public String getWidget_verb_binding_context() {
		return widget_verb_binding_context;
	}

	public void setWidget_verb_binding_context(
			String widget_verb_binding_context) {
		this.widget_verb_binding_context = widget_verb_binding_context;
	}

	public String getWidget_verb_target() {
		return widget_verb_target;
	}

	public void setWidget_verb_target(String widget_verb_target) {
		this.widget_verb_target = widget_verb_target;
	}

	public long getWidget_number_of_children() {
		return widget_number_of_children;
	}

	public void setWidget_number_of_children(long widget_number_of_children) {
		this.widget_number_of_children = widget_number_of_children;
	}

	public String getWidget_extended_attributes() {
		return widget_extended_attributes;
	}

	public void setWidget_extended_attributes(String widget_extended_attributes) {
		this.widget_extended_attributes = widget_extended_attributes;
	}

	public String getWidget_event_verb_combo() {
		return widget_event_verb_combo;
	}

	public void setWidget_event_verb_combo(String widget_event_verb_combo) {
		this.widget_event_verb_combo = widget_event_verb_combo;
	}

	public String getWidget_notes() {
		return widget_notes;
	}

	public void setWidget_notes(String widget_notes) {
		this.widget_notes = widget_notes;
	}

	public Date getWidget_createdate() {
		return widget_createdate;
	}

	public void setWidget_createdate(Date widget_createdate) {
		this.widget_createdate = widget_createdate;
	}

	public long getWidget_createdby() {
		return widget_createdby;
	}

	public void setWidget_createdby(long widget_createdby) {
		this.widget_createdby = widget_createdby;
	}

	public Date getWidget_lastmodifieddate() {
		return widget_lastmodifieddate;
	}

	public void setWidget_lastmodifieddate(Date widget_lastmodifieddate) {
		this.widget_lastmodifieddate = widget_lastmodifieddate;
	}

	public long getWidget_lastmodifiedby() {
		return widget_lastmodifiedby;
	}

	public void setWidget_lastmodifiedby(long widget_lastmodifiedby) {
		this.widget_lastmodifiedby = widget_lastmodifiedby;
	}

	public String getWidget_ui_technology() {
		return widget_ui_technology;
	}

	public void setWidget_ui_technology(String widget_ui_technology) {
		this.widget_ui_technology = widget_ui_technology;
	}

	public String getScreen_is_orientation_locked() {
		return screen_is_orientation_locked;
	}

	public String getWidget_events() {
		return widget_events;
	}

	public void setWidget_events(String widget_events) {
		this.widget_events = widget_events;
	}

	public long getWidget_parent_id() {
		return widget_parent_id;
	}

	public void setWidget_parent_id(long widget_parent_id) {
		this.widget_parent_id = widget_parent_id;
	}

	public Boolean getWidget_is_container() {
		return widget_is_container;
	}

	public void setWidget_is_container(Boolean widget_is_container) {
		this.widget_is_container = widget_is_container;
	}

	public long getWidget_portraitOffsetX() {
		return widget_portraitOffsetX;
	}

	public void setWidget_portraitOffsetX(long widget_portraitOffsetX) {
		this.widget_portraitOffsetX = widget_portraitOffsetX;
	}

	public long getWidget_portraitOffsetY() {
		return widget_portraitOffsetY;
	}

	public void setWidget_portraitOffsetY(long widget_portraitOffsetY) {
		this.widget_portraitOffsetY = widget_portraitOffsetY;
	}

	public long getWidget_landscapeOffsetX() {
		return widget_landscapeOffsetX;
	}

	public void setWidget_landscapeOffsetX(long widget_landscapeOffsetX) {
		this.widget_landscapeOffsetX = widget_landscapeOffsetX;
	}

	public long getWidget_landscapeOffsetY() {
		return widget_landscapeOffsetY;
	}

	public void setWidget_landscapeOffsetY(long widget_landscapeOffsetY) {
		this.widget_landscapeOffsetY = widget_landscapeOffsetY;
	}

	public long getWidget_screen_id() {
		return widget_screen_id;
	}

	public void setWidget_screen_id(long widget_screen_id) {
		this.widget_screen_id = widget_screen_id;
	}

	public String getWidget_css_class() {
		return widget_css_class;
	}

	public void setWidget_css_class(String widget_css_class) {
		this.widget_css_class = widget_css_class;
	}

	public long getWidget_rows() {
		return widget_rows;
	}

	public void setWidget_rows(long widget_rows) {
		this.widget_rows = widget_rows;
	}

	/*
	 * public long getWidget_columns() { return widget_columns; }
	 * 
	 * public void setWidget_columns(long widget_columns) { this.widget_columns
	 * = widget_columns; }
	 */
	public String getWidget_target_url() {
		return widget_target_url;
	}

	public void setWidget_target_url(String widget_target_url) {
		this.widget_target_url = widget_target_url;
	}

	public String getWidget_header() {
		return widget_header;
	}

	public void setWidget_header(String widget_header) {
		this.widget_header = widget_header;
	}

	public String getWidget_footer() {
		return widget_footer;
	}

	public void setWidget_footer(String widget_footer) {
		this.widget_footer = widget_footer;
	}

	public String getWidget_image_src() {
		return widget_image_src;
	}

	public void setWidget_image_src(String widget_image_src) {
		this.widget_image_src = widget_image_src;
	}

	public String getWidget_extra_verb_info() {
		return widget_extra_verb_info;
	}

	public void setWidget_extra_verb_info(String widget_extra_verb_info) {
		this.widget_extra_verb_info = widget_extra_verb_info;
	}

	public long getScreen_wizard_id() {
		return screen_wizard_id;
	}

	public void setScreen_wizard_id(long screen_wizard_id) {
		this.screen_wizard_id = screen_wizard_id;
	}

	public long getScreen_wizard_sequence_id() {
		return screen_wizard_sequence_id;
	}

	public void setScreen_wizard_sequence_id(long screen_wizard_sequence_id) {
		this.screen_wizard_sequence_id = screen_wizard_sequence_id;
	}

	public String getScreen_role() {
		return screen_role;
	}

	public void setScreen_role(String screen_role) {
		this.screen_role = screen_role;
	}

	public long getWidget_data_binding_target_noun_id() {
		return widget_data_binding_target_noun_id;
	}

	public void setWidget_data_binding_target_noun_id(
			long widget_data_binding_target_noun_id) {
		this.widget_data_binding_target_noun_id = widget_data_binding_target_noun_id;
	}

	public long getWidget_data_binding_target_noun_attr_id() {
		return widget_data_binding_target_noun_attr_id;
	}

	public void setWidget_data_binding_target_noun_attr_id(
			long widget_data_binding_target_noun_attr_id) {
		this.widget_data_binding_target_noun_attr_id = widget_data_binding_target_noun_attr_id;
	}

	public long getWidget_section_position() {
		return widget_section_position;
	}

	public void setWidget_section_position(long widget_section_position) {
		this.widget_section_position = widget_section_position;
	}

	public String getGroup_values() {
		return group_values;
	}

	public void setGroup_values(String group_values) {
		this.group_values = group_values;
	}
	
	

	/* WIDGETS ATTRIBUTES END */

}
