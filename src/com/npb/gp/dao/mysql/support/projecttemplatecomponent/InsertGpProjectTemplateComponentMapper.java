package com.npb.gp.dao.mysql.support.projecttemplatecomponent;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/31/2015</br>
 * @since 1.0</p>
 *
 *        Insert class supporting the GpProjectTemplateComponentDao class</p>
 *
 */

public class InsertGpProjectTemplateComponentMapper extends SqlUpdate {

	public static String SQL_INSERT_PROJECT = "";

	public InsertGpProjectTemplateComponentMapper(DataSource dataSource) {
		super(dataSource, SQL_INSERT_PROJECT);

		super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("project_template_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("template_component_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("template_component_value",
				Types.VARCHAR));

		super.setGeneratedKeysColumnNames(new String[] { "project_template_component_id" });
		super.setReturnGeneratedKeys(true);
	}
}
