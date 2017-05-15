package com.npb.gp.interfaces.dao;

import com.npb.gp.domain.core.GpModule;

/**
 * 
 * @author Reinaldo</br> 
 * Date Created: 20/09/2015</br>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br> for the modules</p>
 *
 */
public interface IGpModuleDao {

	public GpModule insert(GpModule module);
}
