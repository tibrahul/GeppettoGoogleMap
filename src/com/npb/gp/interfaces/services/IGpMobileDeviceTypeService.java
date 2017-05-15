package com.npb.gp.interfaces.services;

import java.util.List;

import com.npb.gp.domain.core.GpMobileDeviceType;

/**
 * 
 * @author Suresh Palanisamy</br> Creation Date: 25/03/2015</br>
 * @since .75</p>
 * 
 *        this interface will be implemented by a service that handles device
 *        types</p>
 *
 */

public interface IGpMobileDeviceTypeService {

	public List<GpMobileDeviceType> find_all_devices() throws Exception;
}
