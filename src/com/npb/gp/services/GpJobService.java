package com.npb.gp.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpJobDao;
import com.npb.gp.domain.core.GpJob;
import com.npb.gp.interfaces.services.IGpJobService;

/***
 * 
 * @author Kumaresan Perumal
 * 
 *         <p>
 *         The purpose of this class is to provide the entry point for any
 *         functions having to do with job service
 *         </p>
 * 
 *
 */
@Service("GpJobService")
public class GpJobService extends GpBaseService implements IGpJobService {
	private GpJobDao job_dao;

	public GpJobDao getJob_dao() {
		return job_dao;
	}

	@Resource(name = "GpJobDao")
	public void setJob_dao(GpJobDao job_dao) {
		this.job_dao = job_dao;
	}

	@Override
	public void insert(long project_id, long user_id) {

	}

	@Override
	public void update(long project_id, long user_id) {

	}

	@Override
	public void update_status(long job_id, String status_info) {

	}

	@Override
	public void delete(long project_id, long user_id) {

	}

	@Override
	public GpJob find_by_id(long noun_id) throws Exception {
		return null;
	}

	@Override
	public long find_and_lock() throws Exception {
		return 0;
	}

	@Override
	public GpJob start_generation(GpJob the_job) throws Exception {
		System.out.println("Gp Job Service Entered");
		return job_dao.start_generation(the_job);
	}

	@Override
	public GpJob get_generation_status(long parent_job_id) throws Exception {
		System.out.println("Gp Job Service Entered");
		return job_dao.get_generation_status(parent_job_id);
	}
	
	public GpJob get_job_by_pid_uid_status(long parent_job_id, String gen_status) throws Exception{
		return job_dao.get_job_by_id_pid_gst(parent_job_id, gen_status);
	}

	public List<GpJob> get_job_errors(String parent_job_id) {
		return job_dao.get_job_errors(parent_job_id);
	}
}