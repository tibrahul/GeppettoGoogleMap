package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpProjectTemplateComponent;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0 </p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required for the template components base information functions </p>
 *
 */

public interface IGpTemplateComponentDao {

	public void insert(GpProjectTemplateComponent wrapper) throws Exception;

	public void update(GpProjectTemplateComponent wrapper) throws Exception;

	public void delete(long id) throws Exception;

	public void deleteTemplateComponent(long templateId) throws Exception;

	public GpProjectTemplateComponent find(long id) throws Exception;

	public List<GpProjectTemplateComponent> findByTemplate(long templateId)
			throws Exception;
}
