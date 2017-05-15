package com.npb.gp.dao.mysql.support.authority;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Rashmi</br> Date Created: 09/09/2015
 * 
 *
 *
 */

public class InsertAuthority extends SqlUpdate {

	public static String SQL_INSERT_AUTHORITY = "";

	public InsertAuthority(DataSource dataSource) {
		super(dataSource, SQL_INSERT_AUTHORITY);

		super.declareParameter(new SqlParameter("user_id", Types.LONGVARCHAR));
		super.declareParameter(new SqlParameter("role_id", Types.BIGINT));
		setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}

}
