package com.npb.gp.dao.mysql.support.flowcontrol;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertFlowControl extends SqlUpdate {

	public static String SQL_INSERT_FLOW_CONTROL = "";

	public InsertFlowControl(DataSource dataSource) {
		super(dataSource, SQL_INSERT_FLOW_CONTROL);
		super.declareParameter(new SqlParameter("master_flow_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("component_type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("sequence_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("sub_sequence_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("activity_id", Types.BIGINT));

		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}
}
