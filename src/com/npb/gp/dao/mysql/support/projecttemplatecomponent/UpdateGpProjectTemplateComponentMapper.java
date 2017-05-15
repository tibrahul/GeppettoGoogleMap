package com.npb.gp.dao.mysql.support.projecttemplatecomponent;

/**
 * @author Dheeraj Singh</br> Date Created: 12/31/2015</br>
 * @since 1.0</p>
 *
 *        Update class supporting the GpProjectTemplateComponentDao class</p>
 * 
 * */
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateGpProjectTemplateComponentMapper extends SqlUpdate {

	public static String SQL_UPDATE_PROJECT = "";

	public UpdateGpProjectTemplateComponentMapper(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_PROJECT);

		super.declareParameter(new SqlParameter(
				"project_template_component_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("project_template_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("template_component_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("template_component_value",
				Types.VARCHAR));
	}
}
