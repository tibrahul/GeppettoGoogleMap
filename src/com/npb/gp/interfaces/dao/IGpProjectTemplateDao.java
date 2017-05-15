package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpProjectTemplate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0 </p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required for the project template information functions </p>
 *
 */

public interface IGpProjectTemplateDao {

	public void insert(GpProjectTemplate wrapper) throws Exception;

	public void update(GpProjectTemplate wrapper) throws Exception;

	public void delete(long id) throws Exception;

	public void deleteByProject(long projectId) throws Exception;

	public GpProjectTemplate find(long id) throws Exception;

	public List<GpProjectTemplate> findByProject(long projectId) throws Exception;

	public List<GpProjectTemplate> findByTemplate(long templateId)
			throws Exception;
}
