package com.npb.gp.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpMobileDeviceTypeDao;
import com.npb.gp.domain.core.GpMobileDeviceType;
import com.npb.gp.interfaces.services.IGpMobileDeviceTypeService;

/**
 * 
 * @author Suresh Palanisamy </br> Date Created: 25/03/2015</br>
 * @since .75</p>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with devices</br>
 * 
 */

@Service("GpMobileDeviceTypeService")
public class GpMobileDeviceTypeService extends GpBaseService implements
		IGpMobileDeviceTypeService {

	private GpMobileDeviceTypeDao device_type_dao;

	public GpMobileDeviceTypeDao getDevice_type_dao() {
		return device_type_dao;
	}

	@Resource(name = "GpMobileDeviceTypeDao")
	public void setDevice_type_dao(GpMobileDeviceTypeDao device_type_dao) {
		this.device_type_dao = device_type_dao;
	}

	@Override
	public List<GpMobileDeviceType> find_all_devices() throws Exception {
		return this.device_type_dao.find_all_devices();
	}

}
