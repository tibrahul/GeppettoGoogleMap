package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpCouchBasedomain;

public class CouchMapper implements RowMapper<GpCouchBasedomain> {

	@Override
	public GpCouchBasedomain mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		GpCouchBasedomain couch = new GpCouchBasedomain();
		couch.setId(rs.getLong("id"));
		couch.setProject_id(rs.getLong("project_id"));
		couch.setUser_id(rs.getString("user_id"));
		couch.setbucket(rs.getString("bucket"));
		couch.setPassword(rs.getString("password"));
		couch.setDesign(rs.getString("design"));
		couch.setViews(rs.getString("views"));
		couch.setAttribute(rs.getString("attribute"));
		return couch;
	}

}
