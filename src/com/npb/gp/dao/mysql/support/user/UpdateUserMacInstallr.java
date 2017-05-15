package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateUserMacInstallr  extends SqlUpdate {

	public static String SQL_UPDATE_USER_MAC_INSTALLR = "";

	public UpdateUserMacInstallr(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_USER_MAC_INSTALLR);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("mac_config_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("installr_user_config_id", Types.BIGINT));
		
		super.setGeneratedKeysColumnNames(new String[] { "userid" });
		super.setReturnGeneratedKeys(true);
	}
	
}
