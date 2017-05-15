package com.npb.gp.interfaces.services;

import com.npb.gp.domain.core.GpJob;

public interface IGpJobService {
	public void insert(long project_id, long user_id);

	public void update(long project_id, long user_id);

	public void update_status(long job_id, String status_info);

	public void delete(long project_id, long user_id);

	public GpJob find_by_id(long noun_id) throws Exception;

	public long find_and_lock() throws Exception;

	public GpJob start_generation(GpJob the_job) throws Exception;

	public GpJob get_generation_status(long parent_job_id) throws Exception;
}