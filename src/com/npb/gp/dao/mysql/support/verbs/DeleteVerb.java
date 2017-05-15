package com.npb.gp.dao.mysql.support.verbs;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * @author Reinaldo Lopez<br>
 *         Date Created: 15/10/2015<br>
 * @since 1.0</p>
 * 
 *        <p>
 *        Delete Verb is the supporting class for Verb Dao
 *        </p>
 * 
 */
public class DeleteVerb extends SqlUpdate {

	public static String SQL_DELETE_VERBS = "";

	public DeleteVerb(DataSource dataSource) {
		super(dataSource, SQL_DELETE_VERBS);

		super.declareParameter(new SqlParameter("screen_id", Types.VARCHAR));
		super.declareParameter(new SqlParameter("wsdl_operation_id", Types.VARCHAR));

	}

}
