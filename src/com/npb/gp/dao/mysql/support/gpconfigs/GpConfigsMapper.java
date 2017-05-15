package com.npb.gp.dao.mysql.support.gpconfigs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpConfig;

public class GpConfigsMapper implements RowMapper<GpConfig> {

	@Override
	public GpConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpConfig config = new GpConfig();
		config.setId(rs.getLong("id"));
		config.setName(rs.getString("CONFIG_NAME"));
		config.setLabel(rs.getString("label"));
		config.setDescription(rs.getString("description"));
		config.setValue(rs.getString("value"));
		config.setType(rs.getString("type"));
		config.setSub_type(rs.getString("sub_type"));
		return config;
	}

}
