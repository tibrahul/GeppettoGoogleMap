package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertNewUserWithAdmin  extends SqlUpdate {

	public static String SQL_INSERT_NEW_USER_WITH_ADMIN = "";

	public InsertNewUserWithAdmin(DataSource dataSource) {
		super(dataSource, SQL_INSERT_NEW_USER_WITH_ADMIN);

		super.declareParameter(new SqlParameter("adminid", Types.INTEGER));
		super.declareParameter(new SqlParameter("newuserid", Types.INTEGER));
		super.declareParameter(new SqlParameter("created_on", Types.TIMESTAMP));
		// Added the processed column for Indentfied new user 
//		super.declareParameter(new SqlParameter("processed", Types.VARCHAR));
		super.setReturnGeneratedKeys(true);
	}

}
