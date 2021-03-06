package com.npb.gp.dao.mysql.support.activity;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateGpMicroService extends SqlUpdate {

	public static String SQL_UPDATE_WIZARD = "";

	public UpdateGpMicroService(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_WIZARD);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("activities_json", Types.VARCHAR));
	}
}
