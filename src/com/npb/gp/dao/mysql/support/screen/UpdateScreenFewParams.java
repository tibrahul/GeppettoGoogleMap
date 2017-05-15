package com.npb.gp.dao.mysql.support.screen;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 26/03/2015</br>
 * @since .75</p>
 *
 *        Update class supporting the GpScreenDao class</p>
 *
 */

public class UpdateScreenFewParams extends SqlUpdate {

	public static String SQL_UPDATE_SCREEN = "";

	public UpdateScreenFewParams(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_SCREEN);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("activityid", Types.BIGINT));
	}
}
