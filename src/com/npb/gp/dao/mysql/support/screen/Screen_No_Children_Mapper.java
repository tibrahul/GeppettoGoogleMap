package com.npb.gp.dao.mysql.support.screen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.domain.core.GpScreenX;

/**
 * 
 * @author
 * 
 *         Modified Date: 16/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *         <p>
 *         Modified the sec_noun_ids to arraylist
 *         </p>
 * 
 *         Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *         <p>
 *         Modified the setOrientation_locked result set to String
 * 
 *         And the sec_noun_ids to HashMap
 *         </p>
 */

public class Screen_No_Children_Mapper implements RowMapper<GpScreenX> {

	@Override
	public GpScreenX mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpScreenX screen_base_info = new GpScreenX();

		screen_base_info.setId(rs.getLong("SCREEN_ID"));
		screen_base_info.setName(rs.getString("SCREEN_NAME"));
		screen_base_info.setLabel(rs.getString("SCREEEN_LABEL"));
		screen_base_info.setDescription(rs.getString("SCREEN_DESCRIPTION"));
		screen_base_info.setActivity_id(rs.getLong("SCREEN_ACTIVITY_ID"));
		screen_base_info.setPrimary_noun_id(rs
				.getLong("SCREEN_PRIMARY_NOUN_ID"));
		screen_base_info.setProjectid(rs.getLong("SCREEN_PROJECTID"));

		String[] temp = GpGenericRecordParserBuilder.parseDelimitedString(rs
				.getString("SCREEN_SECONDARY_IDS"));

		ArrayList<String> sec_noun_ids = new ArrayList<String>();
		for (String str : temp) {
			sec_noun_ids.add(str);
		}
		screen_base_info.setSecondary_noun_ids(sec_noun_ids);

		screen_base_info.setType(rs.getString("SCREEN_TYPE"));
		screen_base_info.setClient_device_type_id(rs
				.getLong("SCREEN_CLIENT_DEVICE_TYPE_ID"));
		screen_base_info.setClient_device_type_label(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE_LABEL"));
		screen_base_info.setClient_device_type(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE"));
		screen_base_info.setClient_device_type_os_name(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE_OS_NAME"));
		screen_base_info.setHuman_language_id(rs
				.getLong("SCREEN_HUMAN_LANGUAGE_ID"));
		screen_base_info.setOrientation(rs
				.getString("SCREEN_CURRENT_ORIENTATION"));
		screen_base_info.setOrientation_locked(rs
				.getString("SCREEN_IS_ORIENTATION_LOCKED"));
		screen_base_info.setLandscape_image_name(rs
				.getString("SCREEN_LANDSCAPE_IMAGE_NAME"));
		screen_base_info.setPortrait_image_name(rs
				.getString("SCREEN_PORTRAIT_IMAGE_NAME"));
		screen_base_info.setCreatedby(rs.getLong("SCREEN_CREATED_BY"));
		screen_base_info.setCreatedate(rs.getTimestamp("SCREEN_CREATED_DATE"));
		screen_base_info.setLastmodifiedby(rs
				.getLong("SCREEN_LAST_MODIFIED_BY"));
		screen_base_info.setLastmodifieddate(rs
				.getTimestamp("SCREEN_LAST_MODIFIED_DATE"));
		screen_base_info.setEvent_verb_combo(rs.getString("SCREEN_EVENT_VERB_COMBO"));
		screen_base_info.setWizard_id(rs
				.getLong("SCREEN_WIZARD_ID"));
		screen_base_info.setScreen_wizard_sequence_id(rs
				.getLong("SCREEN_WIZARD_SEQUENCE_ID"));
		return screen_base_info;
	}
}
