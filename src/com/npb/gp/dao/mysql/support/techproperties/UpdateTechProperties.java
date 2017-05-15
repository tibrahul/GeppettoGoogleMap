package com.npb.gp.dao.mysql.support.techproperties;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateTechProperties extends SqlUpdate {

	public static String SQL_UPDATE_TECHPROPERTIES = "";

	public UpdateTechProperties(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_TECHPROPERTIES);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("version", Types.VARCHAR));
		super.declareParameter(new SqlParameter("release_status", Types.VARCHAR));
		super.declareParameter(new SqlParameter("license_status", Types.VARCHAR));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("default_value", Types.VARCHAR));
	}
}
