package com.npb.gp.dao.mysql.support.menubuilder;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertMenuDetail extends SqlUpdate {
	
	public static  String SQL_INSERT_MENU = "";
	
	public InsertMenuDetail(DataSource dataSource) {
        super(dataSource, SQL_INSERT_MENU);
    	super.declareParameter(new SqlParameter("name",Types.VARCHAR));
    	//super.declareParameter(new SqlParameter("menu_master_id", Types.BIGINT));
    	super.declareParameter(new SqlParameter("label",Types.VARCHAR));
    	super.declareParameter(new SqlParameter("description",Types.VARCHAR));
    	super.declareParameter(new SqlParameter("auth_id",Types.BIGINT));
    	super.declareParameter(new SqlParameter("project_id",Types.BIGINT));
    	super.declareParameter(new SqlParameter("activity_id",Types.BIGINT));
    	//super.declareParameter(new SqlParameter("screen_id",Types.BIGINT));
    	super.declareParameter(new SqlParameter("menu_tree",Types.VARCHAR));

        super.setGeneratedKeysColumnNames(new String[] {"id"});
        super.setReturnGeneratedKeys(true);
	}
}