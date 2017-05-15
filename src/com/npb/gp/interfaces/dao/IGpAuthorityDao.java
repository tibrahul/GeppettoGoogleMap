package com.npb.gp.interfaces.dao;

import com.npb.gp.domain.core.GpAuthority;

/**
 * @author Rashmi
 * 
 * @date : 09 Sept/15
 */
public interface IGpAuthorityDao {
	
	public abstract void insert(GpAuthority gpAuth) throws Exception;

}
