package com.npb.gp.dao.mysql.support.flowcontrol;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateMasterFlowId extends SqlUpdate {
	public static String SQL_UPDATE_MASTER = "";

	public UpdateMasterFlowId(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_MASTER);
		super.declareParameter(new SqlParameter("master_flow_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("id", Types.BIGINT));

		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}
}