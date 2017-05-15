package com.npb.gp.dao.mysql.support.menubuilder;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class UpdateMenuTree extends SqlUpdate {
	
	public static  String SQL_UPDATE_MENU_DETAIL = "";
	public UpdateMenuTree(DataSource dataSource) {
        super(dataSource, SQL_UPDATE_MENU_DETAIL);
              
    	super.declareParameter(new SqlParameter("activity_id",Types.BIGINT));
    	super.declareParameter(new SqlParameter("menu_tree",Types.VARCHAR));
    	
	}
}