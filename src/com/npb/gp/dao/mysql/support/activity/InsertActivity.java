package com.npb.gp.dao.mysql.support.activity;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 02/26/2014</br>
 * @since .35
 *        </p>
 *
 *        Insert class supporting the GpActivityDao class
 *        </p>
 *
 *
 */
public class InsertActivity extends SqlUpdate {

	public static String SQL_INSERT_ACTIVITY = "";

	public InsertActivity(DataSource dataSource) {
		super(dataSource, SQL_INSERT_ACTIVITY);
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("projectid", Types.BIGINT));
		super.declareParameter(new SqlParameter("moduleid", Types.BIGINT));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("primary_noun_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("activity_types", Types.VARCHAR));
		super.declareParameter(new SqlParameter("secondary_nouns", Types.VARCHAR));
		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("module_type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("predefined_activity_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("wsdl_id", Types.BIGINT));
		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);

	}

}
