package com.npb.gp.dao.mysql.support.activity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpAuthority;

public class GpAuthorityRowMapper implements RowMapper<GpAuthority> {

	@Override
	public GpAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpAuthority authority = new GpAuthority();
		authority.setId(rs.getLong("ID"));
		authority.setUsername(rs.getString("USER_NAME"));
		authority.setAuthority(rs.getString("ROLE_NAME"));
		return authority;
	}

}
