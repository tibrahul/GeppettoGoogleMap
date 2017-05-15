package com.npb.gp.interfaces.dao;

import com.npb.gp.domain.core.GpJob;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 06/13/2014</br>
 * @since .35
 *        </p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br>
 *        for the functions that handle the start of code generation
 *        </p>
 *
 *
 */

public interface IGpJobDao {

	public GpJob insert(GpJob the_job);

	public void update(long project_id, long user_id);

	public void update_status(long job_id, String status_info);

	public void delete(long project_id, long user_id);

	public GpJob find_by_id(long noun_id) throws Exception;

	public long find_and_lock() throws Exception;

	public GpJob start_generation(GpJob the_job) throws Exception;

	public GpJob get_generation_status(long parent_job_id) throws Exception;
}