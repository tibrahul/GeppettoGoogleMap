package com.npb.gp.dao.mysql.support.noun;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertWSDL_Link extends SqlUpdate {
	
	public static  String SQL_InsertWSDL_Link = "";
	
	public InsertWSDL_Link(DataSource dataSource){
		super(dataSource, SQL_InsertWSDL_Link);
		
        super.declareParameter(new SqlParameter("wsdl_endpoint", Types.VARCHAR));
        super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
        super.declareParameter(new SqlParameter("user_id", Types.BIGINT));
	}

}
