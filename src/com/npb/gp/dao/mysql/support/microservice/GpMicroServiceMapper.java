package com.npb.gp.dao.mysql.support.microservice;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMicroService;

public class GpMicroServiceMapper implements RowMapper<GpMicroService> {

	public GpMicroService mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpMicroService the_project = new GpMicroService();

		the_project.setProject_id(rs.getLong("project_id"));
		the_project.setId(rs.getLong("id"));
		the_project.setMicroservice_name(rs.getString("microservice_name"));
		the_project.setActivities_json(rs.getString("activities_json"));
		the_project.setDescription((rs.getString("description")));
		the_project.setNotes(rs.getString("notes"));
		
		return the_project;
	}

}
