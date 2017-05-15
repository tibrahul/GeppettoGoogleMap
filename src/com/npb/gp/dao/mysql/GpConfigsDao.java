package com.npb.gp.dao.mysql;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.gpconfigs.GpConfigsMapper;
import com.npb.gp.domain.core.GpConfig;

@Repository("GpConfigsDao")
public class GpConfigsDao {
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${get_gpconfig_by_name.sql}")
	private String get_gpconfig_by_name;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public GpConfig get_by_name(String name) {
		GpConfigsMapper the_mapper = new GpConfigsMapper();
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("name", name);
		GpConfig the_config = this.namedParameterJdbcTemplate.queryForObject(this.get_gpconfig_by_name, parameters, the_mapper);
		return the_config;
	}
	
	
}
