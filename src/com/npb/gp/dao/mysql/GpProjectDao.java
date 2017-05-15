package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.dao.mysql.support.project.GpExisting_ProjectName_Mapper;
import com.npb.gp.dao.mysql.support.project.GpProject_Mapper;
import com.npb.gp.dao.mysql.support.project.InsertProject;
import com.npb.gp.dao.mysql.support.project.Insert_Deleted_Project;
import com.npb.gp.dao.mysql.support.project.UpdateProject;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.interfaces.dao.IGpProjectDao;

/**
 * 
 * @author Dan Castillo</br> Date Created: 04/08/2014</br>
 * @since .35 </p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for a project </p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun" </p>
 * 
 *        Modified Date: 22/05/2015</br> Modified By: kumaresan perumal </p>
 * 
 *        i removed the client css frame works because it was an array list to
 *        select multiple options. our requirement is single selection. i
 *        changed it as single selection string
 * 
 *        <b> Modified date: 06/05/2015</br> Modified by: Kumaresan perumal<b><br>
 * 
 *        <p>
 *        Written new method as get_default_module_id
 *        </p>
 * 
 *        Modified date: 10/04/2015</br> Modified by: Kumaresan perumal<b></br>
 * 
 *        <p>
 *        Modified the enforce_documentation in insert and update method
 *        </p>
 *
 *        Modified date: 10/04/2015</br> Modified by: Kumaresan perumal<b></br>
 * 
 *        <p>
 *        I added a method that is "insertion_for_deleted_projects_of_record"
 *        method and statements in delete method
 *        "delete execution statements for tables"
 *        </p>
 *
 *        Modified date: 07/04/2015</br> Modified by: Kumaresan perumal<b></br>
 * 
 *        <p>
 *        Wrote delete,update methods two object variables that is
 *        "delete_project,update_project"
 *        </p>
 *        <p>
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo
 *        </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 *
 *        <b>Modified Date: 02/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Modified the "insert" to create a new project and the InsertProject
 *        moved inside the insert method
 *        </p>
 *
 *        <b>Modified Date: 01/01/2016<br>
 *        Modified By: Dheeraj Singh<b><br>
 * 
 *        <p>
 *        Modified the "delete" to add the delete scripts for ProjectTemplaet
 *        and ProjectTemplateComponent
 *        </p>
 *
 */
@Repository("GpProjectDao")
public class GpProjectDao implements IGpProjectDao {

	private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;
	private InsertProject insert_project;
	private UpdateProject update_project;
	private Insert_Deleted_Project insert_deleted_project;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${getAllProjectsByUserId.sql}")
	private String getAllProjectByUserId;

	@Value("${findProjectDetailsByProjectId.sql}")
	private String findProjectDetailsByProjectId;

	@Value("${insert.project.sql}")
	private String inserProject;

	@Value("${update.project.sql}")
	private String updateProject;

	@Value("${delete.project.sql}")
	private String deleteProject;

	@Value("${insert.projects.deletion.sql}")
	private String insertion_for_project_deletion;

	@Value("${delete.nouns.sql}")
	private String delete_nouns;

	@Value("${delete.nounsattributes.sql}")
	private String delete_nounattributes;

	@Value("${delete.widgets.sql}")
	private String delete_widgets;

	@Value("${delete.screens.sql}")
	private String delete_screens;

	@Value("${delete.micro_flow.sql}")
	private String micro_flow;

	@Value("${delete.flow_control.sql}")
	private String flowcontrol;

	@Value("${delete.activities.sql}")
	private String activities;

	@Value("${delete.verbs.sql}")
	private String verbs;

	@Value("${search_for_exist_project.sql}")
	private String search_for_exist_project;

	@Value("${delete.projecttemplate.sql}")
	private String projectTemplate;

