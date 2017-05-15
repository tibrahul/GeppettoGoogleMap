package com.npb.gp.dao.mysql.support.template;

/**
 * @author Dheeraj Singh</br> Date Created: 12/29/2015</br>
 * @since 1.0</p>
 *
 *        Update class supporting the GpTemplateDao class</p>
 * 
 * */
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateGpTemplateMapper extends SqlUpdate {

	public static String SQL_UPDATE_PROJECT = "";

	public UpdateGpTemplateMapper(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_PROJECT);

		super.declareParameter(new SqlParameter("template_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("color", Types.VARCHAR));
		super.declareParameter(new SqlParameter("device", Types.VARCHAR));
	}
}
