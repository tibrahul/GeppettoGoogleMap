package com.npb.gp.dao.mysql.support.project;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dan Castillo</br> Date Created: 04/15/2014</br>
 * @since .35</p>
 *
 *        Insert class supporting the GpProjectDao class</p>
 *
 *
 */

public class Insert_Deleted_Project extends SqlUpdate {

	public static String SQL_INSERT_PROJECT = "";

	public Insert_Deleted_Project(DataSource dataSource) {
		super(dataSource, SQL_INSERT_PROJECT);
        super.declareParameter(new SqlParameter("project_id",Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("default_module_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("default_module_label",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date",
				Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("client_os_types",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_device_types",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_dev_languages",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_dev_frameworks",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_widget_frameworks",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("mobile_css_framework",Types.VARCHAR));
		super.declareParameter(new SqlParameter("desktop_css_framework",Types.VARCHAR));
		super.declareParameter(new SqlParameter("stand_alone_app",Types.VARCHAR));
		super.declareParameter(new SqlParameter("communication_protocal",Types.VARCHAR));
		super.declareParameter(new SqlParameter("app_ui_template",Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_code_pattern",Types.BIGINT));
		super.declareParameter(new SqlParameter("server_code_pattern",Types.BIGINT));
		super.declareParameter(new SqlParameter("server_dev_lang", Types.BIGINT));
		super.declareParameter(new SqlParameter("server_dev_framework",	Types.BIGINT));
		super.declareParameter(new SqlParameter("server_run_time", Types.BIGINT));
		super.declareParameter(new SqlParameter("server_os", Types.BIGINT));
		super.declareParameter(new SqlParameter("server_dbms", Types.BIGINT));
		super.declareParameter(new SqlParameter("server_deployment_environment", Types.BIGINT));
		super.declareParameter(new SqlParameter("global_extensions",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("ui_navigation_styles",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("supported_browsers",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("default_human_language",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("other_human_languages",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("entity", Types.VARCHAR));
		super.declareParameter(new SqlParameter("enforce_documentation",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("widget_count", Types.BIGINT));
		super.declareParameter(new SqlParameter("generation_type",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("authorization_type",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("authorizations", Types.VARCHAR));
		//super.setGeneratedKeysColumnNames(new String[] { "id" });
		//super.setReturnGeneratedKeys(true);
	}

}
