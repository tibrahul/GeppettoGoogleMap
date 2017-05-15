package com.npb.gp.dao.mysql.support.activity;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;


public class InsertGroup extends SqlUpdate {

	public static String SQL_INSERT_GROUP = "";

	public InsertGroup(DataSource dataSource) {
		super(dataSource, SQL_INSERT_GROUP);
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("screenId", Types.BIGINT));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("data_binding_context", Types.VARCHAR));
		super.declareParameter(new SqlParameter("noun_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("noun_attribute_id", Types.BIGINT));
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);

	}

}
