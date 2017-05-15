package com.npb.gp.interfaces.services;

import com.npb.gp.domain.core.GpModule;
import com.npb.gp.domain.core.GpUser;
/**
 * 
 * @author Reinaldo</br> 
 * Creation Date: 20/09/2015</br>
 * 
 *        This interface will be implemented by a service that handles modules
 *
 */
public interface IGpModuleService {
	
	public GpModule insert_module(GpModule module, GpUser user) throws Exception;

}
