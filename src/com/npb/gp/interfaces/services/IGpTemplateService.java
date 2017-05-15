package com.npb.gp.interfaces.services;

import java.util.List;
import java.util.Set;

import com.npb.gp.domain.core.GpProjectTemplate;

/**
 * @author Dheeraj Singh</br> Creation Date: 12/30/2015</br>
 * @since 1.0 </p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with template information </p>
 * 
 */

public interface IGpTemplateService {
	public void create(GpProjectTemplate wrapper) throws Exception;

	public void update(GpProjectTemplate wrapper) throws Exception;

	public void delete(long id) throws Exception;

	public GpProjectTemplate find(long id) throws Exception;

	public List<GpProjectTemplate> findAll(long organization_id) throws Exception;
}