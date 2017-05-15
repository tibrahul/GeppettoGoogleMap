package com.npb.gp.dao.mysql;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.module.InsertModule;
import com.npb.gp.domain.core.GpModule;
import com.npb.gp.interfaces.dao.IGpModuleDao;

/**
 * 
 * @author Reinaldo</br> 
 * Date Created: 20/09/2015</br>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for the modules</p>
 *
 */
@Repository("GpModuleDao")
public class GpModuleDao implements IGpModuleDao{
	
	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private InsertModule insert_module;
	
	@Value("${insert_module.sql}")
	private String insert_module_sql;
	
	@Value("${get_gcd_json_by_id.sql}")
	private String get_gcd_json_by_id;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Override
	public GpModule insert(GpModule module) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", module.getName());
		paramMap.put("label", module.getLabel());
		paramMap.put("description", module.getDescription());
		paramMap.put("notes", module.getNotes());		
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", module.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", module.getLastmodifiedby());
		paramMap.put("projectid", module.getProjectid());
		paramMap.put("base_location", module.getBase_location());
		paramMap.put("predefined_activity_id", module.getPredefined_activity_id());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertModule.SQL_INSERT_MODULE = insert_module_sql;
		this.insert_module = new InsertModule(this.dataSource);
		this.insert_module.updateByNamedParam(paramMap, keyHolder);
		module.setId(keyHolder.getKey().longValue());
		System.out.println("The module id is: " + module.getId());

		return module;
	}
	
	public String get_gcd_json(long predefined_activity_id) throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("predefined_activity_id", predefined_activity_id);
		String json_string = this.namedParameterJdbcTemplate.queryForObject(get_gcd_json_by_id, parameters, String.class);
		if(json_string == null || json_string.isEmpty())
			throw new Exception("gcd json empty");
		return json_string;
	}

}
