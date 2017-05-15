package com.npb.gp.dao.mysql.support.module;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
/**
 * 
 * @author Reinaldo</br>
 *         Date Created: 20/09/2015</br>
 *        </p>
 *
 *        Insert class supporting the GpModuleDao class
 *        </p>
 *
 *
 */
public class InsertModule extends SqlUpdate {
	
	public static String SQL_INSERT_MODULE = "";

	public InsertModule(DataSource dataSource) {
		super(dataSource, SQL_INSERT_MODULE);
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));		
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));		
		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("projectid", Types.BIGINT));
		super.declareParameter(new SqlParameter("base_location", Types.VARCHAR));
		super.declareParameter(new SqlParameter("predefined_activity_id", Types.BIGINT));
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);

	}

}
