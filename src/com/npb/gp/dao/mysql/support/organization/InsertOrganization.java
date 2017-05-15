package com.npb.gp.dao.mysql.support.organization;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dan Castillo</br> Date Created: 04/15/2014</br>
 * @since .35</p>
 *
 *        Insert class supporting the GpProjectDao class</p>
 *
 *
 */

public class InsertOrganization extends SqlUpdate {

	public static String SQL_INSERT_ORGANIZATION = "";

	public InsertOrganization(DataSource dataSource) {
		super(dataSource, SQL_INSERT_ORGANIZATION);

		super.declareParameter(new SqlParameter("organization_name", Types.VARCHAR));
 
		super.declareParameter(new SqlParameter("contact_phone",Types.VARCHAR));
		super.declareParameter(new SqlParameter("city",Types.VARCHAR));
		super.declareParameter(new SqlParameter("country",Types.VARCHAR));
		super.declareParameter(new SqlParameter("about",Types.VARCHAR));
		
  
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}

}