	@Value("${delete.projecttemplatecomponent.sql}")
	private String projectTemplateComponent;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public GpProject insert(GpProject aproject) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", aproject.getName());
		paramMap.put("label", aproject.getLabel());
		paramMap.put("description", aproject.getDescription());
		// paramMap.put("default_module_id",
		// aproject.getDefault_module().getId());
		paramMap.put("default_module_label", aproject.getDefault_module()
				.getLabel());
		paramMap.put("notes", aproject.getNotes());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", aproject.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", aproject.getLastmodifiedby());
		String client_os_types = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_os_types());
		paramMap.put("client_os_types", client_os_types);
		String client_device_types = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_device_types());
		paramMap.put("client_device_types", client_device_types);
		long client_dev_languages = aproject.getClient_dev_language();
		paramMap.put("client_dev_languages", client_dev_languages);
		long client_dev_frameworks = aproject.getClient_dev_framework();
		paramMap.put("client_dev_frameworks", client_dev_frameworks);
		String client_widget_frameworks = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_widget_frameworks());
		paramMap.put("client_widget_frameworks", client_widget_frameworks);
		paramMap.put("mobile_css_framework", aproject.getMobile_css_framework());
		paramMap.put("desktop_css_framework",
				aproject.getDesktop_css_framework());
		if (aproject.isStand_alone_app()) {
			paramMap.put("stand_alone_app", "y");
		} else {
			paramMap.put("stand_alone_app", "n");
		}
		if (aproject.isCommunication_protocal()) {
			paramMap.put("communication_protocal", "y");
		} else {
			paramMap.put("communication_protocal", "n");
		}

		String app_ui_template = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getApp_ui_template());
		paramMap.put("app_ui_template", app_ui_template);
		paramMap.put("client_code_pattern", aproject.getClient_code_pattern());
		paramMap.put("server_code_pattern", aproject.getServer_code_pattern());
		paramMap.put("server_dev_lang", aproject.getServer_dev_lang());
		paramMap.put("server_dev_framework", aproject.getServer_dev_framework());
		paramMap.put("server_run_time", aproject.getServer_run_time());
		paramMap.put("server_os", aproject.getServer_os());
		paramMap.put("server_dbms", aproject.getServer_dbms());
		paramMap.put("server_deployment_environment",
				aproject.getServer_deployment_environment());
		String global_extensions = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getGlobal_extensions());
		paramMap.put("global_extensions", global_extensions);
		String ui_navigation_styles = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getUi_navigation_styles());
		paramMap.put("ui_navigation_styles", ui_navigation_styles);
		String supported_browsers = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getSupported_browsers());
		paramMap.put("supported_browsers", supported_browsers);
		paramMap.put("default_human_language",
				aproject.getDefault_human_language());
		String other_human_languages = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getOther_human_languages());
		paramMap.put("other_human_languages", other_human_languages);
		paramMap.put("entity", aproject.getEntity());
		if (aproject.isEnforce_documentation()) {
			paramMap.put("enforce_documentation", "y");
		} else {
			paramMap.put("enforce_documentation", "n");
		}
		paramMap.put("widget_count", aproject.getWidget_count());
		paramMap.put("generation_type", aproject.getGeneration_type());
		paramMap.put("authorization_type", aproject.getAuthorization_type());
		String authorizations = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getAuthorizations());
		paramMap.put("authorizations", authorizations);
		paramMap.put("application_type", aproject.getApplication_type());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertProject.SQL_INSERT_PROJECT = inserProject;
		this.insert_project = new InsertProject(dataSource);
		this.insert_project.updateByNamedParam(paramMap, keyHolder);
		aproject.setId(keyHolder.getKey().longValue());
		return aproject;

	}

	@Override
	public GpProject update(GpProject aproject) throws Exception {
		Map<String, Object> updateObj = new HashMap<String, Object>();
		updateObj.put("id", aproject.getId());
		updateObj.put("name", aproject.getName());
		updateObj.put("label", aproject.getLabel());
		updateObj.put("description", aproject.getDescription());
		updateObj.put("default_module_id", aproject.getDefault_module_id());
		updateObj.put("default_module_label",
				aproject.getDefault_module_label());
		updateObj.put("notes", aproject.getNotes());
		updateObj.put("created_by", aproject.getCreatedby());
		updateObj.put("created_date", new Date());
		updateObj.put("last_modified_by", aproject.getLastmodifiedby());
		updateObj.put("last_modified_date", new Date());
		String client_os_types = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_os_types());
		updateObj.put("client_os_types", client_os_types);
		String client_device_types = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_device_types());
		updateObj.put("client_device_types", client_device_types);
		long client_dev_languages = aproject.getClient_dev_language();
		updateObj.put("client_dev_languages", client_dev_languages);
		long client_dev_frameworks = aproject.getClient_dev_framework();
		updateObj.put("client_dev_frameworks", client_dev_frameworks);
		String client_widget_frameworks = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_widget_frameworks());
		updateObj.put("client_widget_frameworks", client_widget_frameworks);

		updateObj.put("mobile_css_framework",
				aproject.getMobile_css_framework());
		updateObj.put("desktop_css_framework",
				aproject.getDesktop_css_framework());
		if (aproject.isStand_alone_app()) {
			updateObj.put("stand_alone_app", "y");
		} else {
			updateObj.put("stand_alone_app", "n");
		}
		if (aproject.isCommunication_protocal()) {
			updateObj.put("communication_protocal", "y");
		} else {
			updateObj.put("communication_protocal", "n");
		}
		String app_ui_template = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getApp_ui_template());
		updateObj.put("app_ui_template", app_ui_template);
		updateObj.put("client_code_pattern", aproject.getClient_code_pattern());
		updateObj.put("server_code_pattern", aproject.getServer_code_pattern());
		updateObj.put("server_dev_lang", aproject.getServer_dev_lang());
		updateObj.put("server_dev_framework",
				aproject.getServer_dev_framework());
		updateObj.put("server_run_time", aproject.getServer_run_time());
		updateObj.put("server_os", aproject.getServer_os());
		updateObj.put("server_dbms", aproject.getServer_dbms());
		updateObj.put("server_deployment_environment",
				aproject.getServer_deployment_environment());
		String global_extensions = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getGlobal_extensions());
		updateObj.put("global_extensions", global_extensions);
		String ui_navigation_styles = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getUi_navigation_styles());
		updateObj.put("ui_navigation_styles", ui_navigation_styles);
		String supported_browsers = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getSupported_browsers());
		updateObj.put("supported_browsers", supported_browsers);
		updateObj.put("default_human_language",
				aproject.getDefault_human_language());
		String other_human_languages = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getOther_human_languages());
		updateObj.put("other_human_languages", other_human_languages);
		updateObj.put("entity", aproject.getEntity());
		if (aproject.isEnforce_documentation()) {
			updateObj.put("enforce_documentation", "y");
		} else {
			updateObj.put("enforce_documentation", "n");
		}
		updateObj.put("widget_count", aproject.getWidget_count());
		updateObj.put("generation_type", aproject.getGeneration_type());
		updateObj.put("authorization_type", aproject.getAuthorization_type());
		String authorizations = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getAuthorizations());
		updateObj.put("authorizations", authorizations);
		updateObj.put("application_type", aproject.getApplication_type());
		updateObj.put("lotus_notes_enabled", aproject.getLotus_notes_enabled());
		updateObj.put("extra_project_info", aproject.getExtra_project_info());
		updateObj.put("lotus_notes_cred_enabled", aproject.getLotus_notes_cred_enabled());
		
		System.out.println(updateObj.toString());
		
		UpdateProject.SQL_UPDATE_PROJECT = updateProject;
		this.update_project = new UpdateProject(this.dataSource);
		this.update_project.updateByNamedParam(updateObj);
		return aproject;
	}

	@Override
	public void delete(GpProject aproject) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", aproject.getId());

		this.insertion_for_deleted_projects_of_record(aproject);

		this.namedParameterJdbcTemplate.execute(this.deleteProject, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});

		this.namedParameterJdbcTemplate.execute(this.delete_nounattributes,
				paramMap, new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.delete_nouns, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.delete_widgets, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.delete_screens, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});

		this.namedParameterJdbcTemplate.execute(this.micro_flow, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.flowcontrol, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.verbs, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.activities, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.projectTemplate, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		this.namedParameterJdbcTemplate.execute(this.projectTemplateComponent,
				paramMap, new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public GpProject find_by_id(long project_id) throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		GpProject_Mapper project_mapper = new GpProject_Mapper();
		List<GpProject> dto_list = this.namedParameterJdbcTemplate.query(
				findProjectDetailsByProjectId, parameters, project_mapper);
		if (dto_list.size() < 1) {
			throw new Exception("project_id number " + project_id
					+ " was not found");
		}
		return (GpProject) dto_list.get(0);

	}

	@Override
	public ArrayList<GpProject> find_by_user_id(long user_id) throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("created_by", user_id);
		GpProject_Mapper project_mapper = new GpProject_Mapper();
		List<GpProject> dto_list = this.namedParameterJdbcTemplate.query(
				getAllProjectByUserId, parameters, project_mapper);
		/*
		 * if (dto_list.size() < 1) { throw new Exception("user_id number " +
		 * user_id + " was not found"); }
		 */
		System.out
				.println("######### - In GpProjectDao -  find_by_user_id dto_list.size() is: "
						+ dto_list.size() + " #######################");

		return (ArrayList<GpProject>) dto_list;
	}

	// Insertion process for deleting record
	@Override
	public void insertion_for_deleted_projects_of_record(GpProject aproject)
			throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_id", aproject.getId());
		paramMap.put("name", aproject.getName());
		paramMap.put("label", aproject.getLabel());
		paramMap.put("description", aproject.getDescription());
		paramMap.put("default_module_id", aproject.getDefault_module_id());
		paramMap.put("default_module_label", aproject.getDefault_module_label());
		paramMap.put("notes", aproject.getNotes());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", aproject.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", aproject.getLastmodifiedby());
		String client_os_types = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_os_types());
		paramMap.put("client_os_types", client_os_types);
		String client_device_types = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_device_types());
		paramMap.put("client_device_types", client_device_types);
		long client_dev_languages = aproject.getClient_dev_language();
		paramMap.put("client_dev_languages", client_dev_languages);
		long client_dev_frameworks = aproject.getClient_dev_framework();
		paramMap.put("client_dev_frameworks", client_dev_frameworks);
		String client_widget_frameworks = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getClient_widget_frameworks());
		paramMap.put("client_widget_frameworks", client_widget_frameworks);
		paramMap.put("mobile_css_framework", aproject.getMobile_css_framework());
		paramMap.put("desktop_css_framework",
				aproject.getDesktop_css_framework());
		if (aproject.isStand_alone_app()) {
			paramMap.put("stand_alone_app", "y");
		} else {
			paramMap.put("stand_alone_app", "n");
		}
		if (aproject.isCommunication_protocal()) {
			paramMap.put("communication_protocal", "y");
		} else {
			paramMap.put("communication_protocal", "n");
		}
		String app_ui_template = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getApp_ui_template());
		paramMap.put("app_ui_template", app_ui_template);
		paramMap.put("client_code_pattern", aproject.getClient_code_pattern());
		paramMap.put("server_code_pattern", aproject.getServer_code_pattern());
		paramMap.put("server_dev_lang", aproject.getServer_dev_lang());
		paramMap.put("server_dev_framework", aproject.getServer_dev_framework());
		paramMap.put("server_run_time", aproject.getServer_run_time());
		paramMap.put("server_os", aproject.getServer_os());
		paramMap.put("server_dbms", aproject.getServer_dbms());
		paramMap.put("server_deployment_environment",
				aproject.getServer_deployment_environment());
		String global_extensions = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getGlobal_extensions());
		paramMap.put("global_extensions", global_extensions);
		String ui_navigation_styles = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getUi_navigation_styles());
		paramMap.put("ui_navigation_styles", ui_navigation_styles);
		String supported_browsers = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getSupported_browsers());
		paramMap.put("supported_browsers", supported_browsers);
		paramMap.put("default_human_language",
				aproject.getDefault_human_language());
		String other_human_languages = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getOther_human_languages());
		paramMap.put("other_human_languages", other_human_languages);
		paramMap.put("entity", aproject.getEntity());
		if (aproject.isEnforce_documentation()) {
			paramMap.put("enforce_documentation", "y");
		} else {
			paramMap.put("enforce_documentation", "n");
		}
		paramMap.put("widget_count", aproject.getWidget_count());
		paramMap.put("generation_type", aproject.getGeneration_type());
		paramMap.put("authorization_type", aproject.getAuthorization_type());
		String authorizations = GpGenericRecordParserBuilder
				.buildDelimitedString(aproject.getAuthorizations());
		paramMap.put("authorizations", authorizations);
		Insert_Deleted_Project.SQL_INSERT_PROJECT = insertion_for_project_deletion;
		this.insert_deleted_project = new Insert_Deleted_Project(dataSource);
		this.insert_deleted_project.updateByNamedParam(paramMap);

	}

	@Override
	public long get_default_module_id(long project_id) throws Exception {
		GpProject project = this.find_by_id(project_id);
		System.out.println("$$$$$$$$$$" + project.getDefault_module_id());
		return project.getDefault_module_id();
	}

	@Override
	public boolean search_for_exist_project(String project_name)
			throws Exception {
		System.out.println("The existing project name is " + project_name);
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_name", project_name);
		GpExisting_ProjectName_Mapper project_mapper = new GpExisting_ProjectName_Mapper();
		List<GpProject> dto_list = this.namedParameterJdbcTemplate.query(
				search_for_exist_project, parameters, project_mapper);
		System.out.println("Project name size is :" + dto_list.size());
		if (dto_list.isEmpty())
			return true;
		else
			return false;
	}
}