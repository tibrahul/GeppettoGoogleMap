package com.npb.gp.dao.mysql.support.activity;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 03/10/2014</br>
 * @since .35
 *        </p>
 *
 *        Update class supporting the GpActivityDao class
 *        </p>
 *
 *
 */

public class UpdateButtonGroup extends SqlUpdate {
	public static String SQL_UPDATE_BUTTON_GROUP = "";

	public UpdateButtonGroup(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_BUTTON_GROUP);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("data_binding_context", Types.VARCHAR));
		super.declareParameter(new SqlParameter("noun_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("noun_attribute_id", Types.BIGINT));
	}
}
