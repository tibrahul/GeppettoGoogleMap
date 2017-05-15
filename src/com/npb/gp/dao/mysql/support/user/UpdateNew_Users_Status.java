package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateNew_Users_Status extends SqlUpdate {

	public static String SQL_UPDATE_NEW_USER_STATUS = "";

	public UpdateNew_Users_Status(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_NEW_USER_STATUS);
		super.declareParameter(new SqlParameter("user_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		/*super.declareParameter(new SqlParameter("installr_user_config_id", Types.BIGINT));
		
		super.setGeneratedKeysColumnNames(new String[] { "userid" });*/
		super.setReturnGeneratedKeys(true);
	}
	
}
