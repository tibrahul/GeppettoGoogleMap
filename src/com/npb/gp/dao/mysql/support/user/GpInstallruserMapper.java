package com.npb.gp.dao.mysql.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpInstallrConfig;

public class GpInstallruserMapper implements RowMapper<GpInstallrConfig> {

	@Override
	public GpInstallrConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpInstallrConfig userInstallr= new GpInstallrConfig();
		userInstallr.setId(rs.getLong("INSTALLR_ID"));
		userInstallr.setInstallr_user_name(rs.getString("INSTALLR_USER_NAME"));
		userInstallr.setInstallr_password(rs.getString("INSTALLR_PASSWORD"));
		userInstallr.setUser_id(rs.getLong("USER_ID"));
		userInstallr.setMac_id(rs.getLong("MAC_CONFIG_ID"));
		userInstallr.setCreatedDate(rs.getDate("CREATEDDATE"));
		userInstallr.setUpdatedDate(rs.getDate("UPDATEDDATE"));
		userInstallr.setMac_ip_address(rs.getString("INSTALLRTOKEN"));
		
		return userInstallr;
	}

}
