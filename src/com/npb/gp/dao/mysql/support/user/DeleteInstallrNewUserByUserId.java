package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class DeleteInstallrNewUserByUserId extends SqlUpdate {

	public static String SQL_DELETE_NEW_USER_DATA_BYUSER_ID = "";

	public DeleteInstallrNewUserByUserId(DataSource dataSource) {
		super(dataSource, SQL_DELETE_NEW_USER_DATA_BYUSER_ID);

		super.declareParameter(new SqlParameter("user_id", Types.INTEGER));

	}

}