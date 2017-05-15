package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class DeleteUserDetailWithAdminData extends SqlUpdate {

	public static String SQL_DELETE_USERDETAILWITHADMIN_DATA = "";

	public DeleteUserDetailWithAdminData(DataSource dataSource) {
		super(dataSource, SQL_DELETE_USERDETAILWITHADMIN_DATA);

		super.declareParameter(new SqlParameter("id", Types.INTEGER));

	}

}