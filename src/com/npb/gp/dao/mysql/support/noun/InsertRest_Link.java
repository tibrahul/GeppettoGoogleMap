package com.npb.gp.dao.mysql.support.noun;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertRest_Link extends SqlUpdate {
public static  String SQL_InsertREST_LINK = "";
	
	public InsertRest_Link(DataSource dataSource){
		super(dataSource, SQL_InsertREST_LINK);
		
        super.declareParameter(new SqlParameter("rest_endpoint", Types.VARCHAR));
        super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
        super.declareParameter(new SqlParameter("user_id", Types.BIGINT));
	}
	
}
