package com.npb.gp.dao.mysql.support.template;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/29/2015</br>
 * @since 1.0</p>
 *
 *        Insert class supporting the GpTemplateDao class</p>
 *
 */

public class InsertGpTemplateMapper extends SqlUpdate {

	public static String SQL_INSERT_PROJECT = "";

	public InsertGpTemplateMapper(DataSource dataSource) {
		super(dataSource, SQL_INSERT_PROJECT);

		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("color", Types.VARCHAR));

		super.setGeneratedKeysColumnNames(new String[] { "template_id" });
		super.setReturnGeneratedKeys(true);
	}
}
