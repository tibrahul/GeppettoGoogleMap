package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateInstallrNameInNewUserTbl 
	extends SqlUpdate {
		public static String SQL_UPDATE_INSTALLR_NAME_IN_USER_TABLE = "";

		public UpdateInstallrNameInNewUserTbl(DataSource dataSource) {
			super(dataSource, SQL_UPDATE_INSTALLR_NAME_IN_USER_TABLE);
			super.declareParameter(new SqlParameter("user_id", Types.BIGINT));
			super.declareParameter(new SqlParameter("installr_name", Types.VARCHAR));
			super.setReturnGeneratedKeys(true);
		}

	}

