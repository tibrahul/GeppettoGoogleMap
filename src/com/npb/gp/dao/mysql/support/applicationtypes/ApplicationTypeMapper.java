package com.npb.gp.dao.mysql.support.applicationtypes;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpApplicationType;

public class ApplicationTypeMapper implements RowMapper<GpApplicationType> {

	@Override
	public GpApplicationType mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpApplicationType applicationType = new GpApplicationType();
		applicationType.setId(rs.getLong("id"));
		applicationType.setName(rs.getString("name"));
		applicationType.setDescription(rs.getString("description"));
		
		return applicationType;
	}

}
