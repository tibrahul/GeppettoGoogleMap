package com.npb.gp.dao.mysql.support.templatecomponent;

/**
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0</p>
 *
 *        Update class supporting the GpTemplateComponentDao class</p>
 * 
 * */
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateGpTemplateComponentMapper extends SqlUpdate {

	public static String SQL_UPDATE_PROJECT = "";

	public UpdateGpTemplateComponentMapper(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_PROJECT);

		super.declareParameter(new SqlParameter("template_component_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("template_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("template_section",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("template_component_value",
				Types.VARCHAR));
	}
}
