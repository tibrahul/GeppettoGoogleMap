package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpProjectTemplate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/18/2015</br>
 * @since 1.0 </p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required for the template base information functions </p>
 *
 */

public interface IGpTemplateDao {

	public void insert(GpProjectTemplate wrapper) throws Exception;

	public void update(GpProjectTemplate wrapper) throws Exception;

	public void delete(long id) throws Exception;

	public GpProjectTemplate find(long id) throws Exception;

	public List<GpProjectTemplate> findAll(long organization_id) throws Exception;
}
