package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateUser extends SqlUpdate {
	
	
	public static String SQL_UPDATE_USER = "";

	public UpdateUser(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_USER);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("docker_json", Types.VARCHAR));
		
	}

}
