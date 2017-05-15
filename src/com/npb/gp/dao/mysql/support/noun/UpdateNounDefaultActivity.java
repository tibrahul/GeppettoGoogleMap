package com.npb.gp.dao.mysql.support.noun;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateNounDefaultActivity extends SqlUpdate {

	public static String SQL_UPDATE_NOUN_DEFAULT_ACTIVITY = "";

	public UpdateNounDefaultActivity(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_NOUN_DEFAULT_ACTIVITY);

		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("default_activity_id",
				Types.BIGINT));

	}

}
