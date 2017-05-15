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
 *        Update noun attribute class supporting the GpNounDao class</p>
 *
 */

public class UpdateNounAttribute extends SqlUpdate {

	public static String SQL_UPDATE_ATTRIBUTE = "";

	public UpdateNounAttribute(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_ATTRIBUTE);

		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("nounid", Types.BIGINT));
		super.declareParameter(new SqlParameter("base_attr_type_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("part_of_unique_key",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("data_length", Types.VARCHAR));
		super.declareParameter(new SqlParameter("relationships", Types.VARCHAR));

		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date",
				Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by",
				Types.BIGINT));
		
	    super.declareParameter(new SqlParameter("modifier_name",Types.VARCHAR));
	    super.declareParameter(new SqlParameter("modifier_id",Types.BIGINT));
	    super.declareParameter(new SqlParameter("is_secondary_noun",Types.TINYINT));
	}
}
