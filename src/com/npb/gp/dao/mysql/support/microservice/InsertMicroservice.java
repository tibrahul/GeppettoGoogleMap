package com.npb.gp.dao.mysql.support.microservice;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertMicroservice extends SqlUpdate {
	public static String SQL_INSERT_MICROSERVICE = "";

	public InsertMicroservice(DataSource dataSource) {
		super(dataSource, SQL_INSERT_MICROSERVICE);

		super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("microservice_name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("activities_json", Types.VARCHAR));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}
}
