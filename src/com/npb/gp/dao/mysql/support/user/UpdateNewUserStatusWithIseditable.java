package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateNewUserStatusWithIseditable  extends SqlUpdate {
	
	
	public static String SQL_UPDATE_NEW_USER_STATUS_LOCK_UNLOCK = "";

	public UpdateNewUserStatusWithIseditable(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_NEW_USER_STATUS_LOCK_UNLOCK);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("lockorunlock", Types.VARCHAR));
		super.declareParameter(new SqlParameter("isEditable", Types.VARCHAR));
		super.declareParameter(new SqlParameter("adminid", Types.INTEGER));
		
		
	}

}
