package com.npb.gp.dao.mysql.support.verbs;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateWSDLOperation extends SqlUpdate {

	public static String SQL_UPDATE_WSDL_OPERATION = "";

	public UpdateWSDLOperation(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_WSDL_OPERATION);

		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("wsdl_operation_id", Types.VARCHAR));
	}
}
