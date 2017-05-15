package com.npb.gp.dao.mysql.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpUser;

public class GpUserIdRowMapper implements RowMapper<GpUser>{

	@Override
	public GpUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpUser user = new GpUser();
		user.setId(rs.getLong("USER_ID"));
		user.setUsername(rs.getString("USER_NAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setLanguagepreference(rs.getString("LANGUAGE_PREFERENCE"));
		user.setStartdate(rs.getDate("START_DATE"));
		user.setLicenseid(rs.getString("LICENESE_ID"));
		user.setLastaccess(rs.getDate("LAST_ACCESS"));
		user.setMustresetpassword(rs.getString("RESETPASSWORD"));
		user.setAccesstype(rs.getString("ACCESS_TYPE"));
		user.setNewuser(rs.getBoolean("NEW_USER"));
		user.setRole(rs.getString("ROLE"));
		return user;
	}

}
