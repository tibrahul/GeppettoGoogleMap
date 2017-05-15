package com.npb.gp.dao.mysql.support.activity;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateGpMicroServiceData extends SqlUpdate {

	public static String SQL_UPDATE_WIZARD_DATA = "";

	public UpdateGpMicroServiceData(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_WIZARD_DATA);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("microservice_name", Types.VARCHAR));
	}
}
