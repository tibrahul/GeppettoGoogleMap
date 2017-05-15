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

import com.npb.gp.dao.mysql.support.templatecomponent.InsertGpTemplateComponentMapper;
import com.npb.gp.dao.mysql.support.templatecomponent.SelectGpTemplateComponentMapper;
import com.npb.gp.dao.mysql.support.templatecomponent.UpdateGpTemplateComponentMapper;
import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.interfaces.dao.IGpTemplateComponentDao;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0 </p>
 *
 *        The purpose of this class is to interact with the DB for the basic
 *        search and CRUD operations for a template components base information
 *        </p>
 *
 */
@Repository("GpTemplateComponentDao")
public class GpTemplateComponentDao implements IGpTemplateComponentDao {
	private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;
	private InsertGpTemplateComponentMapper insertMapper;
	private UpdateGpTemplateComponentMapper updateMapper;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${sql.templateComponent.insert}")
	private String sqlInsert;

	@Value("${sql.templateComponent.update}")
	private String sqlUpdate;

	@Value("${sql.templateComponent.delete}")
	private String sqlDelete;

	@Value("${sql.templateComponent.delete.bytemplate}")
	private String sqlDeleteTemplateComponent;

	@Value("${sql.templateComponent.find}")
	private String sqlFind;

	@Value("${sql.templateComponent.find.ByTemplate}")
	private String sqlFindByTemplateId;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public void insert(GpProjectTemplateComponent wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", wrapper.getName());
		paramMap.put("description", wrapper.getDescription());
		paramMap.put("label", wrapper.getLabel());
		paramMap.put("template_id", wrapper.getTemplateId());
		paramMap.put("template_component_value",
				wrapper.getTemplateComponentValue());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertGpTemplateComponentMapper.SQL_INSERT_PROJECT = sqlInsert;
		this.insertMapper = new InsertGpTemplateComponentMapper(dataSource);
		this.insertMapper.updateByNamedParam(paramMap, keyHolder);
		wrapper.setTemplateComponentId(keyHolder.getKey().longValue());
	}

	@Override
	public void update(GpProjectTemplateComponent wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("template_component_id", wrapper.getTemplateComponentId());
		paramMap.put("name", wrapper.getName());
		paramMap.put("description", wrapper.getDescription());
		paramMap.put("label", wrapper.getLabel());
		paramMap.put("template_id", wrapper.getTemplateId());
		paramMap.put("template_component_value",
				wrapper.getTemplateComponentValue());

		UpdateGpTemplateComponentMapper.SQL_UPDATE_PROJECT = sqlUpdate;
		this.updateMapper = new UpdateGpTemplateComponentMapper(this.dataSource);
		this.updateMapper.updateByNamedParam(paramMap);
	}

	@Override
	public void delete(long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("template_component_id", id);

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
	public void deleteTemplateComponent(long templateId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("template_id", templateId);

		this.namedParameterJdbcTemplate.execute(
				this.sqlDeleteTemplateComponent, paramMap,
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
		parameters.addValue("template_component_id", id);

		SelectGpTemplateComponentMapper selectMapper = new SelectGpTemplateComponentMapper();

		List<GpProjectTemplateComponent> dto_list = this.namedParameterJdbcTemplate
				.query(sqlFind, parameters, selectMapper);
		if (dto_list.size() < 1) {
			log.warn("Template Component ID '" + id + "' was not found");
			throw new Exception("Template Component for ID '" + id
					+ "' was not found");
		}
		return (GpProjectTemplateComponent) dto_list.get(0);
	}

	@Override
	public List<GpProjectTemplateComponent> findByTemplate(long templateId)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("template_id", templateId);

		SelectGpTemplateComponentMapper selectMapper = new SelectGpTemplateComponentMapper();

		List<GpProjectTemplateComponent> dto_list = this.namedParameterJdbcTemplate
				.query(sqlFindByTemplateId, parameters, selectMapper);
		if (dto_list.size() < 1) {
			log.warn("Template Component for Template ID '" + templateId
					+ "' was not found");
		}
		return dto_list;
	}
}