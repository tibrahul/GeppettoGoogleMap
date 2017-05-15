package com.npb.gp.dao.mysql.support.templatecomponent;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpProjectTemplateComponent;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0</p>
 *
 *        The purpose of this class is to support the GpTemplateComponentDao by
 *        providing mapping</p>
 * 
 */
public class SelectGpTemplateComponentMapper implements
		RowMapper<GpProjectTemplateComponent> {

	public GpProjectTemplateComponent mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpProjectTemplateComponent wrapper = new GpProjectTemplateComponent();

		wrapper.setTemplateComponentId(rs.getLong("TEMPLATE_COMPONENT_ID"));
		wrapper.setName(rs.getString("NAME"));
		wrapper.setDescription(rs.getString("DESCRIPTION"));
		wrapper.setLabel(rs.getString("LABEL"));
		wrapper.setTemplateId(rs.getInt("TEMPLATE_ID"));
		wrapper.setTemplateSection(rs.getString("TEMPLATE_SECTION"));
		wrapper.setTemplateComponentValue(rs
				.getString("TEMPLATE_COMPONENT_VALUE"));
		wrapper.setElementType(rs.getString("ELEMENT_TYPE"));

		return wrapper;
	}
}
