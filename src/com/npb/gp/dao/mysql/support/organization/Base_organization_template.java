package com.npb.gp.dao.mysql.support.organization;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class Base_organization_template  extends SqlUpdate  {

	public static String BASE_ORGANIZATION_TEMPLATE = "";

	public Base_organization_template(DataSource dataSource) {
		super(dataSource, BASE_ORGANIZATION_TEMPLATE);

		super.declareParameter(new SqlParameter("base_organization_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("template_id",Types.BIGINT));
	
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);	}
}
