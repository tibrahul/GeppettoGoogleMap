package com.npb.gp.dao.mysql.support.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpJob;

public class JobMapper implements RowMapper<GpJob> {
	@Override
	public GpJob mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpJob job_objects = new GpJob();
		job_objects.setId(rs.getLong("ID"));
		job_objects.setProject_id(rs.getLong("PROJECT_ID"));
		job_objects.setUser_id(rs.getLong("USER_ID"));
		job_objects.setUser_name(rs.getString("USER_NAME"));
		job_objects.setStatus(rs.getString("STATUS"));
		job_objects.setStatus_message(rs.getString("STATUS_MESSAGE"));
		job_objects.setStack_trace(rs.getString("STACK_TRACE"));
		job_objects.setClaimed(rs.getString("CLAIMED"));
		job_objects.setParent_job_id(rs.getLong("parent_job_id"));
		return job_objects;
	}
}