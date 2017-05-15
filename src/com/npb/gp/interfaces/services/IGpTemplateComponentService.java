package com.npb.gp.interfaces.services;

import java.util.List;

import com.npb.gp.domain.core.GpProjectTemplateComponent;

/**
 * @author Dheeraj Singh</br> Creation Date: 12/30/2015</br>
 * @since 1.0 </p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with template component information </p>
 * 
 */

public interface IGpTemplateComponentService {
	public void create(GpProjectTemplateComponent wrapper) throws Exception;

	public void update(GpProjectTemplateComponent wrapper) throws Exception;

	public void delete(long id) throws Exception;

	public void deleteTemplateComponent(long templateId) throws Exception;

	public GpProjectTemplateComponent find(long id) throws Exception;

	public List<GpProjectTemplateComponent> findByTemplate(long templateId)
			throws Exception;
}