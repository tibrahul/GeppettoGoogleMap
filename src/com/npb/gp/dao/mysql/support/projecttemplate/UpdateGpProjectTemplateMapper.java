package com.npb.gp.dao.mysql.support.projecttemplate;

/**
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0</p>
 *
 *        Update class supporting the GpProjectTemplateDao class</p>
 * 
 * */
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateGpProjectTemplateMapper extends SqlUpdate {

	public static String SQL_UPDATE_PROJECT = "";

	public UpdateGpProjectTemplateMapper(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_PROJECT);

		super.declareParameter(new SqlParameter("project_template_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("template_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("color", Types.VARCHAR));
	}
}
