package com.npb.gp.dao.mysql.support.screen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author
 * 
 * 		Modified Date: 09/10/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Added new variables as image_src to save the image sources
 * 
 *         Modified Date: 10/09/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Added new variables as header and footer for the component as card in
 *         under the widgets
 * 
 *         Modified Date: 04/09/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Added new variable as target_url for the component as link in under
 *         the widgets
 * 
 *         Modified Date: 05/08/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Removed the variable as columns
 * 
 *         Modified Date: 01/07/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Added the new variables as rows and columns
 * 
 *         Modified Date: 19/06/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Removed the variables X and Y
 * 
 *         Modified Date: 04/06/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Added new variable as css_class
 * 
 *         Modified Date: 26/05/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Changed the is_container to Boolean and removed parent variable
 * 
 *         Added new variables as portraitOffsetX, portraitOffsetY,
 *         landscapeOffsetX, landscapeOffsetY and etc.
 * 
 *         Modified Date: 12/05/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         Changed the parent to String and added new variable as parent_id
 * 
 *         Modified Date: 04/05/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         <p>
 *         Added the new variable as WIDGET_UI_TECHNOLOGY
 *         </p>
 * 
 *         Modified Date: 07/04/2015</br>
 *         Modified By: Suresh Palanisamy
 *         </p>
 * 
 *         <p>
 *         Modified the setScreen_is_orientation_locked and
 *         setWidget_is_container result set to String
 *         </p>
 *
 */

public class Screen_with_widget_mapper implements RowMapper<GpDto_screen_and_widgets> {

	@Override
	public GpDto_screen_and_widgets mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpDto_screen_and_widgets dto = new GpDto_screen_and_widgets();

		/* SCREEN ATTRIBUTES START */
		dto.setScreen_id(rs.getLong("SCREEN_ID"));
		dto.setScreen_name(rs.getString("SCREEN_NAME"));
		dto.setScreen_label(rs.getString("SCREEEN_LABEL"));
		dto.setScreen_role(rs.getString("SCREEN_ROLE"));
		dto.setScreen_description(rs.getString("SCREEN_DESCRIPTION"));
		dto.setScreen_activity_id(rs.getLong("SCREEN_ACTIVITY_ID"));
		dto.setScreen_projectid(rs.getLong("SCREEN_PROJECTID"));
		dto.setScreen_client_device_type(rs.getString("SCREEN_CLIENT_DEVICE_TYPE"));
		dto.setScreen_human_language_id(rs.getLong("SCREEN_HUMAN_LANGUAGE_ID"));
		dto.setScreen_type(rs.getString("SCREEN_TYPE"));
		dto.setScreen_client_device_type_id(rs.getLong("SCREEN_CLIENT_DEVICE_TYPE_ID"));
		dto.setScreen_current_orientation(rs.getString("SCREEN_CURRENT_ORIENTATION"));
		dto.setScreen_is_orientation_locked(rs.getString("SCREEN_IS_ORIENTATION_LOCKED"));
		dto.setScreen_client_device_type_label(rs.getString("SCREEN_CLIENT_DEVICE_TYPE_LABEL"));
		dto.setScreen_notes(rs.getString("SCREEN_NOTES"));
		dto.setScreen_createdate(rs.getTimestamp("SCREEN_CREATED_DATE"));
		dto.setScreen_createdby(rs.getLong("SCREEN_CREATED_BY"));
		dto.setScreen_lastmodifieddate(rs.getTimestamp("SCREEN_LAST_MODIFIED_DATE"));
		dto.setScreen_lastmodifiedby(rs.getLong("SCREEN_LAST_MODIFIED_BY"));
		dto.setScreen_landscape_image_name(rs.getString("SCREEN_LANDSCAPE_IMAGE_NAME"));
		dto.setScreen_portrait_image_name(rs.getString("SCREEN_PORTRAIT_IMAGE_NAME"));
		dto.setScreen_primary_noun_id(rs.getLong("SCREEN_PRIMARY_NOUN_ID"));
		dto.setScreen_secondary_noun_ids(rs.getString("SCREEN_SECONDARY_IDS"));
		dto.setScreen_client_device_type_os_name(rs.getString("SCREEN_CLIENT_DEVICE_TYPE_OS_NAME"));
		dto.setScreen_client_device_type_name(rs.getString("SCREEN_CLIENT_DEVICE_TYPE_NAME"));
		dto.setScreen_client_device_type_description(rs.getString("SCREEN_CLIENT_DEVICE_TYPE_DESCRIPTION"));
		dto.setScreen_client_device_screen_size(rs.getString("SCREEN_CLIENT_DEVICE_SCREEN_SIZE"));
		dto.setScreen_client_device_resolution(rs.getString("SCREEN_CLIENT_DEVICE_RESOLUTION"));
		dto.setScreen_client_device_ppcm(rs.getString("SCREEN_CLIENT_DEVICE_PPCM"));
		dto.setScreen_client_device_type_os_version(rs.getString("SCREEN_CLIENT_DEVICE_TYPE_OS_VERSION"));
		dto.setScreen_client_language_type(rs.getString("SCREEN_CLIENT_LANGUAGE_TYPE"));
		dto.setScreen_client_library_type(rs.getString("SCREEN_CLIENT_LIBRARY_TYPE"));
		dto.setScreen_height(rs.getLong("SCREEN_HEIGHT"));
		dto.setScreen_width(rs.getLong("SCREEN_WIDTH"));
		dto.setScreen_event_verb_combo(rs.getString("SCREEN_EVENT_VERB_COMBO"));
		dto.setScreen_wizard_id(rs.getLong("SCREEN_WIZARD_ID"));
		dto.setScreen_wizard_sequence_id(rs.getLong("SCREEN_WIZARD_SEQUENCE_ID"));

