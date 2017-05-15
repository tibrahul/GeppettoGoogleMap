package com.npb.gp.dao.mysql.support.microflow;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**	
 * @author Reinaldo Lopez<br> Date Created: 15/10/2015<br>
 * @since 1.0</p>
 * 
 * <p> Delete MicroFlow is the supporting class for MicroFlow Dao</p>
 * 
 */
public class DeleteMicroFlow extends SqlUpdate {
	
	public static String SQL_DELETE_MICROFLOW = "";

	public DeleteMicroFlow(DataSource dataSource) {
		super(dataSource, SQL_DELETE_MICROFLOW);

		super.declareParameter(new SqlParameter("verb_id", Types.BIGINT));		
		
	}

}
