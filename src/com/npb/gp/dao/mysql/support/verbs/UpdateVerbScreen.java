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

public class UpdateVerbScreen extends SqlUpdate {

	public static String SQL_UPDATE_VERB_SCREEN = "";

	public UpdateVerbScreen(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_VERB_SCREEN);

		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("screen_id", Types.VARCHAR));
	}
}
