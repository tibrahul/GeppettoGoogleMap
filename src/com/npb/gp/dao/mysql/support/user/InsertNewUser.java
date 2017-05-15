package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Rashmi
 * 
 *
 *        Insert class supporting the GpNewUsersDao class</p>
 *
 *
 */

public class InsertNewUser extends SqlUpdate {

	public static String SQL_INSERT_NEW_USER = "";
	
	public InsertNewUser(){
		
	}

	public InsertNewUser(DataSource dataSource) {
		super(dataSource, SQL_INSERT_NEW_USER);

		super.declareParameter(new SqlParameter("userid", Types.INTEGER));
		super.declareParameter(new SqlParameter("username", Types.VARCHAR));
		super.declareParameter(new SqlParameter("createdon", Types.TIMESTAMP));
		// Added the processed column for Indentfied new user 
		super.declareParameter(new SqlParameter("processed", Types.VARCHAR));
		super.setReturnGeneratedKeys(true);
	}

}
