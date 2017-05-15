package com.npb.gp.dao.mysql.support.techproperties;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertTechProperties extends SqlUpdate {

	public static String SQL_INSERT_TECHPROPERTIES = "";

	public InsertTechProperties(DataSource dataSource) {
		super(dataSource, SQL_INSERT_TECHPROPERTIES);

		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("version", Types.VARCHAR));
		super.declareParameter(new SqlParameter("release_status", Types.VARCHAR));
		super.declareParameter(new SqlParameter("license_status", Types.VARCHAR));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("default_value", Types.VARCHAR));
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);

	}

}