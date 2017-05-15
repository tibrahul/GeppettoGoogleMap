package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InstallrInsertUserInfo extends SqlUpdate   {

	public static String SQL_INSERT_INSTALLR_USER_INFO = "";

	public InstallrInsertUserInfo(DataSource dataSource) {
		super(dataSource, SQL_INSERT_INSTALLR_USER_INFO);
		super.declareParameter(new SqlParameter("id",Types.INTEGER));
		super.declareParameter(new SqlParameter("installr_user_name",Types.VARCHAR ));
		super.declareParameter(new SqlParameter("installr_password",Types.VARCHAR ));
		super.declareParameter(new SqlParameter("installrToken",Types.VARCHAR));
		super.setReturnGeneratedKeys(true);
	}
	
}
