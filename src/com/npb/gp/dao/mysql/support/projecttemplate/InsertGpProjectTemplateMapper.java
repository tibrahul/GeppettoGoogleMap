package com.npb.gp.dao.mysql.support.projecttemplate;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0</p>
 *
 *        Insert class supporting the GpProjectTemplateDao class</p>
 *
 */

public class InsertGpProjectTemplateMapper extends SqlUpdate {

	public static String SQL_INSERT_PROJECT = "";

	public InsertGpProjectTemplateMapper(DataSource dataSource) {
		super(dataSource, SQL_INSERT_PROJECT);

		super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("template_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("color", Types.VARCHAR));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("device", Types.VARCHAR));
		
		super.setGeneratedKeysColumnNames(new String[] { "project_template_id" });
		super.setReturnGeneratedKeys(true);
	}
}
