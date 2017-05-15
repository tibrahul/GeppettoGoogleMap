package com.npb.gp.dao.mysql.support.templatecomponent;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0</p>
 *
 *        Insert class supporting the GpTemplateComponentDao class</p>
 *
 */

public class InsertGpTemplateComponentMapper extends SqlUpdate {

	public static String SQL_INSERT_PROJECT = "";

	public InsertGpTemplateComponentMapper(DataSource dataSource) {
		super(dataSource, SQL_INSERT_PROJECT);

		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("template_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("template_section",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("template_component_value",
				Types.VARCHAR));

		super.setGeneratedKeysColumnNames(new String[] { "template_component_id" });
		super.setReturnGeneratedKeys(true);
	}
}
