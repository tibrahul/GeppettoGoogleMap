package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertInstallrUser  extends SqlUpdate   {

	public static String SQL_INSERT_INSTALLR_USER = "";

	public InsertInstallrUser(DataSource dataSource) {
		super(dataSource, SQL_INSERT_INSTALLR_USER); 
		super.declareParameter(new SqlParameter("user_id",Types.BIGINT ));
		super.declareParameter(new SqlParameter("mac_config_id",Types.BIGINT ));
		super.declareParameter(new SqlParameter("createdDate",Types.DATE ));
		super.declareParameter(new SqlParameter("updatedDate",Types.DATE ));
		super.setReturnGeneratedKeys(true);
	}
	
}
