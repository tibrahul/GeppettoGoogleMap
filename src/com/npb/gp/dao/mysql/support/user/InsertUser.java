package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dan Castillo</br> Date Created: 04/15/2014</br>
 * @since .35</p>
 *
 *        Insert class supporting the GpUserDao class</p>
 *
 *
 */

public class InsertUser extends SqlUpdate {

	public static String SQL_INSERT_USER = "";

	public InsertUser(DataSource dataSource) {
		super(dataSource, SQL_INSERT_USER);

		super.declareParameter(new SqlParameter("username", Types.VARCHAR));
		super.declareParameter(new SqlParameter("password", Types.VARCHAR));
		super.declareParameter(new SqlParameter("startdate", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("languagepreference", Types.VARCHAR));
		super.declareParameter(new SqlParameter("licenseid", Types.VARCHAR));
		super.declareParameter(new SqlParameter("mustresetpassword", Types.VARCHAR));
		super.declareParameter(new SqlParameter("accesstype", Types.VARCHAR));
		super.declareParameter(new SqlParameter("lastaccess", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("newuser", Types.BOOLEAN));
		
		super.declareParameter(new SqlParameter("mac_config_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("installr_user_config_id", Types.BIGINT));
		
				super.setGeneratedKeysColumnNames(new String[] { "userid" });
		super.setReturnGeneratedKeys(true);
	}

}
