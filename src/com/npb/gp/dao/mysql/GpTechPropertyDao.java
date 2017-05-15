package com.npb.gp.dao.mysql;

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

import com.npb.gp.dao.mysql.support.project.GpProject_Mapper;
import com.npb.gp.dao.mysql.support.techproperties.InsertTechProperties;
import com.npb.gp.dao.mysql.support.techproperties.TechPropertiesMapper;
import com.npb.gp.dao.mysql.support.techproperties.UpdateTechProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.dao.IGpTechPropertiesDao;

/**
 * 
 * @author Dan Castillo</br> Date Created: 06/18/2014</br>
 * @since .35</p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for a tech property - </p>
 * 
 *        <b>Modified Date: 05/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 *        <p>
 *        Modified the default_value in insert and update method
 *        </p>
 * 
 * 
 *        <b>Modified Date: 16/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 *        <p>
 *        Switched the sql quries to the properties file for the
 *        get_all_properties_sql
 *        </p>
 *
 *        <b>Modified Date: 14/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 *        <p>
 *        Wrote the following methods as insert_techproperties,
 *        update_techproperties, delete_techproperties and
 *        get_default_properties.
 *        </p>
 */
@Repository("GpTechPropertyDao")
public class GpTechPropertyDao implements IGpTechPropertiesDao {
	private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;

	private InsertTechProperties insert_tech_properties;
	private UpdateTechProperties update_tech_properties;

	@Value("${get_properties.sql}")
	private String get_properties_sql;

	@Value("${get_all_properties.sql}")
	private String get_all_properties_sql;

	@Value("${get_default_properties.sql}")
	private String get_default_properties_sql;

	@Value("${insert_tech_properties.sql}")
	private String insert_tech_properties_sql;

	@Value("${delete_tech_properties.sql}")
	private String delete_tech_properties_sql;

	@Value("${update_tech_properties.sql}")
	private String update_tech_properties_sql;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public GpTechProperties insert(GpTechProperties property) {
		Map<String, Object> techpropertiesMap = new HashMap<String, Object>();
		techpropertiesMap.put("name", property.getName());
		techpropertiesMap.put("label", property.getLabel());
		techpropertiesMap.put("description", property.getDescription());
		techpropertiesMap.put("version", property.getVersion());
		techpropertiesMap.put("release_status", property.getRelease_status());
		techpropertiesMap.put("license_status", property.getLicense_status());
		techpropertiesMap.put("type", property.getType());
		techpropertiesMap.put("notes", property.getNotes());
		// techpropertiesMap.put("default_value", property.isDefault_value());
		if (property.isDefault_value()) {
			techpropertiesMap.put("default_value", "y");
		} else {
			techpropertiesMap.put("default_value", "n");
		}
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertTechProperties.SQL_INSERT_TECHPROPERTIES = insert_tech_properties_sql;
		this.insert_tech_properties = new InsertTechProperties(dataSource);
		this.insert_tech_properties.updateByNamedParam(techpropertiesMap,
				keyHolder);
		property.setId(keyHolder.getKey().longValue());
		return property;
	}

	@Override
	public GpTechProperties update(GpTechProperties property) {
		System.out.println("update dao called");
		Map<String, Object> updatetechObj = new HashMap<String, Object>();
		updatetechObj.put("id", property.getId());
		updatetechObj.put("name", property.getName());
		updatetechObj.put("label", property.getLabel());
		updatetechObj.put("description", property.getDescription());
		updatetechObj.put("version", property.getVersion());
		updatetechObj.put("release_status", property.getRelease_status());
		updatetechObj.put("license_status", property.getLicense_status());
		updatetechObj.put("type", property.getType());
		updatetechObj.put("notes", property.getNotes());
		// updatetechObj.put("default_value", property.isDefault_value());
		if (property.isDefault_value()) {
			updatetechObj.put("default_value", "y");
		} else {
			updatetechObj.put("default_value", "n");
		}
		UpdateTechProperties.SQL_UPDATE_TECHPROPERTIES = update_tech_properties_sql;
		this.update_tech_properties = new UpdateTechProperties(this.dataSource);
		this.update_tech_properties.updateByNamedParam(updatetechObj);
		return property;
	}

	@Override
	public void delete(long property_id) {
		Map<String, Object> deleteparamMapping = new HashMap<String, Object>();
		deleteparamMapping.put("id", property_id);

		this.namedParameterJdbcTemplate.execute(delete_tech_properties_sql,
				deleteparamMapping, new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(
							java.sql.PreparedStatement ps) throws SQLException,
							DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public GpTechProperties find_by_id(long property_id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tech_property_id", property_id);
		
		TechPropertiesMapper property_mapper = new TechPropertiesMapper();
		
		GpTechProperties dto = this.namedParameterJdbcTemplate.queryForObject(
				get_properties_sql, parameters, property_mapper);
		if (dto == null) {
			throw new Exception("tech_property_id number " + property_id
					+ " was not found");
		}
		return dto;

	}

	@Override
	public ArrayList<GpTechProperties> find_by_property_type(long property_type)
			throws Exception {
		return null;
	}

	@Override
	public ArrayList<GpTechProperties> get_all_properties() throws Exception {

		TechPropertiesMapper property_mapper = new TechPropertiesMapper();

		List<GpTechProperties> property_list = this.namedParameterJdbcTemplate
				.query(this.get_all_properties_sql, property_mapper);
		if (property_list.size() < 1) {
			throw new Exception("no tech properties found");
		}
		System.out
				.println("######### - In GpTechPropertyDao -  get_all_properties is: "
						+ property_list.size() + " #######################");

		return (ArrayList<GpTechProperties>) property_list;

	}

	@Override
	public ArrayList<GpTechProperties> get_default_properties(
			GpTechProperties aproperty, GpUser user) {
		MapSqlParameterSource parameters_Of_Default;
		parameters_Of_Default = new MapSqlParameterSource();
		parameters_Of_Default.addValue("default_value", false);

		TechPropertiesMapper property_mapper = new TechPropertiesMapper();
		List<GpTechProperties> default_property_list = this.namedParameterJdbcTemplate
				.query(this.get_default_properties_sql, parameters_Of_Default,
						property_mapper);
		return (ArrayList<GpTechProperties>) default_property_list;
	}
}
