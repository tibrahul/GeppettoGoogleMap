package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpMobileDeviceType;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 25/03/2015</br>
 * @since .75</p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br> for the device types</p>
 *
 */

public interface IGpMobileDeviceTypeDao {

	public List<GpMobileDeviceType> find_all_devices() throws Exception;
	
}
