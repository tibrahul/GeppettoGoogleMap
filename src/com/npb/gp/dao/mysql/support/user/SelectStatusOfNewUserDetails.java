package com.npb.gp.dao.mysql.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpNewUser;
import com.npb.gp.domain.core.GpUserDetailWithAdmin;

public class SelectStatusOfNewUserDetails implements RowMapper<GpNewUser> {

	public GpNewUser mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpNewUser wrapper = new GpNewUser();
		wrapper.setId(rs.getInt("ID"));
		wrapper.setAdminid(rs.getInt("ADMINID"));
		wrapper.setUsername(rs.getString("USERNAME"));
		wrapper.setProcessed(rs.getString("PROCESSED"));
		wrapper.setLockorunlock(rs.getString("LOCKORUNLOCK"));
		wrapper.setIsEditable(rs.getString("ISEDITABLE"));
		wrapper.setType(rs.getString("TYPE"));
		// wrapper.setUser_id(rs.getInt("ADMIN_USER_ID"));

		return wrapper;
	}

}
