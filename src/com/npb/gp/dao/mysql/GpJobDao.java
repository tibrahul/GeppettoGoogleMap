package com.npb.gp.dao.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.job.Insert_Requested_Status;
import com.npb.gp.dao.mysql.support.job.JobMapper;
import com.npb.gp.domain.core.GpJob;
import com.npb.gp.interfaces.dao.IGpJobDao;

/**
 * 
 * @author Kumaresan Perumal
 *         <p>
 *         The purpose of this class is to interact with the db for the basic
 *         search</br>
 *         and CRUD operations for a job service
 *         </p>
 *      @author Kumaresan Perumal</br>
 *         Date Created: 04/25/2016</br>
 * <p>  Here i added a parameter for claimed field. the value is 'f' insdie insert method
 *        </br>
 *         </p>
 * 
 * 
 */
@Repository("GpJobDao")
public class GpJobDao implements IGpJobDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private Insert_Requested_Status insert_status;

	@Value("${get_generation_status.sql}")
	private String get_generation_status;

	@Value("${start_generation.sql}")
	private String start_generation;

	@Value("${insert_status.sql}")
	private String insert_status_requested;
	
	@Value("${get_job_by_uid_pid_status.sql}")
	private String get_job_by_uid_pid_status;
	
	@Value("${get_job_errors.sql}")
	private String get_job_errors;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public GpJob insert(GpJob the_job) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_id", the_job.getProject_id());
		paramMap.put("user_id", the_job.getUser_id());
		paramMap.put("user_name", the_job.getUser_name());
		paramMap.put("status", "gen_requested");
		paramMap.put("status_message", "generation requested");
		paramMap.put("stack_trace", "gen_processing");
		
		paramMap.put("claimed", "f");
		Insert_Requested_Status.SQL_INSERT_JOB = insert_status_requested;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.insert_status = new Insert_Requested_Status(this.dataSource);
		this.insert_status.updateByNamedParam(paramMap,keyHolder);
		the_job.setId(keyHolder.getKey().longValue());
		return the_job;
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
		System.out.println("Gp Job code entered");
		System.out.println("Here we insert the job record");
		the_job = this.insert(the_job);
		return the_job;
	}

	@Override
	public GpJob get_generation_status(long parent_job_id) throws Exception {
		System.out.println("Gp Job code entered");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("parent_job_id", parent_job_id);
		GpJob the_generation_status;
		try {
			JobMapper job_mapper = new JobMapper();
			System.out.println(this.get_generation_status);
			the_generation_status = this.namedParameterJdbcTemplate.queryForObject(this.get_generation_status,
					parameters, job_mapper);
			return the_generation_status;
		} catch (Exception e) {
			System.out.println("didn't find new status for this job: " + parent_job_id);
		}
		return null;
	}
	
	public GpJob get_job_by_id_pid_gst(long parent_job_id, String gen_status) throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("parent_job_id", parent_job_id);
		parameters.addValue("gen_status", gen_status);
		JobMapper job_mapper = new JobMapper();
		GpJob the_job = this.namedParameterJdbcTemplate.queryForObject(this.get_job_by_uid_pid_status, parameters, job_mapper);
		return the_job;
	}

	public List<GpJob> get_job_errors(String parent_job_id) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("parent_job_id", parent_job_id);
		parameters.addValue("gen_status", "gen_error");
		JobMapper job_mapper = new JobMapper();
		List<GpJob> the_jobs = this.namedParameterJdbcTemplate.query(this.get_job_errors, parameters, job_mapper);
		return the_jobs;
	}
}