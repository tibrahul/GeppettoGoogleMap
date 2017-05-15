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

import com.npb.gp.dao.mysql.support.projecttemplate.InsertGpProjectTemplateMapper;
import com.npb.gp.dao.mysql.support.projecttemplate.SelectGpProjectTemplateMapper;
import com.npb.gp.dao.mysql.support.projecttemplate.UpdateGpProjectTemplateMapper;
import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.interfaces.dao.IGpProjectTemplateDao;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0 </p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search and CRUD operations for a project template information </p>
 *
 */
@Repository("GpProjectTemplateDao")
public class GpProjectTemplateDao implements IGpProjectTemplateDao {
	private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;
	private InsertGpProjectTemplateMapper insertMapper;
	private UpdateGpProjectTemplateMapper updateMapper;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${sql.projectTemplate.insert}")
	private String sqlInsert;

	@Value("${sql.projectTemplate.update}")
	private String sqlUpdate;

	@Value("${sql.projectTemplate.delete}")
	private String sqlDelete;

	@Value("${sql.projectTemplate.delete.byProject}")
	private String sqlDeleteByProject;

	@Value("${sql.projectTemplate.find}")
	private String sqlFind;

	@Value("${sql.projectTemplate.find.byProject}")
	private String sqlFindByProject;

	@Value("${sql.projectTemplate.find.byTemplate}")
	private String sqlFindByTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public void insert(GpProjectTemplate wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_id", wrapper.getProjectId());
		paramMap.put("template_id", wrapper.getTemplateId());
		paramMap.put("color", wrapper.getColor());
		paramMap.put("name", wrapper.getName());
		paramMap.put("label", wrapper.getLabel());
		paramMap.put("description", wrapper.getDescription());
		paramMap.put("device", wrapper.getDevice());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertGpProjectTemplateMapper.SQL_INSERT_PROJECT = sqlInsert;
		this.insertMapper = new InsertGpProjectTemplateMapper(dataSource);
		this.insertMapper.updateByNamedParam(paramMap, keyHolder);
		wrapper.setProjectTemplateId(keyHolder.getKey().longValue());
	}

	@Override
	public void update(GpProjectTemplate wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_template_id", wrapper.getProjectTemplateId());
		paramMap.put("project_id", wrapper.getProjectId());
		paramMap.put("template_id", wrapper.getTemplateId());
		paramMap.put("color", wrapper.getColor());

		UpdateGpProjectTemplateMapper.SQL_UPDATE_PROJECT = sqlUpdate;
		this.updateMapper = new UpdateGpProjectTemplateMapper(this.dataSource);
		this.updateMapper.updateByNamedParam(paramMap);
	}

	@Override
	public void delete(long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_template_id", id);

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
	public GpProjectTemplate find(long id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_template_id", id);
		SelectGpProjectTemplateMapper selectMapper = new SelectGpProjectTemplateMapper();

		List<GpProjectTemplate> dto_list = this.namedParameterJdbcTemplate
				.query(sqlFind, parameters, selectMapper);
		if (dto_list.size() < 1) {
			log.warn("Project Template for ID '" + id + "' was not found");
			throw new Exception("Project Template for ID '" + id
					+ "' was not found");
		}
		return (GpProjectTemplate) dto_list.get(0);
	}

	@Override
	public List<GpProjectTemplate> findByProject(long projectId)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", projectId);
		SelectGpProjectTemplateMapper selectMapper = new SelectGpProjectTemplateMapper();

		return this.namedParameterJdbcTemplate.query(sqlFindByProject,
				parameters, selectMapper);
	}

	@Override
	public List<GpProjectTemplate> findByTemplate(long templateId)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("template_id", templateId);
		SelectGpProjectTemplateMapper selectMapper = new SelectGpProjectTemplateMapper();

		return this.namedParameterJdbcTemplate.query(sqlFindByTemplate,
				parameters, selectMapper);
	}

}