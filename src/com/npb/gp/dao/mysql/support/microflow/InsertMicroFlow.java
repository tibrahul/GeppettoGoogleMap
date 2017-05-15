package com.npb.gp.dao.mysql.support.microflow;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/***
 * <b>Created Date: 04/09/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * 
 * */
public class InsertMicroFlow extends SqlUpdate {

	public static String SQL_INSERT_MICRO_FLOW = "";

	public InsertMicroFlow(DataSource dataSource) {
		super(dataSource, SQL_INSERT_MICRO_FLOW);
		super.declareParameter(new SqlParameter("flow_control_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("component_type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("master_flow_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("code_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("sequence_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("verb_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("action", Types.VARCHAR));

		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}
}
