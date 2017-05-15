package com.npb.gp.dao.mysql;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("GpDockerDownloadRequestsDao")
public class GpDockerDownloadRequestsDao {
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${request_docker_download.sql}")
	private String request_docker_download;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public String request_download(long user_id){
		JSONObject json = new JSONObject();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("user_id", user_id);
		try{
			this.namedParameterJdbcTemplate.update(request_docker_download, parameters);
			json.put("status", "download requested");
		}catch(Exception e){
			json.put("error", true);
			json.put("error_message", e.getCause());
		}
		return json.toString();
	}
	
}
