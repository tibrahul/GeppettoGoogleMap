package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.npb.gp.dao.mysql.support.template.InsertGpTemplateMapper;
import com.npb.gp.dao.mysql.support.template.SelectGpTemplateByOrganizationMapper;
import com.npb.gp.dao.mysql.support.template.SelectGpTemplateByOrganization_dto;
import com.npb.gp.dao.mysql.support.template.SelectGpTemplateMapper;
import com.npb.gp.dao.mysql.support.template.UpdateGpTemplateMapper;
import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.interfaces.dao.IGpTemplateDao;

/**
 * 
 * @author Dheeraj Singh</br>
 *         Date Created: 12/28/2015</br>
 * @since 1.0
 *        </p>
 *
 *        The purpose of this class is to interact with the DB for the basic
 *        search and CRUD operations for a template base information
 *        </p>
 *
 */
@Repository("GpTemplateDao")
public class GpTemplateDao implements IGpTemplateDao {
	private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;
	private InsertGpTemplateMapper insertMapper;
	private UpdateGpTemplateMapper updateMapper;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${sql.template.insert}")
	private String sqlInsert;

	@Value("${sql.template.update}")
	private String sqlUpdate;

	@Value("${sql.template.delete}")
	private String sqlDelete;

	@Value("${sql.template.find}")
	private String sqlFind;

	@Value("${sql.template.findAll}")
	private String sqlFindAll;

	@Value("${sql.template.findByOrganization}")
	private String findByOrganization;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void insert(GpProjectTemplate wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", wrapper.getName());
		paramMap.put("description", wrapper.getDescription());
		paramMap.put("label", wrapper.getLabel());
		paramMap.put("color", wrapper.getColor());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertGpTemplateMapper.SQL_INSERT_PROJECT = sqlInsert;
		this.insertMapper = new InsertGpTemplateMapper(dataSource);
		this.insertMapper.updateByNamedParam(paramMap, keyHolder);
		wrapper.setTemplateId(keyHolder.getKey().longValue());
	}

	@Override
	public void update(GpProjectTemplate wrapper) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("template_id", wrapper.getTemplateId());
		paramMap.put("name", wrapper.getName());
		paramMap.put("description", wrapper.getDescription());
		paramMap.put("label", wrapper.getLabel());
		paramMap.put("color", wrapper.getColor());

		UpdateGpTemplateMapper.SQL_UPDATE_PROJECT = sqlUpdate;
		this.updateMapper = new UpdateGpTemplateMapper(this.dataSource);
		this.updateMapper.updateByNamedParam(paramMap);
	}

	@Override
	public void delete(long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("template_id", id);

		this.namedParameterJdbcTemplate.execute(this.sqlDelete, paramMap, new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

	@Override
	public GpProjectTemplate find(long id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("template_id", id);
		SelectGpTemplateMapper selectMapper = new SelectGpTemplateMapper();

		List<GpProjectTemplate> dto_list = this.namedParameterJdbcTemplate.query(sqlFind, parameters, selectMapper);
		if (dto_list.size() < 1) {
			log.warn("Template for ID '" + id + "' was not found");
			throw new Exception("Template for ID '" + id + "' was not found");
		}
		return (GpProjectTemplate) dto_list.get(0);
	}

	ArrayList<Integer> arl = new ArrayList<Integer>();
	@Override
	public List<GpProjectTemplate> findAll(long organization_id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		MapSqlParameterSource get_template_by_id = new MapSqlParameterSource();
		//To choose Geppetto's ase template Geppetto ONE and Geppetto TWO
		long base_template_id = 4;
		arl.add(15);
		arl.add(18);
		//arl.add(13);
		//arl.add(16);
		
		List<SelectGpTemplateByOrganization_dto> IndividualTemplateByOrgId = new ArrayList<SelectGpTemplateByOrganization_dto>();
		SelectGpTemplateByOrganizationMapper selectMapper = new SelectGpTemplateByOrganizationMapper();
		SelectGpTemplateMapper selectGpTemplateMapper = new SelectGpTemplateMapper();
		List<GpProjectTemplate> IndividualTemplate = new ArrayList<GpProjectTemplate>();
		List<GpProjectTemplate> sendtemplate = new ArrayList<GpProjectTemplate>();
		//if (!arl.contains(organization_id)) {
			System.out.println("ONLY INDIVIDUAL Organization-- > ");
			
			parameters.addValue("base_organization_id", organization_id);
			IndividualTemplateByOrgId = this.namedParameterJdbcTemplate.query(findByOrganization, parameters, selectMapper);
			System.out.println("GOT template ID based on  Organization id-- > "+IndividualTemplateByOrgId.size());
			for(int i = 0 ; i<IndividualTemplateByOrgId.size();i++){
				System.out.println("-- ?IndividualTemplateByOrgId.get(i).getTemplate_id() - >  > "+IndividualTemplateByOrgId.get(i).getTemplate_id());
				get_template_by_id.addValue("template_id", IndividualTemplateByOrgId.get(i).getTemplate_id());
				IndividualTemplate = this.namedParameterJdbcTemplate.query(sqlFind,get_template_by_id,selectGpTemplateMapper );
				sendtemplate.addAll(IndividualTemplate);
			}
		
				
		for(int y=0;y<IndividualTemplate.size();y++){
			System.out.println("Iteration time  - > "+y );
			System.out.println(" - > IndividualTemplate - > "+IndividualTemplate.get(y));
			//sendtemplate.add(IndividualTemplate.get(y));
		}
		return sendtemplate;
	}
}