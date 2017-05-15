package com.npb.gp.dao.mysql.support.user;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dhinakar
 * 
 *
 *        Insert class supporting the GpNewUsersDao class for allocate the mac machine for user</p>
 *
 *
 */
public class UpdateMacUser  extends SqlUpdate {

	public static String SQL_UPADATE_MAC_USER = "";

	public UpdateMacUser(DataSource dataSource) {
		super(dataSource, SQL_UPADATE_MAC_USER);

		super.declareParameter(new SqlParameter("id", Types.INTEGER));
		super.declareParameter(new SqlParameter("status",Types.VARCHAR ));
		super.declareParameter(new SqlParameter("updatedDate",Types.DATE ));
		super.setReturnGeneratedKeys(true);
	}

}
