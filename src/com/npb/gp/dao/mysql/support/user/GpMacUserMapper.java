package com.npb.gp.dao.mysql.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMacConfig;

public class GpMacUserMapper  implements RowMapper<GpMacConfig>  {

	@Override
	public GpMacConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpMacConfig userMac= new GpMacConfig();
		userMac.setId(rs.getLong("MAC_CONFIG_ID"));
		userMac.setIpa_mac_ip_address(rs.getString("IPA_MAC_IP_ADDRESS"));
		userMac.setIpa_mac_password(rs.getString("IPA_MAC_PASSWORD"));
		userMac.setIpa_mac_user_name(rs.getString("IPA_MAC_USER_NAME"));
		userMac.setStatus(rs.getString("STATUS"));
		userMac.setApple_dev_account(rs.getString("APPLE_DEV_ACCOUNT"));
		userMac.setCreatdedDate(rs.getDate("CREATEDDATE"));
		userMac.setUpdatedDate(rs.getDate("UPDATEDDATE"));
		
		return userMac;
	}

}
