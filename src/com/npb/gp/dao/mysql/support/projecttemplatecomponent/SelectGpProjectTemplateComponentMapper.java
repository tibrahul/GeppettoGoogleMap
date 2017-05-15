package com.npb.gp.dao.mysql.support.projecttemplatecomponent;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpProjectTemplateComponent;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/31/2015</br>
 * @since 1.0</p>
 *
 *        The purpose of this class is to support the
 *        GpProjectTemplateComponentDao by providing mapping</p>
 * 
 */
public class SelectGpProjectTemplateComponentMapper implements
		RowMapper<GpProjectTemplateComponent> {

	public GpProjectTemplateComponent mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpProjectTemplateComponent wrapper = new GpProjectTemplateComponent();

		wrapper.setProjectTemplateComponentId(rs
				.getLong("PROJECT_TEMPLATE_COMPONENT_ID"));
		wrapper.setProjectId(rs.getLong("PROJECT_ID"));
		wrapper.setProjectTemplateId(rs.getLong("PROJECT_TEMPLATE_ID"));
		wrapper.setTemplateComponentId(rs.getLong("TEMPLATE_COMPONENT_ID"));
		wrapper.setTemplateComponentValue(rs
				.getString("TEMPLATE_COMPONENT_VALUE"));

		return wrapper;
	}
}
