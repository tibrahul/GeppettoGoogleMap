package com.npb.gp.dao.mysql.support.activity;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;


public class InsertWizard extends SqlUpdate {

	public static String SQL_INSERT_WIZARD = "";

	public InsertWizard(DataSource dataSource) {
		super(dataSource, SQL_INSERT_WIZARD);
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("activityId", Types.BIGINT));
		super.declareParameter(new SqlParameter("screenIds", Types.VARCHAR));
		super.declareParameter(new SqlParameter("wizard_type", Types.VARCHAR));
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);

	}

}
