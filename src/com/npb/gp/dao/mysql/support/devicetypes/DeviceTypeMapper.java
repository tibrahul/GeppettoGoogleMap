package com.npb.gp.dao.mysql.support.devicetypes;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMobileDeviceType;

public class DeviceTypeMapper implements RowMapper<GpMobileDeviceType> {

	@Override
	public GpMobileDeviceType mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpMobileDeviceType devicetype = new GpMobileDeviceType();
		devicetype.setId(rs.getLong("id"));
		devicetype.setClient_device_type(rs.getString("client_device_type"));
		devicetype.setClient_device_type_name(rs.getString("client_device_type_name"));
		devicetype.setClient_device_type_label(rs.getString("client_device_type_label"));
		devicetype.setClient_device_type_description(rs.getString("client_device_type_description"));
		devicetype.setClient_device_screen_size(rs.getString("client_device_screen_size"));
		devicetype.setClient_device_resolution(rs.getString("client_device_resolution"));
		devicetype.setClient_device_type_os_name(rs.getString("client_device_type_os_name"));
		devicetype.setClient_device_type_os_version(rs.getString("client_device_type_os_version"));
		devicetype.setClient_device_ppcm(rs.getString("client_device_ppcm"));
		devicetype.setLandscape_image_name(rs.getString("landscape_image_name"));
		devicetype.setPortrait_image_name(rs.getString("portrait_image_name"));

		return devicetype;
	}

}
