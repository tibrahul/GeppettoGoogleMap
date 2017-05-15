package com.npb.gp.interfaces.services;

import java.util.List;

import com.npb.gp.domain.core.GpProjectTemplateComponent;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/31/2015</br>
 * @since 1.0 </p>
 *
 *        this interface will be implemented by a service that handles deals
 *        with project template component functions </p>
 *
 */

public interface IGpProjectTemplateComponentService {

	public void create(GpProjectTemplateComponent wrapper) throws Exception;

	public void update(GpProjectTemplateComponent wrapper) throws Exception;

	public void createOrUpdate(long projectId, long projectTemplateId,
			List<GpProjectTemplateComponent> templateComponents)
			throws Exception;

	public void delete(long id) throws Exception;

	public void deleteByProject(long projectId) throws Exception;

	public void deleteByProjectTemplate(long projectTemplateId)
			throws Exception;

	public GpProjectTemplateComponent find(long id) throws Exception;

	public List<GpProjectTemplateComponent> findByProject(long projectId)
			throws Exception;

	public List<GpProjectTemplateComponent> findByTemplateComponent(
			long templateComponentId) throws Exception;

	public List<GpProjectTemplateComponent> findByProjectTemplateAndTemplateId(
			long templateId, long projectTemplateId) throws Exception;
}
