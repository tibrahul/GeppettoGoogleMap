package com.npb.gp.dao.mysql.support.organization;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class Base_organization_user  extends SqlUpdate {

	public static String BASE_ORGANIZATION_USER = "";

	public Base_organization_user(DataSource dataSource) {
		super(dataSource, BASE_ORGANIZATION_USER);

		super.declareParameter(new SqlParameter("base_organization_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("user_id",Types.BIGINT));
	
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);	}

}
