package com.npb.gp.dao.mysql.support.verbs;

/**	
 * @author Suresh Palanisamy<br> Date Created: 31/03/2015<br>
 * @since .75</p>
 * 
 * <p> Insert Verb is the supporting class for Verb Dao</p>
 * 
 */

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertVerb extends SqlUpdate {

	public static String SQL_INSERT_VERB = "";

	public InsertVerb(DataSource dataSource) {
		super(dataSource, SQL_INSERT_VERB);

		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("action_on_data", Types.VARCHAR));
		super.declareParameter(new SqlParameter("activity_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("authorizations", Types.VARCHAR));
		super.declareParameter(new SqlParameter("screen_id", Types.VARCHAR));
		super.declareParameter(new SqlParameter("base_verb_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("actual_information", Types.VARCHAR));
		super.declareParameter(new SqlParameter("wsdl_operation_id", Types.VARCHAR));

		super.setGeneratedKeysColumnNames(new String[] { "id" });
		super.setReturnGeneratedKeys(true);
	}
}
