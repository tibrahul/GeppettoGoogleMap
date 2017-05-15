package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.devicetypes.DeviceTypeMapper;
import com.npb.gp.domain.core.GpMobileDeviceType;
import com.npb.gp.interfaces.dao.IGpMobileDeviceTypeDao;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 25/03/2015</br>
 * @since .75</p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for a device types</p>
 *
 */

@Repository("GpMobileDeviceTypeDao")
public class GpMobileDeviceTypeDao implements IGpMobileDeviceTypeDao {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${findAllDeviceTypes.sql}")
	private String findAllDevices;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public List<GpMobileDeviceType> find_all_devices() throws Exception {

		DeviceTypeMapper devices = new DeviceTypeMapper();
		List<GpMobileDeviceType> all_devices = this.namedParameterJdbcTemplate
				.query(findAllDevices, devices);

		if (all_devices.size() < 1) {
			return new ArrayList<GpMobileDeviceType>();
		}

		return all_devices;
	}

}
