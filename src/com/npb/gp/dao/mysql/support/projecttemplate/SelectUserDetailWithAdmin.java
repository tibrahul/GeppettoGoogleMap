package com.npb.gp.dao.mysql.support.projecttemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpUserDetailWithAdmin;

public class SelectUserDetailWithAdmin implements RowMapper<GpUserDetailWithAdmin> {

	public GpUserDetailWithAdmin mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpUserDetailWithAdmin wrapper = new GpUserDetailWithAdmin();
		wrapper.setAdminid(rs.getInt("ADMIN_USER_ID"));
		wrapper.setId(rs.getInt("ID"));
		wrapper.setNewuserid(rs.getInt("NEW_USER_ID"));
		return wrapper;
	}

}
