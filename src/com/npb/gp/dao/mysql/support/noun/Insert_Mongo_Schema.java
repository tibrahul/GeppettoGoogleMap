package com.npb.gp.dao.mysql.support.noun;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class Insert_Mongo_Schema extends SqlUpdate {
	
	public static  String SQL_INSERT_MONGO_SCHEMA = "";
	
	public Insert_Mongo_Schema(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		
        super(dataSource, SQL_INSERT_MONGO_SCHEMA);
        
        super.declareParameter(new SqlParameter("db_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("collection_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("attribute", Types.VARCHAR));
        super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
        super.declareParameter(new SqlParameter("user_id", Types.BIGINT));
        
        super.setGeneratedKeysColumnNames(new String[] {"id"});
        super.setReturnGeneratedKeys(true);
	}

	

}
