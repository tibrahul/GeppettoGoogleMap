package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import com.npb.gp.dao.mysql.support.projecttemplatecomponent.InsertGpProjectTemplateComponentMapper;
import com.npb.gp.dao.mysql.support.projecttemplatecomponent.SelectGpProjectTemplateComponentMapper;
import com.npb.gp.dao.mysql.support.projecttemplatecomponent.SelectGpProjectTemplateComponentMapper2;
import com.npb.gp.dao.mysql.support.projecttemplatecomponent.UpdateGpProjectTemplateComponentMapper;
import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.interfaces.dao.IGpProjectTemplateComponentDao;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/31/2015</br>
 * @since 1.0 </p>
 *
 *        The purpose of this class is to interact with the DB for the basic
 *        search and CRUD operations for a project template component </p>
 *
 */
@Repository("GpProjectTemplateComponentDao")
public class GpProjectTemplateComponentDao implements
		IGpProjectTemplateComponentDao {
	private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;
	private InsertGpProjectTemplateComponentMapper insertMapper;
	private UpdateGpProjectTemplateComponentMapper updateMapper;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${sql.projectTemplateComponent.insert}")
	private String sqlInsert;

	@Value("${sql.projectTemplateComponent.update}")
	private String sqlUpdate;

	@Value("${sql.projectTemplateComponent.delete}")
	private String sqlDelete;

	@Value("${sql.projectTemplateComponent.delete.byProject}")
	private String sqlDeleteByProject;

	@Value("${sql.projectTemplateComponent.delete.byProjectTemplate}")
	private String sqlDeleteByProjectTemplate;

	@Value("${sql.projectTemplateComponent.find}")
	private String sqlFind;

	@Value("${sql.projectTemplateComponent.find.byProject}")
	private String sqlFindByProject;

	@Value("${sql.projectTemplateComponent.find.byTemplateComponent}")
	private String sqlFindByTemplateComponent;

	@Value("${sql.projectTemplateComponent.find.byTemplateComponentAndProjectTemplate}")
	private String sqlFindByTemplateComponentAndProjectTemplate;

	@Value("${sql.projectTemplateComponent.find.byProjectTemplateAndTemplate}")
	private String sqlFindbyProjectTemplateAndTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public void insert(GpProjectTemplateComponent wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_id", wrapper.getProjectId());
		paramMap.put("project_template_id", wrapper.getProjectTemplateId());
		paramMap.put("template_component_id", wrapper.getTemplateComponentId());
		paramMap.put("template_component_value",
				wrapper.getTemplateComponentValue());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertGpProjectTemplateComponentMapper.SQL_INSERT_PROJECT = sqlInsert;
		this.insertMapper = new InsertGpProjectTemplateComponentMapper(
				dataSource);
		this.insertMapper.updateByNamedParam(paramMap, keyHolder);
		wrapper.setProjectTemplateComponentId(keyHolder.getKey().longValue());
	}

	@Override
	public void update(GpProjectTemplateComponent wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_template_component_id",
				wrapper.getProjectTemplateComponentId());
		paramMap.put("project_id", wrapper.getProjectId());
		paramMap.put("project_template_id", wrapper.getProjectTemplateId());
		paramMap.put("template_component_id", wrapper.getTemplateComponentId());
		paramMap.put("template_component_value",
				wrapper.getTemplateComponentValue());

		UpdateGpProjectTemplateComponentMapper.SQL_UPDATE_PROJECT = sqlUpdate;
		this.updateMapper = new UpdateGpProjectTemplateComponentMapper(
				this.dataSource);
		this.updateMapper.updateByNamedParam(paramMap);
	}

	@Override
	public void delete(long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_template_component_id", id);

		this.namedParameterJdbcTemplate.execute(this.sqlDelete, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public void deleteByProject(long projectId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_id", projectId);

		this.namedParameterJdbcTemplate.execute(this.sqlDeleteByProject,
				paramMap, new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public void deleteByProjectTemplate(long projectTemplateId)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_template_id", projectTemplateId);

		this.namedParameterJdbcTemplate.execute(
				this.sqlDeleteByProjectTemplate, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public GpProjectTemplateComponent find(long id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_template_component_id", id);

		SelectGpProjectTemplateComponentMapper selectMapper = new SelectGpProjectTemplateComponentMapper();

		List<GpProjectTemplateComponent> dto_list = this.namedParameterJdbcTemplate
				.query(sqlFind, parameters, selectMapper);
		if (dto_list.size() < 1) {
			log.warn("Project Template Component for ID '" + id
					+ "' was not found");
			throw new Exception("Project Template Component for ID '" + id
					+ "' was not found");
		}
		return (GpProjectTemplateComponent) dto_list.get(0);
	}

	@Override
	public List<GpProjectTemplateComponent> findByProjectTemplateAndTemplateId(
			long templateId, long projectTemplateId) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("template_id", templateId);
		parameters.addValue("project_template_id", projectTemplateId);

		SelectGpProjectTemplateComponentMapper2 selectMapper = new SelectGpProjectTemplateComponentMapper2();

		return this.namedParameterJdbcTemplate.query(
				sqlFindbyProjectTemplateAndTemplate, parameters, selectMapper);
	}

	@Override
	public List<GpProjectTemplateComponent> findByProject(long projectId)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", projectId);
		SelectGpProjectTemplateComponentMapper selectMapper = new SelectGpProjectTemplateComponentMapper();

		return this.namedParameterJdbcTemplate.query(sqlFindByProject,
				parameters, selectMapper);
	}

	@Override
	public List<GpProjectTemplateComponent> findByProjectTemplate(
			long projectTemplateId) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_template_id", projectTemplateId);
		SelectGpProjectTemplateComponentMapper selectMapper = new SelectGpProjectTemplateComponentMapper();

		return this.namedParameterJdbcTemplate.query(
				sqlFindByTemplateComponent, parameters, selectMapper);
	}

	@Override
	public List<GpProjectTemplateComponent> findByTemplateComponent(
			long templateComponentId) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("template_component_id", templateComponentId);
		SelectGpProjectTemplateComponentMapper selectMapper = new SelectGpProjectTemplateComponentMapper();

		return this.namedParameterJdbcTemplate.query(
				sqlFindByTemplateComponent, parameters, selectMapper);
	}

	@Override
	public GpProjectTemplateComponent findByTemplateComponentAndProjectTemplate(
			long projectTemplateId, long templateComponentId) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_template_id", projectTemplateId);
		parameters.addValue("template_component_id", templateComponentId);
		SelectGpProjectTemplateComponentMapper selectMapper = new SelectGpProjectTemplateComponentMapper();

		List<GpProjectTemplateComponent> dto_list = this.namedParameterJdbcTemplate
				.query(sqlFindByTemplateComponentAndProjectTemplate,
						parameters, selectMapper);
		if (dto_list != null && !dto_list.isEmpty()) {
			return (GpProjectTemplateComponent) dto_list.get(0);
		}
		return null;
	}
}