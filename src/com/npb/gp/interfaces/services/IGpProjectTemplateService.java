package com.npb.gp.interfaces.services;

import java.util.List;

import com.npb.gp.domain.core.GpProjectTemplate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0 </p>
 *
 *        this interface will be implemented by a service that handles deals
 *        with project template functions </p>
 *
 */

public interface IGpProjectTemplateService {

	public void create(GpProjectTemplate wrapper) throws Exception;

	public void update(GpProjectTemplate wrapper) throws Exception;

	public void delete(long id) throws Exception;

	public void deleteByProject(long projectId) throws Exception;

	public GpProjectTemplate find(long id) throws Exception;

	public List<GpProjectTemplate> findByProject(long projectId) throws Exception;

	public List<GpProjectTemplate> findByTemplate(long templateId)
			throws Exception;
}
