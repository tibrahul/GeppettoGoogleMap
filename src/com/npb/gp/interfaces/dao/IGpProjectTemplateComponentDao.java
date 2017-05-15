package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpProjectTemplateComponent;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/31/2015</br>
 * @since 1.0 </p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required for the project template component functions </p>
 *
 */

public interface IGpProjectTemplateComponentDao {

	public void insert(GpProjectTemplateComponent wrapper) throws Exception;

	public void update(GpProjectTemplateComponent wrapper) throws Exception;

	public void delete(long id) throws Exception;

	public void deleteByProject(long projectId) throws Exception;

	public void deleteByProjectTemplate(long projectTemplateId)
			throws Exception;

	public GpProjectTemplateComponent find(long id) throws Exception;

	public List<GpProjectTemplateComponent> findByProject(long projectId)
			throws Exception;

	public List<GpProjectTemplateComponent> findByProjectTemplate(
			long projectTemplateId) throws Exception;

	public List<GpProjectTemplateComponent> findByTemplateComponent(
			long templateComponentId) throws Exception;

	public List<GpProjectTemplateComponent> findByProjectTemplateAndTemplateId(
			long templateId, long projectTemplateId) throws Exception;

	public GpProjectTemplateComponent findByTemplateComponentAndProjectTemplate(
			long projectTemplateId, long templateComponentId) throws Exception;

}