		/* SCREEN ATTRIBUTES END */

		/* WIDGETS ATTRIBUTES START */

		dto.setWidget_id(rs.getLong("WIDGET_ID"));
		dto.setWidget_name(rs.getString("WIDGET_NAME"));
		dto.setWidget_label(rs.getString("WIDGET_LABEL"));
		dto.setWidget_description(rs.getString("WIDGET_DESCRIPTION"));
		dto.setWidget_parent_id(rs.getLong("WIDGET_PARENT_ID"));
		dto.setWidget_is_container(rs.getBoolean("WIDGET_IS_CONTAINER"));
		dto.setWidget_supports_label(rs.getString("WIDGET_SUPPORTS_LABEL"));
		dto.setWidget_ui_technology(rs.getString("WIDGET_UI_TECHNOLOGY"));
		dto.setWidget_width(rs.getLong("WIDGET_WIDTH"));
		dto.setWidget_height(rs.getLong("WIDGET_HEIGHT"));
		// dto.setWidget_x(rs.getLong("WIDGET_X"));
		// dto.setWidget_y(rs.getLong("WIDGET_Y"));
		dto.setWidget_notes(rs.getString("WIDGET_NOTES"));
		dto.setWidget_data_binding_context(rs.getString("WIDGET_DATA_BINDING_CONTEXT"));
		dto.setWidget_verb_binding_context(rs.getString("WIDGET_VERB_BINDING_CONTEXT"));
		dto.setWidget_noun_id(rs.getLong("WIDGET_NOUN_ID"));
		dto.setWidget_noun_attribute_id(rs.getLong("WIDGET_NOUN_ATTRIBUTE_ID"));
		dto.setWidget_type(rs.getString("WIDGET_TYPE"));
		dto.setWidget_parent_name(rs.getString("WIDGET_PARENT_NAME"));
		dto.setWidget_number_of_children(rs.getLong("WIDGET_NUMBER_OF_CHILDREN"));
		dto.setWidget_extended_attributes(rs.getString("WIDGET_EXTENDED_ATTRIBUTES"));
		dto.setWidget_event_verb_combo(rs.getString("WIDGET_EVENT_VERB_COMBO"));
		dto.setWidget_verb_target(rs.getString("WIDGET_VERB_TARGET"));
		dto.setWidget_portrait_x(rs.getLong("WIDGET_PORTRAIT_X"));
		dto.setWidget_portrait_y(rs.getLong("WIDGET_PORTRAIT_Y"));
		dto.setWidget_portrait_width(rs.getLong("WIDGET_PORTRAIT_WIDTH"));
		dto.setWidget_portrait_height(rs.getLong("WIDGET_PORTRAIT_HEIGHT"));
		dto.setWidget_landscape_x(rs.getLong("WIDGET_LANDSCAPE_X"));
		dto.setWidget_landscape_y(rs.getLong("WIDGET_LANDSCAPE_Y"));
		dto.setWidget_landscape_width(rs.getLong("WIDGET_LANDSCAPE_WIDTH"));
		dto.setWidget_landscape_height(rs.getLong("WIDGET_LANDSCAPE_HEIGHT"));
		dto.setWidget_createdate(rs.getTimestamp("WIDGET_CREATED_DATE"));
		dto.setWidget_createdby(rs.getLong("WIDGET_CREATED_BY"));
		dto.setWidget_lastmodifieddate(rs.getTimestamp("WIDGET_LAST_MODIFIED_DATE"));
		dto.setWidget_lastmodifiedby(rs.getLong("WIDGET_LAST_MODIFIED_BY"));
		dto.setWidget_screen_id(rs.getLong("WIDGET_SCREEN_ID"));
		dto.setWidget_events(rs.getString("WIDGET_EVENTS"));
		dto.setWidget_portraitOffsetX(rs.getLong("WIDGET_PORTRAIT_OFFSET_X"));
		dto.setWidget_portraitOffsetY(rs.getLong("WIDGET_PORTRAIT_OFFSET_Y"));
		dto.setWidget_landscapeOffsetX(rs.getLong("WIDGET_LANDSCAPE_OFFSET_X"));
		dto.setWidget_landscapeOffsetY(rs.getLong("WIDGET_LANDSCAPE_OFFSET_Y"));
		dto.setWidget_css_class(rs.getString("WIDGET_CSS_CLASS"));
		dto.setWidget_rows(rs.getLong("WIDGET_ROWS"));
		// dto.setWidget_columns(rs.getLong("WIDGET_COLUMNS"));
		dto.setWidget_target_url(rs.getString("WIDGET_TARGET_URL"));
		dto.setWidget_header(rs.getString("WIDGET_HEADER"));
		dto.setWidget_footer(rs.getString("WIDGET_FOOTER"));
		dto.setWidget_image_src(rs.getString("WIDGET_IMAGE_SRC"));
		dto.setWidget_extra_verb_info(rs.getString("WIDGET_EXTRA_VERB_INFO"));
		dto.setWidget_data_binding_target_noun_id(rs.getLong("WIDGET_DATA_BINDING_TARGET_NOUN_ID"));
		dto.setWidget_data_binding_target_noun_attr_id(rs.getLong("WIDGET_DATA_BINDING_TARGET_NOUN_ATTR_ID"));
		dto.setWidget_section_position(rs.getLong("WIDGET_SECTION_POSITION"));
		dto.setGroup_values(rs.getString("WIDGET_GROUP_VALUES"));

		/* WIDGETS ATTRIBUTES END */

		return dto;
	}
}
