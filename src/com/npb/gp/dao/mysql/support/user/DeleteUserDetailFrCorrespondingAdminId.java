package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class DeleteUserDetailFrCorrespondingAdminId extends SqlUpdate {

	public static String SQL_DELETE_USERDETAILWITH_CORSPOND_ADMIN_ID = "";

	public DeleteUserDetailFrCorrespondingAdminId(DataSource dataSource) {
		super(dataSource, SQL_DELETE_USERDETAILWITH_CORSPOND_ADMIN_ID);

		super.declareParameter(new SqlParameter("newuserid", Types.INTEGER));
		super.declareParameter(new SqlParameter("adminid", Types.INTEGER));

	}

}