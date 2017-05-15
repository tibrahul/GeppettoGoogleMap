package com.npb.gp.dao.mysql.support.noun;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 13/03/2015</br>
 * @since .75</p>
 *
 *        Update class supporting the GpNounDao class</p>
 *
 */

public class UpdateNoun extends SqlUpdate {

	public static String SQL_UPDATE_NOUN = "";

	public UpdateNoun(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_NOUN);

		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("projectid", Types.BIGINT));
		super.declareParameter(new SqlParameter("moduleid", Types.BIGINT));
		super.declareParameter(new SqlParameter("cache_enabled", Types.VARCHAR));

		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date",
				Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by",
				Types.BIGINT));

	}

}
